package wizard_team.wizards_tale;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;

import wizard_team.wizards_tale.screens.MainMenuScreen;
import wizard_team.wizards_tale.screens.SinglePlayerScreen;

public class WizardsTaleGame extends Game {
  private SpriteBatch spriteBatch;
  private AssetManager assetManager;
  private Skin skin;

  public WarpClient getWarpClient() {
    return warpClient;
  }

  private WarpClient warpClient;

  @Override
  public void create() {
    spriteBatch = new SpriteBatch();
    assetManager = new AssetManager();

    // Load shared assets
    assetManager.load("menuscreen.png", Texture.class);
    assetManager.load("menu_background.png", Texture.class);
    assetManager.load("playbackground.png", Texture.class);
    assetManager.load("sprites/black_mage.png", Texture.class);
    assetManager.load("sprites/bomb.png", Texture.class);
    assetManager.load("sprites/explosion.png", Texture.class);
    assetManager.load("sprites/mountain.png", Texture.class);
    assetManager.load("sprites/powerup_amount.png", Texture.class);
    assetManager.load("sprites/powerup_power.png", Texture.class);
    assetManager.load("sprites/powerup_range.png", Texture.class);
    assetManager.load("sprites/powerup_speed.png", Texture.class);
    assetManager.load("sprites/soft_wall.png", Texture.class);
    assetManager.load("sprites/white_mage.png", Texture.class);
    assetManager.load("gdx-skins-master/neon/skin/neon-ui.json", Skin.class);

    // Block until shared assets are loaded
    assetManager.finishLoading();
    skin = assetManager.get("gdx-skins-master/neon/skin/neon-ui.json", Skin.class);

//    setScreen(new MainMenuScreen(this, spriteBatch, skin, assetManager));
    setScreen(new MainMenuScreen(this));
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
