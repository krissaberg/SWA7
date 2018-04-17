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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
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
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import wizard_team.wizards_tale.WizardsTaleGame;

public class MPRoomSelect implements Screen, Observer {
    private WizardsTaleGame game;
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
    private float updateTimer = 2;

    public MPRoomSelect(WizardsTaleGame game) {
        this.game = game;
        game.awListeners.zoneRequestListener.addObserver(this);
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

        TextField newRoomName = new TextField("New room name", skin);
        rootTable.add(newRoomName);

        TextButton newRoomButton = new TextButton("New room", skin);
        rootTable.add(newRoomButton);

        rootTable.row();

        roomList = new List(skin);
        roomList.setItems(rooms);
        rootTable.add(roomList).expandX().expandY().left().top();

        TextButton joinRoomButton = new TextButton("Join room", skin);
        rootTable.add(joinRoomButton);

        return stage;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateTimer -= delta;
        if(updateTimer < 0) {
            updateRoomList();
            updateTimer = 2;
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

    }

    @Override
    public void dispose() {

    }

    @Override
    public void update(Observable observable, Object o) {
        Gdx.app.log("MPRoomSelect", "Something happened");
        Gdx.app.log("MPRoomSelect", o.toString());
        if(o instanceof AllRoomsEvent) {
            AllRoomsEvent e = (AllRoomsEvent) o;
            rooms = new Array(e.getRoomIds());
            rooms.sort();
            roomList.setItems(rooms);
        }
        if(o instanceof LiveRoomInfoEvent) {
            System.out.println(((RoomEvent) o).getResult());
        }
    }
}

