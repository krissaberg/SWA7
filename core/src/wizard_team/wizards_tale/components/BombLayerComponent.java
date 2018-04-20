package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by synnovehalle on 20/04/2018.
 */

public class BombLayerComponent implements Component {

    private final int spread;
    private final int depth;
    private final int damage;

    public BombLayerComponent(int spread, int depth, int damage) {
        this.spread = spread;
        this.depth = depth;
        this.damage = damage;
    }
}
