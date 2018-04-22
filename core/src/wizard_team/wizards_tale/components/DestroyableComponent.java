package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by synnovehalle on 20/04/2018.
 */

public class DestroyableComponent implements Component {
    public int hp;

    public DestroyableComponent(int hp) {
        this.hp = hp;
    }
}
