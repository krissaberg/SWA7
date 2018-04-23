package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

import wizard_team.wizards_tale.components.BombLayerComponent;
import wizard_team.wizards_tale.components.CellPositionComponent;
import wizard_team.wizards_tale.components.PowerupComponent;
import wizard_team.wizards_tale.components.SpriteComponent;
import wizard_team.wizards_tale.components.TimedEffectComponent;
import wizard_team.wizards_tale.components.constants.Constants;


public class PowerupRenderSystem extends IteratingSystem {
    private final Texture speedTexture_1;
    private final Texture rangeTexture_1;

    private ComponentMapper<CellPositionComponent> cellPosMapper =
            ComponentMapper.getFor(CellPositionComponent.class);

    private ComponentMapper<TimedEffectComponent> timedMapper =
            ComponentMapper.getFor(TimedEffectComponent.class);

    private ComponentMapper<PowerupComponent> powerupMapper =
            ComponentMapper.getFor(PowerupComponent.class);

    public PowerupRenderSystem(Texture speedTexture, Texture rangeTexture) {
        super(Family.all(PowerupComponent.class, TimedEffectComponent.class).exclude(BombLayerComponent.class).get());

        this.speedTexture_1 = speedTexture;
        this.rangeTexture_1 = rangeTexture;

    }


    public void processEntity(Entity e, float dt) {
        TimedEffectComponent timedEffect = timedMapper.get(e);
        PowerupComponent curPowerup = powerupMapper.get(e);

        if (timedEffect.time <= 0) {
            e.remove(PowerupComponent.class);
        } else {

            switch (curPowerup.powerupType) {
                case SPEED:
                    e.add(new SpriteComponent(speedTexture_1));
//                    System.out.println("got speed doe"); //TODO:remove
                case RANGE:
                    e.add(new SpriteComponent(rangeTexture_1));
                    break;
            }
        }

        Family playerFamily = Family.all(BombLayerComponent.class).get();
        ImmutableArray<Entity> players = getEngine().getEntitiesFor(playerFamily);

        for (Entity player : players) {

            CellPositionComponent playerPos = cellPosMapper.get(player);
            int player_x = playerPos.x;
            int player_y = playerPos.y;
            CellPositionComponent cellPos = cellPosMapper.get(e);

            if (player_x == cellPos.x & player_y == cellPos.y) {

                player.add(new PowerupComponent(curPowerup.powerupType, curPowerup.amount));
                player.add(new TimedEffectComponent(Constants.POWERUP_KEEP_TIME, Constants.EffectTypes.VANISH));
                e.remove(PowerupComponent.class);
                //essentially removes it, got to be careful since timerender handles removal neatly
                timedEffect.time = 0;

            }
        }
    }
}
