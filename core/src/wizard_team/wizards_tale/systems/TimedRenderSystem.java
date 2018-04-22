package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import wizard_team.wizards_tale.components.CellPositionComponent;
import wizard_team.wizards_tale.components.DamagerComponent;

import wizard_team.wizards_tale.components.SpriteComponent;
import wizard_team.wizards_tale.components.TimedEffectComponent;
import wizard_team.wizards_tale.components.constants.Constants;

/**
 * Created by synnovehalle on 17/04/2018.
 */

//The bomb render reperesent the bomb system counts down the
public class TimedRenderSystem extends IteratingSystem {
    private ComponentMapper<CellPositionComponent> cellPosMapper =
            ComponentMapper.getFor(CellPositionComponent.class);


    private ComponentMapper<TimedEffectComponent> timeMapper =
            ComponentMapper.getFor(TimedEffectComponent.class);


    private ComponentMapper<SpriteComponent> spriteMapper =
            ComponentMapper.getFor(SpriteComponent.class);

    private SpriteBatch batch;

    public TimedRenderSystem(SpriteBatch batch) {
        super(Family.all(CellPositionComponent.class, TimedEffectComponent.class, SpriteComponent.class).get());
        this.batch = batch;
    }


    public void processEntity(Entity e, float dt) {

        CellPositionComponent cellPos = cellPosMapper.get(e);
        TimedEffectComponent timeEffect = timeMapper.get(e);

        SpriteComponent spriteComponent = spriteMapper.get(e);
        Sprite sprite = spriteComponent.sprite;

        if (timeEffect.time >= 0) {
            int pos_x = (int)(cellPos.x*Constants.CELL_WIDTH);
            int pos_y = (int)(cellPos.y*Constants.CELL_HEIGHT);
            sprite.setX(pos_x);
            sprite.setY(pos_y);
            batch.begin();
            batch.draw(sprite.getTexture(), pos_x, pos_y);
            batch.end();

            timeEffect.time = timeEffect.time - 1;

        } else {
            //Remove the rendering components
            e.remove(TimedEffectComponent.class);
            e.remove(SpriteComponent.class);
        }

    }
}