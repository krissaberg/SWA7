package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;

import wizard_team.wizards_tale.components.constants.Constants;

/**
 * Created by synnovehalle on 20/04/2018.
 */

public class CollideableComponent implements Component {
    public int height;
    public Constants.CollideableType collidableType;

    public CollideableComponent(int height, Constants.CollideableType collidableType) {
        this.height = height;
        this.collidableType = collidableType;
    }
}
