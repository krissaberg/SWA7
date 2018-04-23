package wizard_team.wizards_tale.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import wizard_team.wizards_tale.WizardsTaleGame;

/**
 * Created by krist on 12/04/2018.
 */

public class FinishedScreen implements Screen{
    private String message;
    BitmapFont font = new BitmapFont();
    WizardsTaleGame game;
    Skin skin;
    Stage stage;
    SpriteBatch spriteBatch;
    Texture backgroundTex;
    AssetManager assetManager;
    Viewport viewport;
    Camera camera;
    int place;

    public FinishedScreen(WizardsTaleGame game, String message, int place){
        this.message = message;
        this.assetManager = game.getAssetManager();
        this.game = game;
        this.skin = game.getSkin();
        this.spriteBatch = game.getSpriteBatch();
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(800, 600, this.camera);
        viewport.apply(true);
        this.stage = createStage(viewport);
        this.place = place;
        Gdx.input.setInputProcessor(this.stage);

        this.backgroundTex = assetManager.get("menuscreen.png", Texture.class);
    }

    private String placeString(int place){
        int realPlace = place +1;
        if (realPlace == 1){
            return "1st place";
        }
        else if(realPlace == 2){
            return "2nd place";
        }
        else if (realPlace == 3){
            return "3rd place";
        }
        else{
            return realPlace + "th place";
        }
    }

    private Stage createStage(Viewport viewport) {
        Stage stage = new Stage(viewport);

        Table rootTable = new Table();
        stage.addActor(rootTable);
        rootTable.setFillParent(true);

        Label messageLabel = new Label(message, skin);
        Label placeLabel = new Label( placeString(place), skin);
        messageLabel.setFontScale((float) 1.5);
        placeLabel.setFontScale((float) 1.5);
        rootTable.add(messageLabel);
        rootTable.row();
        rootTable.add(placeLabel);
        rootTable.row();
        Button startButton = new TextButton("Return to main menu", skin);
        rootTable.add(startButton);
        startButton.addListener(
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.setScreen(new MainMenuScreen(game));
                    }
                });

        return stage;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0.1f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(backgroundTex, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        //font.getData().setScale(2);
        //font.draw(spriteBatch, message, message.length() +viewport.getWorldHeight()/2, viewport.getWorldWidth()/2 + 20);
        //font.draw(spriteBatch, placeString(place), viewport.getWorldHeight()/2 , viewport.getWorldWidth()/2 -10);
        spriteBatch.end();

        stage.act(dt);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
