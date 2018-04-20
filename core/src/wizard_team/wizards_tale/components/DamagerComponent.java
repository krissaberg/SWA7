package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;

import java.io.Serializable;

/**
 * Created by synnovehalle on 17/04/2018.
 */

public class DamagerComponent implements Component, Serializable {
    private final int damage;

    public DamagerComponent(int damage) {
        this.damage = damage;
    }
}
