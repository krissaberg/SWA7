package wizard_team.wizards_tale.screens;

import com.badlogic.gdx.Screen;

import wizard_team.wizards_tale.WizardsTaleGame;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

import wizard_team.wizards_tale.components.BoundRectComponent;
import wizard_team.wizards_tale.components.CollisionComponent;
import wizard_team.wizards_tale.components.PositionComponent;
import wizard_team.wizards_tale.components.SpriteComponent;
import wizard_team.wizards_tale.components.ReceiveInputComponent;

import com.badlogic.gdx.math.MathUtils;

import wizard_team.wizards_tale.components.RandomMovementComponent;
import wizard_team.wizards_tale.components.VelocityComponent;
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

    private Texture whiteMageTex;
    private Texture blackMageTex;
    private Texture wallTexture;

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
        assetManager.finishLoading();
        blackMageTex = assetManager.get("sprites/black_mage.png", Texture.class);
        whiteMageTex = assetManager.get("sprites/white_mage.png", Texture.class);
        wallTexture = assetManager.get("sprites/mountain.png", Texture.class);

        // Create engine
        this.engine = createEngine();
    }

    private Engine createEngine() {
        Engine eng = new Engine();

        // Player character entity
        Entity playerCharacter = new Entity();
        playerCharacter.add(new PositionComponent(100, 200));
        playerCharacter.add(new VelocityComponent());
        playerCharacter.add(new SpriteComponent(blackMageTex));
        playerCharacter.add(new ReceiveInputComponent());
        playerCharacter.add(new BoundRectComponent(new Rectangle(0, 0,
                blackMageTex.getWidth(), blackMageTex.getHeight())));
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
            eng.addEntity(walker);
        }

        // Walls
        Entity wall = new Entity();
        wall.add(new BoundRectComponent(new Rectangle(200, 200, 100, 50)));
        wall.add(new PositionComponent(200, 200));
        wall.add(new SpriteComponent(wallTexture));
        eng.addEntity(wall);

        // Systems
        eng.addSystem(new RandomWalkerSystem());
        eng.addSystem(new VelocityMovementSystem());
        eng.addSystem(new RenderSystem(spriteBatch));
        eng.addSystem(new InputSystem(touchpad));

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

        rootTable.add(new Table()).expandX();

        Button bombButton = new TextButton("Place\nbomb", skin);
        rootTable.add(bombButton).bottom().right();

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
