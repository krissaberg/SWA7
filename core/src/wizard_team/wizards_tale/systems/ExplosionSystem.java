package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import wizard_team.wizards_tale.components.CellPositionComponent;
import wizard_team.wizards_tale.components.PositionComponent;
import wizard_team.wizards_tale.components.SpreadableComponent;
import wizard_team.wizards_tale.components.SpriteComponent;
import wizard_team.wizards_tale.components.TimedEffectComponent;
import wizard_team.wizards_tale.components.constants.Constants;

import com.badlogic.gdx.graphics.Texture;

public class ExplosionSystem extends IteratingSystem {
    private final Texture explosionTexture;
    private ComponentMapper<CellPositionComponent> cellPosMapper =
            ComponentMapper.getFor(CellPositionComponent.class);

    private ComponentMapper<SpreadableComponent> spreadMapper =
            ComponentMapper.getFor(SpreadableComponent.class);

    private ComponentMapper<TimedEffectComponent> effectMapper =
            ComponentMapper.getFor(TimedEffectComponent.class);

    public ExplosionSystem(Texture explosionTexture) {
        super(Family.all(CellPositionComponent.class, SpreadableComponent.class).get());
        this.explosionTexture = explosionTexture;
    }


    public void processEntity(Entity e, float dt) {
        CellPositionComponent cellPos = cellPosMapper.get(e);
        SpreadableComponent spreadable = spreadMapper.get(e);
        TimedEffectComponent timedEffect = effectMapper.get(e);

        int start_x = cellPos.x;
        int start_y = cellPos.y;
        int depth = spreadable.depth;

        if (depth == 0) {
            getEngine().removeEntity(e);
        }

        //Check the spread type
        else if (timedEffect.effect == Constants.EffectTypes.SPREAD) {
            e.add(new TimedEffectComponent(Constants.DEFAULT_EXPLOSION_TIME, Constants.EffectTypes.VANISH));

            //Spread is of type SPREAD, move in + pattern
            //Add new explosion entities
            for (int i = 1; i < depth; i++) {
                Entity explosion_right = new Entity();
                Entity explosion_left = new Entity();
                Entity explosion_up = new Entity();
                Entity explosion_down = new Entity();


                explosion_right.add(new CellPositionComponent(start_x + i, start_y));
                explosion_left.add(new CellPositionComponent(start_x - i, start_y));
                explosion_up.add(new CellPositionComponent(start_x, start_y + i));
                explosion_down.add(new CellPositionComponent(start_x, start_y - i));

                explosion_right.add(new SpreadableComponent(depth - 1));
                explosion_left.add(new SpreadableComponent(depth - 1));
                explosion_up.add(new SpreadableComponent(depth - 1));
                explosion_down.add(new SpreadableComponent(depth - 1));

                explosion_right.add(new TimedEffectComponent(Constants.DEFAULT_EXPLOSION_TIME, Constants.EffectTypes.VANISH));
                explosion_left.add(new TimedEffectComponent(Constants.DEFAULT_EXPLOSION_TIME, Constants.EffectTypes.VANISH));
                explosion_up.add(new TimedEffectComponent(Constants.DEFAULT_EXPLOSION_TIME, Constants.EffectTypes.VANISH));
                explosion_down.add(new TimedEffectComponent(Constants.DEFAULT_EXPLOSION_TIME, Constants.EffectTypes.VANISH));

                getEngine().addEntity(explosion_right);
                getEngine().addEntity(explosion_left);
                getEngine().addEntity(explosion_up);
                getEngine().addEntity(explosion_down);
                getEngine().removeEntity(e);
            }
        }
        else {
            //Render it with a new texture
            e.add(new SpriteComponent(explosionTexture));
        }

    }
}
