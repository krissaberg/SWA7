package wizard_team.wizards_tale;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import wizard_team.wizards_tale.screens.MainMenuScreen;

public class WizardsTaleGame extends Game {

  SpriteBatch spriteBatch;
  AssetManager assetManager;
  Skin skin;

  @Override
  public void create() {
    spriteBatch = new SpriteBatch();
    assetManager = new AssetManager();

    // Load shared assets
    assetManager.load("menu_background.png", Texture.class);
    assetManager.load("gdx-skins-master/neon/skin/neon-ui.json", Skin.class);

    // Block until shared assets are loaded
    assetManager.finishLoading();
    skin = assetManager.get("gdx-skins-master/neon/skin/neon-ui.json", Skin.class);

    setScreen(new MainMenuScreen(this));
  }

  @Override
  public void render() {
    super.render();
    // Gdx.gl.glClearColor(1, 0, 0, 1);
    // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    // spriteBatch.begin();
    // spriteBatch.end();
  }

  @Override
  public void dispose() {
    spriteBatch.dispose();
    assetManager.dispose();
  }

  public SpriteBatch getSpriteBatch() {
    return spriteBatch;
  }

  public AssetManager getAssetManager() {
    return assetManager;
  }

  public Skin getSkin() {
    return skin;
  }
}
