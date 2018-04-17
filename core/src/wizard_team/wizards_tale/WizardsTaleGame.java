package wizard_team.wizards_tale;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import wizard_team.wizards_tale.screens.MainMenuScreen;

public class WizardsTaleGame extends Game {
    private SpriteBatch spriteBatch;
    private AssetManager assetManager;
    private Skin skin;
    public WarpClient warpClient;
    public String username = "";
    private final Properties gameProperties = new Properties();
    private final ArrayList<String> consoleMessages = new ArrayList<String>();
    public final AWListeners awListeners = new AWListeners();
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

        // Get WarpClient API keys
        FileHandle gamePropFile = Gdx.files.internal("game.properties");
        try {
            gameProperties.load(gamePropFile.reader());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Initialize the WarpClient
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

        // Add event callbacks to the WarpClient
        warpClient.addChatRequestListener(awListeners.chatRequestListener);
        warpClient.addConnectionRequestListener(awListeners.connectionListener);
        warpClient.addLobbyRequestListener(awListeners.lobbyRequestListener);
        warpClient.addNotificationListener(awListeners.notificationListener);
        warpClient.addRoomRequestListener(awListeners.roomRequestListener);
        warpClient.addUpdateRequestListener(awListeners.updateRequestListener);
        warpClient.addZoneRequestListener(awListeners.zoneRequestListener);

        setScreen(new MainMenuScreen(this, spriteBatch, skin, assetManager));
//        setScreen(new MPLobbyScreen(this));
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
