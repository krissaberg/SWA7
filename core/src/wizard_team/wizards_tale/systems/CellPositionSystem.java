package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;

import wizard_team.wizards_tale.components.CellPositionComponent;
import wizard_team.wizards_tale.components.PositionComponent;
import wizard_team.wizards_tale.components.SpriteComponent;
import wizard_team.wizards_tale.components.VelocityComponent;

/**
 * Created by synnovehalle on 14/04/2018.
 */

public class CellPositionSystem extends IteratingSystem {

    private static final float CELL_HEIGHT = 100f;
    private static final float CELL_WIDTH = 100f;

    private ComponentMapper<PositionComponent> posMapper =
            ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<CellPositionComponent> cellPosMapper =
            ComponentMapper.getFor(CellPositionComponent.class);
    private ComponentMapper<SpriteComponent> spriteMapper =
            ComponentMapper.getFor(SpriteComponent.class);


    public CellPositionSystem() {
        super(Family.all(PositionComponent.class, CellPositionComponent.class, SpriteComponent.class).get());
    }

    public void processEntity(Entity e, float dt) {
        PositionComponent pos = posMapper.get(e);
        CellPositionComponent cell = cellPosMapper.get(e);
        SpriteComponent sprite = spriteMapper.get(e);

        float spriteWidth = sprite.width;
        float spriteHeight = sprite.height;

        // Calculates the cell values
        cell.x = (int)((pos.x+spriteWidth/2)/CELL_WIDTH);
        cell.y = (int)((pos.y+spriteHeight/2)/CELL_HEIGHT);
    }
}
