package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by synnovehalle on 17/04/2018.
 */

public class DamagerComponent implements Component {
    private final int damage;

    public DamagerComponent(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
