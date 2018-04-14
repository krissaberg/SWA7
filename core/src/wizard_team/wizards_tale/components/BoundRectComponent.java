package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

public class BoundRectComponent implements Component {
    public Rectangle boundRect;

    public BoundRectComponent(Rectangle rectangle) {
        boundRect = rectangle;
    }
}
