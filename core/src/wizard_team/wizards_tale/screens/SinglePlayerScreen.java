package wizard_team.wizards_tale.screens;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.Screen;

import wizard_team.wizards_tale.WizardsTaleGame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

import wizard_team.wizards_tale.components.BombLayerComponent;
import wizard_team.wizards_tale.components.CounterComponent;
import wizard_team.wizards_tale.components.GameTimeComponent;

import wizard_team.wizards_tale.components.BoundRectComponent;
import wizard_team.wizards_tale.components.CellPositionComponent;
import wizard_team.wizards_tale.components.CollideableComponent;
import wizard_team.wizards_tale.components.DestroyableComponent;
import wizard_team.wizards_tale.components.PositionComponent;
import wizard_team.wizards_tale.components.ScoreComponent;
import wizard_team.wizards_tale.components.SpriteComponent;
import wizard_team.wizards_tale.components.ReceiveInputComponent;

import wizard_team.wizards_tale.components.VelocityComponent;
import wizard_team.wizards_tale.systems.CountDownSystem;
import wizard_team.wizards_tale.systems.GameCycleSystem;
import wizard_team.wizards_tale.components.constants.Constants;
import wizard_team.wizards_tale.systems.PowerupRenderSystem;
import wizard_team.wizards_tale.systems.PowerupSystem;
import wizard_team.wizards_tale.systems.TimedRenderSystem;
import wizard_team.wizards_tale.systems.CellPositionSystem;
import wizard_team.wizards_tale.systems.CellRenderSystem;
import wizard_team.wizards_tale.systems.CellDebugRenderSystem;
import wizard_team.wizards_tale.systems.ExplosionSystem;
import wizard_team.wizards_tale.systems.RenderSystem;
import wizard_team.wizards_tale.systems.VelocityMovementSystem;
import wizard_team.wizards_tale.systems.RandomWalkerSystem;
import wizard_team.wizards_tale.systems.InputSystem;

public class SinglePlayerScreen implements Screen {
    public boolean isAlive = true;

    private WizardsTaleGame game;
    private Skin skin;
    private Stage stage;
    private SpriteBatch spriteBatch;
    private AssetManager assetManager;
    private Viewport viewport;
    private Camera camera;

    private Engine engine;
    private Touchpad touchpad;
    private Button bombButton;
    private TextButton clockbutton;
    private float gameTimeLeft;

    private Texture whiteMageTex;
    private Texture blackMageTex;
    private Texture wallTexture;
    private Texture softWallTexture;
    private Texture bombTexture;
    private Texture explosionTexture;
    private Texture playbackground;


    private Texture speedTexture;
    private Texture rangeTexture;
    private Texture powerTexture;
    private Texture amountTexture;

    private Texture powerupTexture;
    private ComponentMapper<DestroyableComponent> destroyableMapper =
            ComponentMapper.getFor(DestroyableComponent.class);


    private InputSystem inputSystem;

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
        assetManager.load("sprites/mountain.png", Texture.class);
        assetManager.load("sprites/bomb.png", Texture.class);
        assetManager.load("sprites/explosion.png", Texture.class);
        assetManager.load("sprites/soft_wall.png", Texture.class);

        assetManager.load("sprites/powerup_speed.png", Texture.class);
        assetManager.load("sprites/powerup_power.png", Texture.class);
        assetManager.load("sprites/powerup_range.png", Texture.class);
        assetManager.load("sprites/powerup_amount.png", Texture.class);
        assetManager.load("playbackground.png", Texture.class);
        assetManager.finishLoading();

        blackMageTex = assetManager.get("sprites/black_mage.png", Texture.class);
        whiteMageTex = assetManager.get("sprites/white_mage.png", Texture.class);
        wallTexture = assetManager.get("sprites/mountain.png", Texture.class);
        bombTexture = assetManager.get("sprites/bomb.png", Texture.class);
        explosionTexture = assetManager.get("sprites/explosion.png", Texture.class);
        softWallTexture = assetManager.get("sprites/soft_wall.png", Texture.class);
        speedTexture = assetManager.get("sprites/powerup_speed.png", Texture.class);
        powerTexture = assetManager.get("sprites/powerup_power.png", Texture.class);
        rangeTexture = assetManager.get("sprites/powerup_range.png", Texture.class);
        amountTexture = assetManager.get("sprites/powerup_amount.png", Texture.class);
        playbackground = assetManager.get("playbackground.png", Texture.class);



