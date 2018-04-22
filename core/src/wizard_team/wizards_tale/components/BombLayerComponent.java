package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by synnovehalle on 20/04/2018.
 */

public class BombLayerComponent implements Component {

    public int spread;
    public int depth;
    public int damage;
    public int maxBombs;
    public int bombsLaid;
    public boolean laidBomb;

    public BombLayerComponent(int spread, int depth, int damage, int maxBombs) {
        this.spread = spread;
        this.depth = depth;
        this.damage = damage;
        this.maxBombs = maxBombs;
        this.laidBomb = false;
    }
}
