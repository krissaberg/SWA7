package wizard_team.wizards_tale.screens;

import com.badlogic.gdx.Screen;

import wizard_team.wizards_tale.WizardsTaleGame;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
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

import wizard_team.wizards_tale.components.CounterComponent;
import wizard_team.wizards_tale.components.GameTimeComponent;

import wizard_team.wizards_tale.components.BoundRectComponent;
import wizard_team.wizards_tale.components.CellBoundaryComponent;
import wizard_team.wizards_tale.components.CellPositionComponent;
import wizard_team.wizards_tale.components.CollisionComponent;
import wizard_team.wizards_tale.components.PositionComponent;
import wizard_team.wizards_tale.components.SpriteComponent;
import wizard_team.wizards_tale.components.ReceiveInputComponent;

import com.badlogic.gdx.math.MathUtils;

import wizard_team.wizards_tale.components.RandomMovementComponent;
import wizard_team.wizards_tale.components.VelocityComponent;
import wizard_team.wizards_tale.systems.CountDownSystem;
import wizard_team.wizards_tale.systems.GameCycleSystem;
import wizard_team.wizards_tale.components.constants.Constants;
import wizard_team.wizards_tale.systems.BombRenderSystem;
import wizard_team.wizards_tale.systems.CellPositionSystem;
import wizard_team.wizards_tale.systems.CellRenderSystem;
import wizard_team.wizards_tale.systems.CellDebugRenderSystem;
import wizard_team.wizards_tale.systems.RenderSystem;
import wizard_team.wizards_tale.systems.VelocityMovementSystem;
import wizard_team.wizards_tale.systems.RandomWalkerSystem;
import wizard_team.wizards_tale.systems.InputSystem;

public class SinglePlayerScreen implements Screen {
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
    private float gameTimeLeft;

  Engine engine;
  Touchpad touchpad;

    private Texture whiteMageTex;
    private Texture blackMageTex;
    private Texture wallTexture;
    private Texture bombTexture;
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
        assetManager.finishLoading();
        blackMageTex = assetManager.get("sprites/black_mage.png", Texture.class);
        whiteMageTex = assetManager.get("sprites/white_mage.png", Texture.class);
        wallTexture = assetManager.get("sprites/mountain.png", Texture.class);
        bombTexture = assetManager.get("sprites/bomb.png", Texture.class);

        // Create engine
        this.engine = createEngine();
    }

  public void setGameTimeLeft(float gameTimeLeft) {
    this.gameTimeLeft = gameTimeLeft;
  }

  private Engine createEngine() {
    Engine eng = new Engine();

        // Player character entity
        Entity playerCharacter = new Entity();

        //Positions
        playerCharacter.add(new PositionComponent(100, 200));
        playerCharacter.add(new CellPositionComponent(0,0));

        playerCharacter.add(new VelocityComponent());
        playerCharacter.add(new SpriteComponent(blackMageTex));
        playerCharacter.add(new ReceiveInputComponent());
        playerCharacter.add(new BoundRectComponent(new Rectangle(0, 0,
                blackMageTex.getWidth(), blackMageTex.getHeight())));
        playerCharacter.add(new CollisionComponent(Constants.CollidableType.SOFT));
        eng.addEntity(playerCharacter);

        // Random walkers
        for (int i = 0; i < 10; i++) {
            Entity walker = new Entity();
            walker.add(new PositionComponent(MathUtils.random(700), MathUtils.random(500)));
            walker.add(new SpriteComponent(whiteMageTex));
            walker.add(new RandomMovementComponent(MathUtils.random(3)));
            walker.add(new VelocityComponent());
            walker.add(new BoundRectComponent(new Rectangle(0, 0,
                    whiteMageTex.getWidth(), whiteMageTex.getHeight())));
            walker.add(new CollisionComponent(Constants.CollidableType.SOFT));
            eng.addEntity(walker);
        }

    // Clock for Game Cycle entity
    Entity clock = new Entity();
    clock.add(new CounterComponent(30));
    clock.add(new GameTimeComponent());
    eng.addEntity(clock);


    // Systems
    eng.addSystem(new RandomWalkerSystem());
    eng.addSystem(new VelocityMovementSystem());
    eng.addSystem(new RenderSystem(spriteBatch));
    eng.addSystem(new InputSystem(touchpad));
    eng.addSystem(new CountDownSystem());
    eng.addSystem(new GameCycleSystem(game, this));
        // Walls
        Entity wall = new Entity();
        wall.add(new BoundRectComponent(new Rectangle(200, 200, 100, 50)));
        wall.add(new PositionComponent(200, 200));
        wall.add(new SpriteComponent(wallTexture));
        wall.add(new CollisionComponent(Constants.CollidableType.HARD));
        eng.addEntity(wall);

        // Cells
        Rectangle rect = new Rectangle(
                0, 0, Constants.CELL_WIDTH, Constants.CELL_HEIGHT);
        for (int x = 0; x<5; x++) {
            for (int y = 0; y < 5; y++) {
                Entity tile = new Entity();
                tile.add(new CellBoundaryComponent(rect));
                tile.add(new CellPositionComponent(
                        (int) (x * Constants.CELL_WIDTH), (int) (y * Constants.CELL_WIDTH)));

                if (x % 2 == 0 && y % 2 == 0) {
                    tile.add(new CollisionComponent(Constants.CollidableType.SOFT));
                } else {
                    tile.add(new CollisionComponent(Constants.CollidableType.HARD));
                }
                eng.addEntity(tile);
            }
        }


        // Systems
        eng.addSystem(new RandomWalkerSystem());
        eng.addSystem(new VelocityMovementSystem());
        eng.addSystem(new RenderSystem(spriteBatch));
        inputSystem = new InputSystem(touchpad, bombButton);
        eng.addSystem(inputSystem);

        eng.addSystem(new CellPositionSystem());
        eng.addSystem(new CellRenderSystem(spriteBatch));
        eng.addSystem(new CellDebugRenderSystem(spriteBatch));
        eng.addSystem(new BombRenderSystem(spriteBatch, bombTexture));

        return eng;
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
    this.touchpad = touchpad;

    //Show gametime
    Label gameTime = new Label(gameTimeLeft + "", skin);
    rootTable.add(gameTime).bottom().center();

    rootTable.add(new Table()).expandX();
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

        engine.update(dt);

        stage.act(dt);
        stage.draw();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
    }
}
