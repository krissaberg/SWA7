package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Texture;

import wizard_team.wizards_tale.components.CellPositionComponent;
import wizard_team.wizards_tale.components.CollideableComponent;
import wizard_team.wizards_tale.components.DamagerComponent;
import wizard_team.wizards_tale.components.SpreadableComponent;
import wizard_team.wizards_tale.components.SpriteComponent;
import wizard_team.wizards_tale.components.TimedEffectComponent;
import wizard_team.wizards_tale.components.constants.Constants;

/**
 * Created by synnovehalle on 18/04/2018.
 */

public class BombSystem extends IteratingSystem {
    private Texture bombTexture;

    private ComponentMapper<CellPositionComponent> cellPosMapper =
            ComponentMapper.getFor(CellPositionComponent.class);
    private ComponentMapper<TimedEffectComponent> timeEffectMapper =
            ComponentMapper.getFor(TimedEffectComponent.class);
    private ComponentMapper<SpreadableComponent> spreadMapper =
            ComponentMapper.getFor(SpreadableComponent.class);
    private ComponentMapper<CollideableComponent> collideableMapper =
            ComponentMapper.getFor(CollideableComponent.class);

    private int start_x;
    private int start_y;
    private int depth;

    public BombSystem(Texture bombTexture) {
        super(Family.all(CellPositionComponent.class, SpreadableComponent.class, TimedEffectComponent.class).get());
        this.bombTexture = bombTexture;
    }


    public void spread(int start_depth, int dx, int dy) {
        for (int i = 1; i < start_depth; i++) {
            //still flow
            int new_depth = modifiedDepth(dx, dy);
            if (new_depth== 0) {
                return;
            } else {
                Entity explosion = new Entity();
                explosion.add(new CellPositionComponent(start_x + i*dx, start_y + i*dy));
                explosion.add(new SpreadableComponent(new_depth));
                explosion.add(new DamagerComponent(Constants.DEFAULT_BOMB_DAMAGE));
                explosion.add(new TimedEffectComponent(Constants.DEFAULT_EXPLOSION_TIME, Constants.EffectTypes.VANISH));
                getEngine().addEntity(explosion);
            }

        }
    }

    public int modifiedDepth(int dx, int dy) {
        Family collideableFam = Family.all(CollideableComponent.class).get();

        ImmutableArray<Entity> collideables = getEngine().getEntitiesFor(collideableFam);
        for (Entity collideable : collideables){
            CellPositionComponent collideablePos = cellPosMapper.get(collideable);

            int collideable_x = collideablePos.x;
            int collideable_y = collideablePos.y;
            int height = collideableMapper.get(collideable).height;

            if (collideable_x == start_x + dx & collideable_y == start_y+dy){
                if (height < depth) {
                    return depth - height;
                }
            }

        }
        return 0;
    }

    public void processEntity(Entity e, float dt) {
        CellPositionComponent cellPos= cellPosMapper.get(e);
        SpreadableComponent spread = spreadMapper.get(e);
        TimedEffectComponent timeEffect = timeEffectMapper.get(e);


        this.start_x = cellPos.x;
        this.start_y = cellPos.y;
        this.depth = spread.getdepth();

        if (timeEffect.effect == Constants.EffectTypes.SPREAD) {
            if (timeEffect.time == 0) {
                spread(depth,1,0);
                spread(depth,-1,0);
                spread(depth, 0,1);
                spread(depth, 0,-1);
            } else {
                // Render as bomb, time down handled by TimedRenderSystem
                e.add(new SpriteComponent(this.bombTexture));
            }

        }
    }

}
