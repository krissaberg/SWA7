package wizard_team.wizards_tale.screens;

import com.badlogic.gdx.Screen;

import wizard_team.wizards_tale.WarpController;
import wizard_team.wizards_tale.WizardsTaleGame;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MultiPlayerRoomScreen implements Screen {

    WarpController wp;
    WizardsTaleGame game;
    Skin skin;
    Stage stage;
    SpriteBatch spriteBatch;
    Texture backgroundTex;
    AssetManager assetManager;
    Viewport viewport;
    Camera camera;

    public MultiPlayerRoomScreen(
            WizardsTaleGame game, SpriteBatch spriteBatch, Skin skin, AssetManager assetManager, WarpController wp) {

        this.wp = wp;
        this.assetManager = assetManager;
        this.game = game;
        this.skin = skin;
        this.spriteBatch = spriteBatch;
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(800, 600, this.camera);
        viewport.apply(true);
        this.stage = createStage(viewport);
        Gdx.input.setInputProcessor(this.stage);

        this.backgroundTex = assetManager.get("mp_background.png", Texture.class);
    }

    private Stage createStage(Viewport viewport) {
        Stage stage = new Stage(viewport);
        Table rootTable = new Table();
        stage.addActor(rootTable);
        rootTable.setFillParent(true);
        rootTable.setDebug(true);

        System.out.println("CONNECTION STATE: " + wp.getConnectionState());

        return stage;
    }

    public void dispose() {}

    public void hide() {}

    public void show() {
        Gdx.input.setInputProcessor(this.stage);
    }

    public void resume() {}

    public void pause() {}

    public void render(float dt) {
        Gdx.gl.glClearColor(0.1f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(backgroundTex, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        spriteBatch.end();

        stage.act(dt);
        stage.draw();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
    }
}