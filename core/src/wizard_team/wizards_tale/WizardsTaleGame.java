package wizard_team.wizards_tale;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.listener.UpdateRequestListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import wizard_team.wizards_tale.appwarp_listeners.WTChatRequestListener;
import wizard_team.wizards_tale.appwarp_listeners.WTLobbyRequestListener;
import wizard_team.wizards_tale.appwarp_listeners.WTNotificationListener;
import wizard_team.wizards_tale.appwarp_listeners.WTRoomRequestListener;
import wizard_team.wizards_tale.appwarp_listeners.WTUpdateRequestListener;
import wizard_team.wizards_tale.appwarp_listeners.WTZoneRequestListener;
import wizard_team.wizards_tale.appwarp_listeners.WTConnectionListener;
import wizard_team.wizards_tale.screens.MainMenuScreen;

public class WizardsTaleGame extends Game {
    private SpriteBatch spriteBatch;
    private AssetManager assetManager;
    private Skin skin;
    public WarpClient warpClient;
    public String username = "";
    private Properties gameProperties = new Properties();

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

        // Set up AppWarp (online multiplayer). First, load API and private keys from file:
        FileInputStream in;
        try {
            in = new FileInputStream("game.properties");
            gameProperties.load(in);
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("File 'game.properties' not found in assets folder.");
            System.out.println("Creating placeholder file. You have to update it with the AppWarp keys!");
            e.printStackTrace();
            System.exit(2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Then, actually initialize the WarpClient.
        WarpClient.initialize(
                gameProperties.getProperty("api_key"),
                gameProperties.getProperty("secret_key")
        );
        try {
            warpClient = WarpClient.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add event callbacks to the WarpClient
        warpClient.addChatRequestListener(new WTChatRequestListener());
        warpClient.addConnectionRequestListener(new WTConnectionListener());
        warpClient.addLobbyRequestListener(new WTLobbyRequestListener());
        warpClient.addNotificationListener(new WTNotificationListener());
        warpClient.addRoomRequestListener(new WTRoomRequestListener());
        warpClient.addUpdateRequestListener(new WTUpdateRequestListener());
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
