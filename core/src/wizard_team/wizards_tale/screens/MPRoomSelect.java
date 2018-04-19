package wizard_team.wizards_tale.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.events.AllRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;

import org.javatuples.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import wizard_team.wizards_tale.WizardsTaleGame;
import wizard_team.wizards_tale.appwarp_listeners.EventType;
import wizard_team.wizards_tale.appwarp_listeners.RoomRequestEventType;
import wizard_team.wizards_tale.appwarp_listeners.ZoneRequestEventType;

public class MPRoomSelect implements Screen, Observer {
    private WizardsTaleGame game;
    private final String username;
    private SpriteBatch spriteBatch;
    private AssetManager assetManager;
    private Skin skin;
    private Stage stage;
    private Viewport viewport;
    private String consoleStr = "";
    private WarpClient warpClient;
    private ArrayList<String> messages = new ArrayList<String>();
    private Label messagesLabel;
    private Array rooms = new Array();
    private List roomList;
    private static final int updatePeriod = 5;
    private float updateTimer = updatePeriod;
    private boolean enterRoom = false;
    private RoomData roomData;
    private final String tag = "RoomSelect";
    private boolean handleEvents = true;

    public MPRoomSelect(WizardsTaleGame game, String username) {
        this.game = game;
        this.username = username;
        game.awListeners.zoneRequestListener.addObserver(this);
        game.awListeners.roomRequestListener.addObserver(this);
        spriteBatch = game.getSpriteBatch();
        assetManager = game.getAssetManager();
        skin = game.getSkin();
        Camera camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);
        viewport.apply(true);
        stage = createStage(viewport);
        warpClient = game.getWarpClient();
        Gdx.input.setInputProcessor(this.stage);
        updateRoomList();
    }

    private void updateRoomList() {
        warpClient.getAllRooms();
    }

    private Stage createStage(Viewport viewport) {
        Stage stage = new Stage(viewport);

        Table rootTable = new Table();
        stage.addActor(rootTable);
        rootTable.setFillParent(true);
        rootTable.setDebug(true);

        final TextField newRoomName = new TextField("New room name", skin);
        rootTable.add(newRoomName);

        TextButton newRoomButton = new TextButton("New room", skin);
        rootTable.add(newRoomButton);
        newRoomButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HashMap<String, Object> roomProps = new HashMap<String, Object>();
                warpClient.createRoom(
                        newRoomName.getText(),
                        username,
                        4,
                        roomProps
                );
            }
        });

        rootTable.row();

        roomList = new List(skin);
        roomList.setItems(rooms);
        rootTable.add(roomList).expandX().expandY().left().top();

        TextButton joinRoomButton = new TextButton("Join room", skin);
        rootTable.add(joinRoomButton);
        joinRoomButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(tag, "Joining room " + roomList.getSelected());
                warpClient.joinAndSubscribeRoom((String) roomList.getSelected());
            }
        });

        return stage;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
        updateRoomList();
        handleEvents = true;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateTimer -= delta;
        if (updateTimer < 0) {
            updateRoomList();
            updateTimer = updatePeriod;
        }

        if (enterRoom) {
            enterRoom = false;
            game.setScreen(new MPRoom(game, roomData, username));
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        handleEvents = false;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void update(Observable observable, Object o) {
        if (!handleEvents) {
            return;
        }

        Tuple tup = (Tuple) o;
        EventType type = (EventType) tup.getValue(0);
        if (type instanceof RoomRequestEventType) {
            switch ((RoomRequestEventType) type) {
                case JOIN_AND_SUBSCRIBE:
                    RoomEvent joinSubEvent = (RoomEvent) tup.getValue(1);
                    roomData = joinSubEvent.getData();
                    enterRoom = true;
                    break;
                default:
                    Gdx.app.log(tag, "Unhandled RoomRequestEventType: " + tup);
            }
        } else if (type instanceof ZoneRequestEventType) {
            switch ((ZoneRequestEventType) type) {
                case GET_ALL_ROOMS_DONE:
                    AllRoomsEvent getAllRoomsDone = (AllRoomsEvent) tup.getValue(1);
                    if (getAllRoomsDone.getRoomIds() != null) {
                        rooms = new Array(getAllRoomsDone.getRoomIds());
                        rooms.sort();
                        roomList.setItems(rooms);
                        break;
                    }
                default:
                    Gdx.app.log(tag, "Unhandled ZoneRequestEventType: " + tup);
            }
        } else {
            Gdx.app.log(tag, "Unexpected EventType: " + tup.toString());
        }

    }

}

