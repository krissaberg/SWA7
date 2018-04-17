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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;

import java.util.ArrayList;

import wizard_team.wizards_tale.WizardsTaleGame;

public class MPLobbyScreen implements Screen {
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

    public MPLobbyScreen(WizardsTaleGame game) {
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
    }

    private Stage createStage(Viewport viewport) {
        Stage stage = new Stage(viewport);

        Table rootTable = new Table();
        stage.addActor(rootTable);
        rootTable.setFillParent(true);
        rootTable.setDebug(true);

        Label messages = new Label("Hei!", skin);
        messagesLabel = messages;
        ScrollPane messagesPane = new ScrollPane(messages);
        rootTable.add(messagesPane).expandX().expandY().left();

        rootTable.row();

        final TextField inputField = new TextField("Default message", skin);
        rootTable.add(inputField).left().bottom().expandX();

        TextButton sendButton = new TextButton("Send", skin);
        rootTable.add(sendButton);
        final ArrayList<String> msgs = this.messages;
        sendButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                msgs.add(inputField.getText());
                inputField.setText("");
                warpClient.getAllRooms();
            }
        });

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

        StringBuilder s = new StringBuilder();
        for(String m : messages) {
            s.append("\n");
            s.append(m);
        }
        messagesLabel.setText(s.toString());

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

}

