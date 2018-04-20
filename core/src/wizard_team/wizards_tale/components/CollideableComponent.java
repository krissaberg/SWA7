package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by synnovehalle on 20/04/2018.
 */

public class CollideableComponent implements Component {
    public int height;
    public CollideableComponent(int height) {
        this.height = height;
    }
}
