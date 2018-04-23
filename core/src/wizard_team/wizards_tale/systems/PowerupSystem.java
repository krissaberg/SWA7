package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import wizard_team.wizards_tale.components.BombLayerComponent;
import wizard_team.wizards_tale.components.CellPositionComponent;
import wizard_team.wizards_tale.components.DamagerComponent;

import wizard_team.wizards_tale.components.PowerupComponent;
import wizard_team.wizards_tale.components.SpriteComponent;
import wizard_team.wizards_tale.components.TimedEffectComponent;
import wizard_team.wizards_tale.components.VelocityComponent;
import wizard_team.wizards_tale.components.constants.Constants;

/**
 * Created by synnovehalle on 17/04/2018.
 */

//The bomb render reperesent the bomb system counts down the
public class PowerupSystem extends IteratingSystem {
    private ComponentMapper<PowerupComponent> powerupMapper =
            ComponentMapper.getFor(PowerupComponent.class);

    private ComponentMapper<TimedEffectComponent> timedMapper =
            ComponentMapper.getFor(TimedEffectComponent.class);

    private ComponentMapper<VelocityComponent> velMapper =
            ComponentMapper.getFor(VelocityComponent.class);

    private ComponentMapper<BombLayerComponent> bombLayerMapper =
            ComponentMapper.getFor(BombLayerComponent.class);


    public PowerupSystem() {
        super(Family.all(BombLayerComponent.class, TimedEffectComponent.class, PowerupComponent.class).get());
    }


    public void processEntity(Entity e, float dt) {
        Entity player = e;
            PowerupComponent power = powerupMapper.get(player);
            TimedEffectComponent timed = timedMapper.get(player);
        if (timed.time == 0) {
            player.remove(TimedEffectComponent.class);
            player.remove(PowerupComponent.class);
        }

        switch (power.powerupType) {
            case SPEED:
                if (timed.time > 0) {
                    VelocityComponent vel = velMapper.get(e);
                    if (vel.buff == Constants.DEFAULT_SPEED) {
                        vel.buff = power.amount;
                    } else {
                        vel.buff = Constants.DEFAULT_SPEED;
                    }
                }
                break;
            case RANGE:
                if (timed.time > 0) {
                    BombLayerComponent lay = bombLayerMapper.get(e);
                    if (lay.rangeBuff == Constants.DEFAULT_BOMB_RANGE) {
                        lay.rangeBuff = power.amount;
                    } else {
                        lay.rangeBuff = Constants.DEFAULT_BOMB_RANGE;
                    }
                }
                break;
        }





    }
}