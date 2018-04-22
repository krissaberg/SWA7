package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import wizard_team.wizards_tale.components.CounterComponent;


public class CountDownSystem extends IteratingSystem{

    public CountDownSystem(){
        super(Family.all(CounterComponent.class).get());

    }

    public void processEntity(Entity e, float dt){
        e.getComponent(CounterComponent.class).playTime -= dt;
    }
}
