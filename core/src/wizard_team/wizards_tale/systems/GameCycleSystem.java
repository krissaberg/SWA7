package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Screen;

import wizard_team.wizards_tale.WizardsTaleGame;
import wizard_team.wizards_tale.components.CounterComponent;
import wizard_team.wizards_tale.components.GameTimeComponent;
import wizard_team.wizards_tale.screens.FinishedScreen;
import wizard_team.wizards_tale.screens.MainMenuScreen;
import wizard_team.wizards_tale.screens.SinglePlayerScreen;

/**
 * Created by krist on 12/04/2018.
 */

public class GameCycleSystem extends IteratingSystem {
    private WizardsTaleGame game;
    private SinglePlayerScreen singlePlayerScreen;

    public GameCycleSystem(WizardsTaleGame game, SinglePlayerScreen singlePlayerScreen){
        super(Family.all(CounterComponent.class, GameTimeComponent.class).get());
        this.game = game;
        this.singlePlayerScreen = singlePlayerScreen;
    }

    public void processEntity(Entity e, float dt){
        if(e.getComponent(CounterComponent.class).playTime <= 0){
            game.setScreen(new FinishedScreen(game, "TIME IS UP!", 0));
        }
        singlePlayerScreen.setGameTimeLeft(e.getComponent(CounterComponent.class).playTime);

    }
}
