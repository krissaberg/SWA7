package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import wizard_team.wizards_tale.components.CellPositionComponent;
import wizard_team.wizards_tale.components.DamagerComponent;
import wizard_team.wizards_tale.components.PositionComponent;
import wizard_team.wizards_tale.components.SpriteComponent;
import wizard_team.wizards_tale.components.TimedEffectComponent;

/**
 * Created by synnovehalle on 17/04/2018.
 */

//The bomb render reperesent the bomb system counts down the
public class BombRenderSystem extends IteratingSystem {
    private final Texture bombTexture;
    private ComponentMapper<CellPositionComponent> cellPosMapper =
            ComponentMapper.getFor(CellPositionComponent.class);


    private ComponentMapper<TimedEffectComponent> timeMapper =
            ComponentMapper.getFor(TimedEffectComponent.class);


    private ComponentMapper<DamagerComponent> damageMapper =
            ComponentMapper.getFor(DamagerComponent.class);


    public BombRenderSystem(SpriteBatch batch, Texture bombTexture) {
        super(Family.all(CellPositionComponent.class, TimedEffectComponent.class, DamagerComponent.class).get());
        this.bombTexture = bombTexture;
    }


    public void processEntity(Entity e, float dt) {
        CellPositionComponent cellPos = cellPosMapper.get(e);
        TimedEffectComponent timeEffect = timeMapper.get(e);
        DamagerComponent damage = damageMapper.get(e);


        if (timeEffect.time != 0) {
            e.add(new SpriteComponent(bombTexture));
            e.add(new PositionComponent(cellPos.x*100, cellPos.y*100));
            timeEffect.time = timeEffect.time - 1;

        } else {
            //TODO: trigger explosion
            getEngine().removeEntity(e);
        }

    }
}