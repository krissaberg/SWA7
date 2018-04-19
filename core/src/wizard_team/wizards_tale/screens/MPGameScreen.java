package wizard_team.wizards_tale.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.UpdateEvent;

import org.javatuples.Triplet;
import org.javatuples.Tuple;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import wizard_team.wizards_tale.WizardsTaleGame;
import wizard_team.wizards_tale.appwarp_listeners.EventType;
import wizard_team.wizards_tale.appwarp_listeners.NotificationEventType;
import wizard_team.wizards_tale.systems.ClientSystem;
import wizard_team.wizards_tale.systems.ServerSystem;

import static wizard_team.wizards_tale.appwarp_listeners.NotificationEventType.*;

public class MPGameScreen extends SinglePlayerScreen implements Screen, Observer {
    private static final float TICK_INTERVAL = 0.1f;
    private final boolean isHost;
    private final String username;
    private WarpClient warpClient;
    private ArrayList<ChatMessage> chatMessages;
    private Array<String> roomUsers = new Array<String>();
    private List<String> userList;
    private GameConfig gameConfig;
    private String tag = "MPGameScreen";
    private boolean gameStarted = false;

    public MPGameScreen(WizardsTaleGame game, GameConfig gameConfig, String username) {
        super(game);
        Gdx.app.log(tag, "Starting online game...");
        this.username = username;
        this.isHost = username.equals(gameConfig.hostUsername);
        this.gameConfig = gameConfig;
        userList = new List<String>(skin);
        warpClient = game.getWarpClient();
        Gdx.input.setInputProcessor(this.stage);

        game.awListeners.notificationListener.addObserver(this);
        game.awListeners.zoneRequestListener.addObserver(this);

        engine = new Engine();
        if (isHost) {
            // Set up the required entities
            createEngineHost(engine);
        }
        // If not the host, just wait for the server to send a state snapshot
        addMultiplayerSystems(engine);
    }

    private void createEngineHost(Engine engine) {
    }

    private void addMultiplayerSystems(Engine engine) {
        engine.addSystem(new ClientSystem(TICK_INTERVAL, warpClient, touchpad, TimeUtils.millis()));
        if (isHost) {
            Gdx.app.log(tag, "This is the host. Adding a ServerSystem.");
            engine.addSystem(new ServerSystem(TICK_INTERVAL, warpClient));
        } else {
            Gdx.app.log(tag, "This is " + username + ". The host is " + gameConfig.hostUsername + ".");
        }
    }

    private Stage createStage(Viewport viewport) {
        Stage stage = new Stage(viewport);

        Table rootTable = new Table();
        stage.addActor(rootTable);
        rootTable.setFillParent(true);
        rootTable.setDebug(true);

        Label title = new Label("Multiplayer Game", skin);
        rootTable.add(title).top();

        rootTable.row();
        rootTable.add(new Label(gameConfig.print(), skin));

        return stage;
    }

    @Override
    public void update(Observable observable, Object o) {
        Tuple tup = (Tuple) o;
        EventType type = (EventType) tup.getValue(0);
        Gdx.app.log(tag, "Received: " + tup.toString());

        if (type instanceof NotificationEventType) {
            switch ((NotificationEventType) type) {
                case UPDATE_PEERS_RECEIVED:
                    UpdateEvent updatePeersReceived = (UpdateEvent) tup.getValue(1);
                    byte[] bytes = updatePeersReceived.getUpdate();
                    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                    ObjectInput in = null;

                    Triplet joystickMsg = null;

                    try {
                        in = new ObjectInputStream(bis);
                        joystickMsg = (Triplet) in.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    Gdx.app.log(tag, joystickMsg.toString());
                    break;
                default:
                    Gdx.app.log(tag, "Unhandled NotificationEventType: " + tup);
            }
        }
    }
}

