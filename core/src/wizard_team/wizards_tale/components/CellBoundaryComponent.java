package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

import java.io.Serializable;

public class CellBoundaryComponent implements Component, Serializable {
    public Rectangle boundRect;

    public CellBoundaryComponent(Rectangle rectangle) {
        boundRect = rectangle;
    }
}