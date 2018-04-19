package wizard_team.wizards_tale.screens;

import com.badlogic.gdx.Game;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import wizard_team.wizards_tale.WizardsTaleGame;

public class MPGameScreen extends SinglePlayerScreen implements Screen, Observer {
    private Stage stage;
    private Viewport viewport;
    private WarpClient warpClient;
    private ArrayList<ChatMessage> chatMessages;
    private Array<String> roomUsers = new Array<String>();
    private List<String> userList;
    private GameConfig gameConfig;

    public MPGameScreen(WizardsTaleGame game, GameConfig gameConfig) {
        super(game);
        this.gameConfig = gameConfig;
        userList = new List<String>(skin);
        game.awListeners.zoneRequestListener.addObserver(this);
        Camera camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);
        viewport.apply(true);
        stage = createStage(viewport);
        warpClient = game.getWarpClient();
        Gdx.input.setInputProcessor(this.stage);
        game.awListeners.notificationListener.addObserver(this);
    }

    private Stage createStage(Viewport viewport) {
        Stage stage = new Stage(viewport);

        Table rootTable = new Table();
        stage.addActor(rootTable);
        rootTable.setFillParent(true);
        rootTable.setDebug(true);

        Label title = new Label("Multiplayer Game", skin);
        rootTable.add(title).top();

        rootTable.row();
        rootTable.add(new Label(gameConfig.print(), skin));

        return stage;
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}

