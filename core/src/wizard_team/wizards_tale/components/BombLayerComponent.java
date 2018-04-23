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
    public boolean laidBomb;
    public int rangeBuff;
    public int damageBuff;

    public BombLayerComponent(int spread, int depth, int damage) {
        this.spread = spread;
        this.depth = depth;
        this.damage = damage;
        this.maxBombs = 3;
        this.laidBomb = false;
        this.rangeBuff = 1;
        this.damageBuff = 1;
    }
}
