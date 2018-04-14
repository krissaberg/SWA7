package wizard_team.wizards_tale;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;

import wizard_team.wizards_tale.appwarp_listeners.WTZoneRequestListener;
import wizard_team.wizards_tale.appwarp_listeners.WTConnectionListener;
import wizard_team.wizards_tale.screens.MainMenuScreen;

public class WizardsTaleGame extends Game {
    private SpriteBatch spriteBatch;
    private AssetManager assetManager;
    private Skin skin;
    public WarpClient warpClient;
    public String username = "";

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

        // Set up AppWarp (online multiplayer)
        WarpClient.initialize(
                "c808b24e0b1b2916071c8cf3da43987d5a4a37566347ac6e0ab439bf99fe984e",
                "f524795192c81a650ad43593d743a6022db74fba9bc1b5b730f3728ef5a5194d"
        );
        try {
            warpClient = WarpClient.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        warpClient.addConnectionRequestListener(new WTConnectionListener());
        warpClient.addZoneRequestListener(new WTZoneRequestListener());

        System.out.println("connecting");
        username = "wizzy";

        warpClient.connectWithUserName(username);

        setScreen(new MainMenuScreen(this, spriteBatch, skin, assetManager));

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
}
