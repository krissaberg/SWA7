package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by synnovehalle on 20/04/2018.
 */

public class DestroyableComponent implements Component {
    public int hp;
    public boolean isAlive;

    public DestroyableComponent(int hp, boolean isAlive) {
        this.hp = hp;
        this.isAlive = isAlive;
    }
}
