package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import wizard_team.wizards_tale.components.CellPositionComponent;
import wizard_team.wizards_tale.components.PositionComponent;


import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class CellRenderSystem extends IteratingSystem {
    private ComponentMapper<CellPositionComponent> cellPosMapper =
            ComponentMapper.getFor(CellPositionComponent.class);

    private ComponentMapper<PositionComponent> PosMapper =
            ComponentMapper.getFor(PositionComponent.class);

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    public CellRenderSystem(SpriteBatch batch) {
        super(Family.all(CellPositionComponent.class, PositionComponent.class).get());
        this.batch = batch;

    }

    public void processEntity(Entity e, float dt) {
        CellPositionComponent cellPos = cellPosMapper.get(e);
        PositionComponent pos = PosMapper.get(e);

        BitmapFont font = new BitmapFont(); //or use alex answer to use custom font

        batch.begin();
//        font.draw(batch, "cell x: " + cellPos.x + " cell y: " + cellPos.y +
//                "\npos x: " + pos.x + "pos y:" + pos.y, 10, 50);
        batch.end();
    }
}
