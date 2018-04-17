package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

import wizard_team.wizards_tale.components.constants.Constants;

public class CollisionComponent implements Component {

    public Constants.CollidableType collidableType;

    public CollisionComponent() {
        this.collidableType = Constants.CollidableType.HARD;
    }

public CollisionComponent(Constants.CollidableType collidableType) {
        this.collidableType = collidableType;
    }
}
