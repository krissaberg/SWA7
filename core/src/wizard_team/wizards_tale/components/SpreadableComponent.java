package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;

import wizard_team.wizards_tale.components.constants.Constants;

/**
 * Created by synnovehalle on 18/04/2018.
 *
 * "Depth" denotes the spread, like a flow in water (minecraft), we test it against a collidable with a height
 * This determines if it spreads around
 */

public class SpreadableComponent implements Component {
    public int depth;
    public Constants.SpreadTypes spreadType;

    public SpreadableComponent(int depth) {
        this.depth = depth;
    }

    public int getdepth() {
        return depth;
    }

    public void setdepth(int depth) {
        this.depth = depth;
    }

}
