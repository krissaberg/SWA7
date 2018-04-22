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

import static wizard_team.wizards_tale.components.constants.Constants.POWERUP_TIME;
import static wizard_team.wizards_tale.components.constants.Constants.PowerupTypes;


public class PowerupRenderSystem extends IteratingSystem {
    private final Texture speedTexture_1;
    private final Texture amountTexture_1;
    private final Texture powerTexture_1;
    private final Texture rangeTexture_1;

    private ComponentMapper<CellPositionComponent> cellPosMapper =
            ComponentMapper.getFor(CellPositionComponent.class);

    private ComponentMapper<TimedEffectComponent> timedMapper =
            ComponentMapper.getFor(TimedEffectComponent.class);

    private ComponentMapper<PowerupComponent> powerupMapper =
            ComponentMapper.getFor(PowerupComponent.class);

    public PowerupRenderSystem(Texture speedTexture, Texture amountTexture, Texture rangeTexture, Texture powerTexture) {
        super(Family.all(PowerupComponent.class, TimedEffectComponent.class).get());

        this.speedTexture_1 = speedTexture;
        this.amountTexture_1 = amountTexture;
        this.rangeTexture_1 = rangeTexture;
        this.powerTexture_1 = powerTexture;

    }


    public void processEntity(Entity e, float dt) {
        TimedEffectComponent timedEffect = timedMapper.get(e);
        PowerupComponent powerup = powerupMapper.get(e);

        PowerupComponent newPower = new PowerupComponent(PowerupTypes.NONE, 0);

        if (timedEffect.time == 0) {
            e.remove(PowerupComponent.class);
        } else if (powerup.powerupType == PowerupTypes.NOT_ASSIGNED) {

            int l = PowerupTypes.values().length;
            // randomly select a powerup type and effect
            Random ranGen = new Random();
            int r = ranGen.nextInt(l);

            PowerupTypes powerupType = PowerupTypes.values()[r];
            powerup.powerupType = powerupType;

            switch (powerup.powerupType) {
                case NONE:
                    break;
                case SPEED:
                    //TODO: make constant values
                    e.add(new SpriteComponent(speedTexture_1));
                    newPower = new PowerupComponent(powerupType, 10);
                    break;
                case BOMB_AMOUNT:
                    e.add(new SpriteComponent(amountTexture_1));
                    newPower = new PowerupComponent(powerupType, 10);
                    break;
                case RANGE:
                    e.add(new SpriteComponent(rangeTexture_1));
                    newPower = new PowerupComponent(powerupType, 10);
                    break;
                case POWER:
                    e.add(new SpriteComponent(powerTexture_1));
                    newPower = new PowerupComponent(powerupType, 10);
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
                //Assign component to player-entity, remove from tile-entity
                player.add(newPower);
                player.add(new TimedEffectComponent(Constants.POWERUP_KEEP_TIME, Constants.EffectTypes.VANISH));
                e.remove(PowerupComponent.class);
                //essentially removes it, got to be careful since timerender handles removal neatly
                timedEffect.time = 0;
            }
        }

    }
}