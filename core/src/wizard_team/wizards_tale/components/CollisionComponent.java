package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

public class CollisionComponent implements Component {

    public CollidableType collidableType;

    public CollisionComponent() {
        this.collidableType = CollidableType.HARD;
    }

public CollisionComponent(CollidableType collidableType) {
        this.collidableType = collidableType;
    }
}
