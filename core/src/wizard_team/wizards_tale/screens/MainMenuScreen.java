package wizard_team.wizards_tale.screens;

import com.badlogic.gdx.Screen;
import wizard_team.wizards_tale.WizardsTaleGame;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import java.util.HashMap;

public class MainMenuScreen implements Screen {
  private WizardsTaleGame game;
  private Skin skin;
  private Stage stage;
  private SpriteBatch spriteBatch;
  private Texture backgroundTex;
  private AssetManager assetManager;
  private Viewport viewport;
  private Camera camera;

  public MainMenuScreen(
      WizardsTaleGame game, SpriteBatch spriteBatch, Skin skin, AssetManager assetManager) {
    this.assetManager = assetManager;
    this.game = game;
    this.skin = skin;
    this.spriteBatch = spriteBatch;
    this.camera = new OrthographicCamera();
    this.viewport = new FitViewport(800, 600, this.camera);
    viewport.apply(true);
    this.stage = createStage(viewport);
    Gdx.input.setInputProcessor(this.stage);

    this.backgroundTex = assetManager.get("menu_background.png", Texture.class);
  }

  private Stage createStage(Viewport viewport) {
    Stage stage = new Stage(viewport);

    Table rootTable = new Table();
    stage.addActor(rootTable);
    rootTable.setFillParent(true);
    rootTable.setDebug(true);

    final Label messageList = new Label("", skin);
    messageList.setText(
            messageList.getText() + "\nhei"
    );
    ScrollPane messageScrollPane = new ScrollPane(messageList);
    rootTable.add(messageScrollPane).expandX().expandY().bottom().left();

    Button createRoomBtn = new TextButton("Create Room", skin);
    rootTable.add(createRoomBtn);
    createRoomBtn.addListener(
            new ClickListener() {
              @Override
              public void clicked(InputEvent event, float x, float y) {
                messageList.setText(messageList.getText() + "\nTrying to create room...");
                game.warpClient.createRoom(
                        "roomby",
                        game.username,
                        4,
                        new HashMap<String, Object>());
                game.warpClient.getOnlineUsers();
              }
            }
    );

    Button lobbyButton = new TextButton("New Lobby", skin);
    rootTable.add(lobbyButton);
    lobbyButton.addListener(
            new ClickListener() {
              @Override
              public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new OnlineLobbyScreen(game));
              }
            });

    TextButton onlineGameButton = new TextButton("Online Game", skin);
    rootTable.add(onlineGameButton);
    onlineGameButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.setScreen(new MPLoginScreen(game));
      }
    });

    Button startButton = new TextButton("New Game", skin);
    rootTable.add(startButton);
    startButton.addListener(
        new ClickListener() {
          @Override
          public void clicked(InputEvent event, float x, float y) {
            game.setScreen(new SinglePlayerScreen(game, spriteBatch, skin, assetManager));
          }
        });

    return stage;
  }

  public void dispose() {}

  public void hide() {}

  public void show() {
    Gdx.input.setInputProcessor(this.stage);
  }

  public void resume() {}

  public void pause() {}

  public void render(float dt) {
    Gdx.gl.glClearColor(0.1f, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    spriteBatch.begin();
    spriteBatch.draw(backgroundTex, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
    spriteBatch.end();

    stage.act(dt);
    stage.draw();
  }

  public void resize(int width, int height) {
    viewport.update(width, height, true);
    spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
  }
}
