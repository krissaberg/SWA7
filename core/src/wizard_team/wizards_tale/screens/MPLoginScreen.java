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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;

import org.javatuples.Tuple;

import java.util.Observable;
import java.util.Observer;

import wizard_team.wizards_tale.WizardsTaleGame;
import wizard_team.wizards_tale.appwarp_listeners.ConnectionRequestEventType;
import wizard_team.wizards_tale.appwarp_listeners.EventType;

public class MPLoginScreen implements Screen, Observer {
    private WizardsTaleGame game;
    private SpriteBatch spriteBatch;
    private AssetManager assetManager;
    private Skin skin;
    private Stage stage;
    private Viewport viewport;
    private String consoleStr = "";
    private WarpClient warpClient;
    private boolean isConnected = false;
    private boolean isConnecting = false;
    private boolean connectionFailed = false;
    private Label errorLabel;
    private TextField usernameArea;
    private String tag = "MPLoginScreen";

    MPLoginScreen(WizardsTaleGame game) {
        this.game = game;
        spriteBatch = game.getSpriteBatch();
        assetManager = game.getAssetManager();
        skin = game.getSkin();
        Camera camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);
        viewport.apply(true);
        stage = createStage(viewport);
        warpClient = game.getWarpClient();
        Gdx.input.setInputProcessor(this.stage);
        game.awListeners.connectionListener.addObserver(this);
    }

    private Stage createStage(Viewport viewport) {
        Stage stage = new Stage(viewport);

        Table rootTable = new Table();
        stage.addActor(rootTable);
        rootTable.setFillParent(true);
        rootTable.setDebug(true);
        rootTable.top();

        Label title = new Label("Sign in", skin);
        rootTable.add(title).top();
        rootTable.row();

        Table signInRow = new Table();
        rootTable.add(signInRow);

        usernameArea = new TextArea("Bob", skin);
        signInRow.add(usernameArea);

        final TextButton loginButton = new TextButton("Sign in", skin);
        signInRow.add(loginButton);
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isConnecting) {
                    isConnecting = true;
                    System.out.println("Trying to log in as " + usernameArea.getText());
                    warpClient.connectWithUserName(usernameArea.getText());
                    errorLabel.setText("Connecting...");
                }
            }
        });

        errorLabel = new Label("", skin);
        rootTable.row();
        rootTable.add(errorLabel).center();

        return stage;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        if (isConnected) {
            game.setScreen(new MPRoomSelect(game, usernameArea.getText()));
        }
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
        Tuple tup = (Tuple) o;
        EventType type = (EventType) tup.getValue(0);
        if (type instanceof ConnectionRequestEventType) {
            switch ((ConnectionRequestEventType) type) {
                case CONNECT_DONE:
                    ConnectEvent connectDone = (ConnectEvent) tup.getValue(1);
                    if (connectDone.getResult() == WarpResponseResultCode.SUCCESS) {
                        isConnected = true;
                        isConnecting = false;
                        connectionFailed = false;
                        errorLabel.setText("Connection succeeded.");
                    } else {
                        isConnected = false;
                        isConnecting = false;
                        connectionFailed = true;
                        errorLabel.setText("Connection failed. Try again with another username.");
                    }
                    break;
                default:
                    Gdx.app.log(
                            this.getClass().toString(),
                            "Unhandled ConnectionRequestEventType: " + tup);
            }
        } else {
            Gdx.app.log(tag, "Unexpected event type: " + o);
        }
    }
}

