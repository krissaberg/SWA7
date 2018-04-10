package wizard_team.wizards_tale.screens;

import com.badlogic.gdx.Screen;
import wizard_team.wizards_tale.WizardsTaleGame;
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
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class SinglePlayerScreen implements Screen {
  WizardsTaleGame game;
  Skin skin;
  Stage stage;
  SpriteBatch spriteBatch;
  AssetManager assetManager;
  Viewport viewport;
  Camera camera;

  Texture whiteMageTex;
  Texture blackMageTex;

  public SinglePlayerScreen(
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

    // Load assets
    assetManager.load("sprites/black_mage.png", Texture.class);
    assetManager.load("sprites/white_mage.png", Texture.class);
    assetManager.finishLoading();
    blackMageTex = assetManager.get("sprites/black_mage.png", Texture.class);
    whiteMageTex = assetManager.get("sprites/white_mage.png", Texture.class);
  }

  private Stage createStage(Viewport viewport) {
    Stage stage = new Stage(viewport);

    Table rootTable = new Table();
    stage.addActor(rootTable);
    rootTable.setFillParent(true);
    rootTable.setDebug(true);
    rootTable.left().bottom();

    Touchpad touchpad = new Touchpad(5, skin);
    rootTable.add(touchpad).bottom().left();

    rootTable.add(new Table()).expandX();

    Button bombButton = new TextButton("Place\nbomb", skin);
    rootTable.add(bombButton).bottom().right();

    return stage;
  }

  public void dispose() {}

  public void hide() {}

  public void show() {
    Gdx.input.setInputProcessor(stage);
  }

  public void resume() {}

  public void pause() {}

  public void render(float dt) {
    Gdx.gl.glClearColor(0.1f, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    spriteBatch.begin();
    spriteBatch.draw(whiteMageTex, 200, 200);
    spriteBatch.end();

    stage.act(dt);
    stage.draw();
  }

  public void resize(int width, int height) {
    viewport.update(width, height, true);
    spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
  }
}
