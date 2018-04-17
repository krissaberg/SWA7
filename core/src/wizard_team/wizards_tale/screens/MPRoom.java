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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import wizard_team.wizards_tale.WizardsTaleGame;

public class MPRoom implements Screen, Observer {
    private WizardsTaleGame game;
    private final RoomData roomData;
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
    private boolean enterRoom = false;

    public MPRoom(WizardsTaleGame game, RoomData roomData) {
        this.game = game;
        this.roomData = roomData;
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
        String tag = "MPRoom";
        Gdx.app.log(tag, o.toString());
    }
}

