package wizard_team.wizards_tale.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;

import org.javatuples.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

import wizard_team.wizards_tale.WizardsTaleGame;
import wizard_team.wizards_tale.appwarp_listeners.EventType;
import wizard_team.wizards_tale.appwarp_listeners.NotificationEventType;
import wizard_team.wizards_tale.appwarp_listeners.RoomRequestEventType;

public class MPRoom implements Screen, Observer {
    private final String tag = "MPRoom";
    private WizardsTaleGame game;
    private final RoomData roomData;
    private SpriteBatch spriteBatch;
    private AssetManager assetManager;
    private Skin skin;
    private Stage stage;
    private Viewport viewport;
    private WarpClient warpClient;
    private ArrayList<ChatMessage> chatMessages;
    private HashSet<String> roomUsers = new HashSet<>();
    private List<String> userList;
    private GameConfig gameConfig = new GameConfig();
    private Slider durationSlider;
    private Label durationLabel;
    private CheckBox powerupsToggle;
    private final HashMap<String, Object> roomProps = new HashMap<>();
    private boolean propsHaveChanged = false;
    private final ArrayList<Observable> observables = new ArrayList<>();

    public MPRoom(WizardsTaleGame game, RoomData roomData, String currentUsername) {
        roomUsers.add(currentUsername);
        skin = game.getSkin();
        userList = new List<String>(skin);
        this.game = game;
        this.roomData = roomData;
        game.awListeners.zoneRequestListener.addObserver(this);
        spriteBatch = game.getSpriteBatch();
        assetManager = game.getAssetManager();
        Camera camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);
        viewport.apply(true);
        stage = createStage(viewport);
        warpClient = game.getWarpClient();
        Gdx.input.setInputProcessor(this.stage);

        observables.add(game.awListeners.notificationListener);
        observables.add(game.awListeners.roomRequestListener);
        for (Observable observable : observables) {
            observable.addObserver(this);
        }

        warpClient.getLiveRoomInfo(roomData.getId());
    }

    @Override
    public void update(Observable observable, Object o) {
        Tuple tup = (Tuple) o;
        EventType type = (EventType) tup.getValue(0);

        Gdx.app.log(tag, tup.toString());

        if (type instanceof NotificationEventType) {
            switch ((NotificationEventType) type) {
                case USER_CHANGE_ROOM_PROPERTY:
                    RoomData roomData = (RoomData) tup.getValue(1);
                    String s = (String) tup.getValue(2);
                    HashMap<String, Object> hashMap = (HashMap) tup.getValue(3);
                    HashMap<String, Object> hashMap1 = (HashMap) tup.getValue(4);
                    Gdx.app.log(tag, tup.toString());

                    // Update gameConfig with received values
                    gameConfig.powerupsActive = (boolean) hashMap.get(RoomProp.POWERUPS_ACTIVE);
                    gameConfig.gameDuration = Float.valueOf((int) hashMap.get(RoomProp.DURATION_SEC));
                    break;
                case CHAT_RECEIVED:
                    break;
                case USER_JOINED_ROOM:
                    String joinedUser = (String) tup.getValue(1);
                    roomUsers.add(joinedUser);
                    updateUserList();
                    break;
                case USER_LEFT_ROOM:
                    String departedUser = (String) tup.getValue(1);
                    roomUsers.remove(departedUser);
                    updateUserList();
                    break;
                case UPDATE_PEERS_RECEIVED:
                default:
                    Gdx.app.log(tag, "Unhandled NotificationEventType: " + tup.toString());
            }
        } else if (type instanceof RoomRequestEventType) {
            switch ((RoomRequestEventType) type) {
                case GET_LIVE_ROOM_INFO:
                    LiveRoomInfoEvent event = (LiveRoomInfoEvent) tup.getValue(1);
                    for (String username : event.getJoinedUsers()) {
                        Gdx.app.log(tag, "User joined: " + username);
                        roomUsers.add(username);
                        updateUserList();
                    }
                    break;
                case SET_CUSTOM_ROOM_DATA:
                case UPDATE_PROPERTY_DONE:
                case JOIN_AND_SUBSCRIBE:
                case LEAVE_AND_UNSUBSCRIBE:
                case JOIN_ROOM_DONE:
                case LEAVE_ROOM_DONE:
                case SUBSCRIBE_ROOM_DONE:
                case UNSUBSCRIBE_ROOM_DONE:
                default:
                    Gdx.app.log(tag, "Unhandled RoomRequestEventType: " + tup.toString());
            }
        }
    }

    private void updateUserList() {
        userList.setItems(roomUsers.toArray(new String[0]));
    }

    private Stage createStage(Viewport viewport) {
        Stage stage = new Stage(viewport);

        Table rootTable = new Table();
        stage.addActor(rootTable);
        rootTable.setFillParent(true);
        rootTable.setDebug(true);

        TextButton backButton = new TextButton("Back", skin);
        rootTable.add(backButton);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.popScreen();
            }
        });

        Label title = new Label("Game Room: ", skin);
        rootTable.add(title).top();

        Label roomTitle = new Label(roomData.getName(), skin);
        rootTable.add(roomTitle);

        // List of players in room
        rootTable.row();
        userList.setItems(roomUsers.toArray(new String[0]));
        rootTable.add(userList);

        // Room settings

        // Game duration
        rootTable.row();
        durationSlider = new Slider(30, 300, 10, false, skin);
        String initialDuration = String.valueOf(durationSlider.getValue());
        durationLabel = new Label(initialDuration, skin);
        Table durationLabelTable = new Table();
        rootTable.add(durationLabelTable);
        durationLabelTable.add(new Label("Game duration", skin));
        durationLabelTable.row();
        durationLabelTable.add(durationLabel);
        rootTable.add(durationSlider);
        durationSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!durationSlider.isDragging()) {
                    gameConfig.gameDuration = durationSlider.getValue();
                    propsHaveChanged = true;
                }
            }
        });

        // Powerups enabled
        rootTable.row();
        powerupsToggle = new CheckBox("Powerups enabled", skin);
        rootTable.add(powerupsToggle);
        powerupsToggle.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameConfig.powerupsActive = powerupsToggle.isChecked();
                propsHaveChanged = true;
            }
        });

        // AI players

        // Ready button
        rootTable.row();
        TextButton readyButton = new TextButton("Ready", skin);
        rootTable.add(readyButton);
        readyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MPGameScreen(game, gameConfig));
            }
        });

        return stage;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update UI element contents
        durationSlider.setValue(gameConfig.gameDuration);
        durationLabel.setText(String.valueOf(gameConfig.gameDuration + " sec"));
        powerupsToggle.setChecked(gameConfig.powerupsActive);

        if (propsHaveChanged) {
            propsHaveChanged = false;
            roomProps.put(RoomProp.DURATION_SEC, gameConfig.gameDuration);
            roomProps.put(RoomProp.POWERUPS_ACTIVE, gameConfig.powerupsActive);
            warpClient.updateRoomProperties(roomData.getId(),
                    roomProps,
                    null);
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
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
        for (Observable observable : observables) {
            observable.addObserver(this);
        }
    }

    @Override
    public void hide() {
        for (Observable observable : observables) {
            observable.deleteObserver(this);
        }
    }

    @Override
    public void dispose() {
        for (Observable observable : observables) {
            observable.deleteObserver(this);
        }
        warpClient.leaveAndUnsubscribeRoom(roomData.getId());
    }

}