        // Create engine
        this.engine = createEngine();
    }

    public void setGameTimeLeft(float gameTimeLeft) {
        this.gameTimeLeft = gameTimeLeft;
        clockbutton.setText("" + Math.round(gameTimeLeft) + " seconds left");
    }

    private Engine createEngine() {
        Engine eng = new Engine();

        // Player character entity
        Entity playerCharacter = new Entity();

        //Positions

        //Start player in top left
        playerCharacter.add(new PositionComponent(0, (Constants.MAP_Y-1)*Constants.CELL_HEIGHT));
        playerCharacter.add(new CellPositionComponent(0, Constants.MAP_Y));
        playerCharacter.add(new VelocityComponent());
        playerCharacter.add(new SpriteComponent(blackMageTex));
        playerCharacter.add(new ReceiveInputComponent());
        Rectangle playerBound = new Rectangle(0, 0, blackMageTex.getWidth(), blackMageTex.getHeight());
        playerCharacter.add(new BoundRectComponent(playerBound));
        playerCharacter.add(new CollideableComponent(0, Constants.CollideableType.SOFT));
        playerCharacter.add(new BombLayerComponent(Constants.DEFAULT_BOMB_RANGE, Constants.DEFAULT_BOMB_DEPTH, Constants.DEFAULT_BOMB_DAMAGE, Constants.DEFAULT_MAX_BOMBS));
        //TODO: put back in, handle death
        playerCharacter.add(new DestroyableComponent(Constants.DEFAULT_PLAYER_HP));
        playerCharacter.add(new ScoreComponent(0, 0));

        eng.addEntity(playerCharacter);

        // Tiles
        Rectangle rect = new Rectangle(
                0, 0, Constants.CELL_WIDTH, Constants.CELL_HEIGHT);

        // MAP_X and MAP_Y define the Map-grid
        createMap(eng);

        // Clock for Game Cycle entity
        Entity clock = new Entity();
        clock.add(new CounterComponent(10));
        clock.add(new GameTimeComponent());
        eng.addEntity(clock);

        // Systems
        eng.addSystem(new CountDownSystem());
        eng.addSystem(new GameCycleSystem(game, this));

        eng.addSystem(new RandomWalkerSystem());
        eng.addSystem(new VelocityMovementSystem());
        eng.addSystem(new RenderSystem(spriteBatch));
        inputSystem = new InputSystem(touchpad, bombButton);
        eng.addSystem(inputSystem);

        eng.addSystem(new CellPositionSystem());
        eng.addSystem(new CellRenderSystem(spriteBatch));
        eng.addSystem(new CellDebugRenderSystem(spriteBatch));

        //Bomb Systems
        //eng.addSystem(new BombSystem(bombTexture));
        eng.addSystem(new ExplosionSystem(bombTexture, explosionTexture, this));
        eng.addSystem(new TimedRenderSystem(spriteBatch));

        // PU
        eng.addSystem(new PowerupRenderSystem(speedTexture,amountTexture,rangeTexture,powerTexture));
        eng.addSystem(new PowerupSystem());


        return eng;
    }

    private Stage createStage(Viewport viewport) {
        Stage stage = new Stage(viewport);
        Table topTable = new Table();
        stage.addActor(topTable);
        topTable.setFillParent(true);
        topTable.center().top();

        //Show gametime
        clockbutton = new TextButton(gameTimeLeft + "seconds left", skin);
        clockbutton.setBounds(0,0, 400, 400);
        clockbutton.setTouchable(Touchable.disabled);
        topTable.add(clockbutton).width(200).height(50);

        Table rootTable = new Table();
        stage.addActor(rootTable);
        rootTable.setFillParent(true);
        //rootTable.setDebug(true);
        rootTable.left().bottom();

        Touchpad touchpad = new Touchpad(15, skin);
        rootTable.add(touchpad).bottom().left();
        this.touchpad = touchpad;
        rootTable.add(new Table()).expandX();

        Button bombButton = new TextButton("Place\nbomb", skin);
        this.bombButton = bombButton;
        rootTable.add(bombButton).bottom().right();
        this.bombButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                inputSystem.setBombButtonPressed();
            }
        });

        return stage;
    }

    public void dispose() {
    }

    public void hide() {
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public void resume() {
    }

    public void pause() {
    }

    public void render(float dt) {
        Gdx.gl.glClearColor(0.1f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //TODO Update server if player with your username is dead.
        //^ The HP of player entity == 0 when dead.
        if (!isAlive) {
            game.setScreen(new FinishedScreen(game, "GAME OVER!", 4));
            //TODO Find remaining players in multiplayerScreen and pass it to "place"
        }

        engine.update(dt);
        stage.act(dt);
        stage.draw();
        //spriteBatch.begin();
        //spriteBatch.draw(playbackground, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        //spriteBatch.end();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
    }

    public void createMap(Engine eng){
        for (int x = 0; x < Constants.MAP_X; x++) {
            for (int y = 0; y < Constants.MAP_Y; y++) {
                Entity tile = new Entity();
                tile.add(new CellPositionComponent(x,y));
                tile.add(new DestroyableComponent(0));

                //TODO: Don't place something on top left L or bottom right L (players start)
                if (0<=x && x<=1 & Constants.MAP_Y-2 <= y && y<= Constants.MAP_Y-1|| x==Constants.MAP_X & y==0) {
                    tile.add(new CollideableComponent(0, Constants.CollideableType.NONE));
                } else {
                    //Collision
                    tile.add(new BoundRectComponent(new Rectangle(x*Constants.CELL_WIDTH, y*Constants.CELL_HEIGHT, Constants.CELL_WIDTH, Constants.CELL_HEIGHT)));

                    //For rendering requires pos
                    tile.add(new PositionComponent(
                            (int) (x * Constants.CELL_WIDTH), (int) (y * Constants.CELL_HEIGHT)));

                    // Place blocks
                    if (x % 2 == 0 && y % 2 == 0) {
                        // Place hard blocks
                        tile.add(new SpriteComponent(wallTexture));
                        tile.add(new CollideableComponent(Constants.HARD_BLOCK_HEIGHT,Constants.CollideableType.HARD));
                    } else {
                        // Place soft blocks
                        tile.add(new SpriteComponent(softWallTexture));
                        tile.add(new DestroyableComponent(Constants.DEFAULT_BLOCK_HP));
                        tile.add(new CollideableComponent(Constants.SOFT_BLOCK_HEIGHT, Constants.CollideableType.HARD));
                    }
                }
                eng.addEntity(tile);
            }
        }
    }
}
