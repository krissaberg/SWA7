package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;

import java.io.Serializable;

class GameState implements Serializable {
    ImmutableArray<Entity> entities;

    public GameState(ImmutableArray<Entity> entities) {
        this.entities = entities;
    }
}
