package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;

import wizard_team.wizards_tale.components.CellPositionComponent;
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
    private ComponentMapper<DamagerComponent> damagerMapper =
            ComponentMapper.getFor(DamagerComponent.class);
    private ComponentMapper<TimedEffectComponent> timeEffectMapper =
            ComponentMapper.getFor(TimedEffectComponent.class);
    private ComponentMapper<SpreadableComponent> spreadMapper =
            ComponentMapper.getFor(SpreadableComponent.class);

    public BombSystem(Texture bombTexture) {
        super(Family.all(CellPositionComponent.class, SpreadableComponent.class, TimedEffectComponent.class).get());
        this.bombTexture = bombTexture;
    }

    public void processEntity(Entity e, float dt) {
        //DamagerComponent damager = damagerMapper.get(e);
        CellPositionComponent cellPos= cellPosMapper.get(e);
        SpreadableComponent spread = spreadMapper.get(e);
        TimedEffectComponent timeEffect = timeEffectMapper.get(e);

        int start_x = cellPos.x;
        int start_y = cellPos.y;
        int depth = spread.getdepth();

        if (timeEffect.effect == Constants.EffectTypes.SPREAD) {
            if (timeEffect.time == 0) {
                //Spread is of type SPREAD, move in + pattern
                for (int i = 1; i < depth; i++) {
                    // Add explosion entities, found by Spreadable property
                    Entity explosion_right = new Entity();
                    Entity explosion_left = new Entity();
                    Entity explosion_up = new Entity();
                    Entity explosion_down = new Entity();

                    //TODO: depth/movement check
                    explosion_right.add(new CellPositionComponent(start_x + i, start_y));
                    explosion_left.add(new CellPositionComponent(start_x - i, start_y));
                    explosion_up.add(new CellPositionComponent(start_x, start_y + i));
                    explosion_down.add(new CellPositionComponent(start_x, start_y - i));

                    //TODO: inherit from player
                    explosion_right.add(new DamagerComponent(Constants.DEFAULT_BOMB_DAMAGE));
                    explosion_left.add(new DamagerComponent(Constants.DEFAULT_BOMB_DAMAGE));
                    explosion_up.add(new DamagerComponent(Constants.DEFAULT_BOMB_DAMAGE));
                    explosion_down.add(new DamagerComponent(Constants.DEFAULT_BOMB_DAMAGE));

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

                }

            } else {
                // Render as bomb, time down handled by TimedRenderSystem
                e.add(new SpriteComponent(this.bombTexture));
            }

        }
    }

}
