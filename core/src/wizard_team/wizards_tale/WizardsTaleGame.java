package wizard_team.wizards_tale;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.UpdateRequestListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import wizard_team.wizards_tale.appwarp_listeners.ConnectionEventListener;
import wizard_team.wizards_tale.appwarp_listeners.WTChatRequestListener;
import wizard_team.wizards_tale.appwarp_listeners.WTLobbyRequestListener;
import wizard_team.wizards_tale.appwarp_listeners.WTNotificationListener;
import wizard_team.wizards_tale.appwarp_listeners.WTRoomRequestListener;
import wizard_team.wizards_tale.appwarp_listeners.WTUpdateRequestListener;
import wizard_team.wizards_tale.appwarp_listeners.WTZoneRequestListener;
import wizard_team.wizards_tale.appwarp_listeners.WTConnectionListener;
import wizard_team.wizards_tale.screens.MPLobbyScreen;
import wizard_team.wizards_tale.screens.MainMenuScreen;

public class WizardsTaleGame extends Game {
    private SpriteBatch spriteBatch;
    private AssetManager assetManager;
    private Skin skin;
    public WarpClient warpClient;
    public String username = "";
    private Properties gameProperties = new Properties();
    private ArrayList<String> consoleMessages = new ArrayList<String>();

    public final WTConnectionListener connectionEventListener = new WTConnectionListener();

    public void addConsoleMsg(String s) {
        consoleMessages.add(s);
    }

    public ArrayList<String> getConsoleMessages() {
        return consoleMessages;
    }

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

            Properties placeholderProperties = new Properties();
            placeholderProperties.setProperty("api_key", "Put the AppWarp API key here");
            placeholderProperties.setProperty("secret_key", "Put the AppWarp secret key here");

            try {
                FileOutputStream out = new FileOutputStream("game.properties");
                placeholderProperties.store(out,
                        "Placeholder game configuration file");
                out.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            e.printStackTrace();
            System.exit(2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Then, actually initialize the WarpClient.
        System.out.println("Initializing WarpClient");
        WarpClient.initialize(
                gameProperties.getProperty("api_key"),
                gameProperties.getProperty("secret_key")
        );
        System.out.println("Done initializing WarpClient");
        try {
            warpClient = WarpClient.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(warpClient.getConnectionState());

        // Add event callbacks to the WarpClient
        warpClient.addChatRequestListener(new WTChatRequestListener());
        warpClient.addConnectionRequestListener(connectionEventListener);
        warpClient.addLobbyRequestListener(new WTLobbyRequestListener());
        warpClient.addNotificationListener(new WTNotificationListener());
        warpClient.addRoomRequestListener(new WTRoomRequestListener());
        warpClient.addUpdateRequestListener(new WTUpdateRequestListener());
        warpClient.addZoneRequestListener(new WTZoneRequestListener());

        setScreen(new MainMenuScreen(this, spriteBatch, skin, assetManager));
//        setScreen(new MPLobbyScreen(this));
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

    public WarpClient getWarpClient() {
        return warpClient;
    }
}
