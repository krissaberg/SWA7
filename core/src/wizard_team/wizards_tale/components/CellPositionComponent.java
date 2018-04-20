package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;

import java.io.Serializable;

/**
 * Created by synnovehalle on 14/04/2018.
 * Holds the integer cell the entity is currently in, should be used by bombs, explosions, player
 */



public class CellPositionComponent implements Component, Serializable {

    public int x;
    public int y;

    public CellPositionComponent(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
