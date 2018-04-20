package wizard_team.wizards_tale.screens;

import com.badlogic.gdx.Screen;

import wizard_team.wizards_tale.WizardsTaleGame;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
import wizard_team.wizards_tale.components.BoundRectComponent;
import wizard_team.wizards_tale.components.CellBoundaryComponent;
import wizard_team.wizards_tale.components.CellPositionComponent;
import wizard_team.wizards_tale.components.CollideableComponent;
import wizard_team.wizards_tale.components.CollisionComponent;
import wizard_team.wizards_tale.components.DestroyableComponent;
import wizard_team.wizards_tale.components.PositionComponent;
import wizard_team.wizards_tale.components.SpriteComponent;
import wizard_team.wizards_tale.components.ReceiveInputComponent;

import com.badlogic.gdx.math.MathUtils;

import wizard_team.wizards_tale.components.RandomMovementComponent;
import wizard_team.wizards_tale.components.VelocityComponent;
import wizard_team.wizards_tale.components.constants.Constants;
import wizard_team.wizards_tale.systems.BombSystem;
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

    private Texture whiteMageTex;
    private Texture blackMageTex;
    private Texture wallTexture;
    private Texture softWallTexture;
    private Texture bombTexture;
    private Texture explosionTexture;
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
        assetManager.finishLoading();

        blackMageTex = assetManager.get("sprites/black_mage.png", Texture.class);
        whiteMageTex = assetManager.get("sprites/white_mage.png", Texture.class);
        wallTexture = assetManager.get("sprites/mountain.png", Texture.class);
        bombTexture = assetManager.get("sprites/bomb.png", Texture.class);
        explosionTexture = assetManager.get("sprites/explosion.png", Texture.class);
        softWallTexture = assetManager.get("sprites/soft_wall.png", Texture.class);

        // Create engine
        this.engine = createEngine();
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
        playerCharacter.add(new BoundRectComponent(new Rectangle(0, 0,
                blackMageTex.getWidth(), blackMageTex.getHeight())));
        playerCharacter.add(new CollisionComponent(Constants.CollidableType.SOFT));
        playerCharacter.add(new BombLayerComponent(Constants.DEFAULT_BOMB_RANGE, Constants.DEFAULT_BOMB_DEPTH, Constants.DEFAULT_BOMB_DAMAGE));
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

        // Tiles
        Rectangle rect = new Rectangle(
                0, 0, Constants.CELL_WIDTH, Constants.CELL_HEIGHT);

        // MAP_X and MAP_Y define the Map-grid
        createMap(eng);

        // Systems
        eng.addSystem(new RandomWalkerSystem());
        eng.addSystem(new VelocityMovementSystem());
        eng.addSystem(new RenderSystem(spriteBatch));
        inputSystem = new InputSystem(touchpad, bombButton);
        eng.addSystem(inputSystem);

        eng.addSystem(new CellPositionSystem());
        eng.addSystem(new CellRenderSystem(spriteBatch));
        eng.addSystem(new CellDebugRenderSystem(spriteBatch));

        //Bomb Systems
        eng.addSystem(new BombSystem(bombTexture));
        eng.addSystem(new ExplosionSystem(explosionTexture));
        eng.addSystem(new TimedRenderSystem(spriteBatch));

        return eng;
    }

    private Stage createStage(Viewport viewport) {
        Stage stage = new Stage(viewport);

        Table rootTable = new Table();
        stage.addActor(rootTable);
        rootTable.setFillParent(true);
        rootTable.setDebug(true);
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

        engine.update(dt);

        stage.act(dt);
        stage.draw();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
    }

    public void createMap(Engine eng){
        for (int x = 0; x < Constants.MAP_X; x++) {
            for (int y = 0; y < Constants.MAP_Y; y++) {

                //Don't place something on top left or bottom right (player start)
                if (x==0 & y==Constants.MAP_Y-1 || x==Constants.MAP_X & y==0) {continue;}
                Entity tile = new Entity();
                tile.add(new CellPositionComponent(x,y));
                tile.add(new DestroyableComponent(0));
                tile.add(new CollideableComponent(0));

                //Collision
                //tile.add(new BoundRectComponent(new Rectangle(x, y, Constants.CELL_WIDTH, Constants.CELL_HEIGHT)));
                //tile.add(new CollisionComponent(Constants.CollidableType.HARD));

                //For rendering requires pos
                tile.add(new PositionComponent(
                        (int) (x * Constants.CELL_WIDTH), (int) (y * Constants.CELL_HEIGHT)));

                // Place blocks
                if (x % 2 == 0 && y % 2 == 0) {
                    // Place hard blocks
                    tile.add(new SpriteComponent(wallTexture));
                    tile.add(new CollideableComponent(Constants.HARD_BLOCK_HEIGHT));
                } else {
                    // Place soft blocks
                    tile.add(new SpriteComponent(softWallTexture));
                    tile.add(new DestroyableComponent(Constants.DEFAULT_BLOCK_HP));
                    tile.add(new CollideableComponent(Constants.SOFT_BLOCK_HEIGHT));
                }
                eng.addEntity(tile);
            }
        }
    }
}