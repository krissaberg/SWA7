package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

import wizard_team.wizards_tale.components.BombLayerComponent;
import wizard_team.wizards_tale.components.CellPositionComponent;
import wizard_team.wizards_tale.components.PowerupComponent;
import wizard_team.wizards_tale.components.SpriteComponent;
import wizard_team.wizards_tale.components.TimedEffectComponent;

import static wizard_team.wizards_tale.components.constants.Constants.PowerupTypes;


public class PowerupSystem extends IteratingSystem {
    private final Texture textures;
    private final Random ranGen;

    private ComponentMapper<CellPositionComponent> cellPosMapper =
            ComponentMapper.getFor(CellPositionComponent.class);

    private ComponentMapper<TimedEffectComponent> timedMapper =
            ComponentMapper.getFor(TimedEffectComponent.class);

    private ComponentMapper<PowerupComponent> powerupMapper =
            ComponentMapper.getFor(PowerupComponent.class);

    public PowerupSystem(Texture powerupTextures) {
        super(Family.all(PowerupComponent.class, TimedEffectComponent.class).get());
        //TODO: make textures damn
        this.textures = powerupTextures;
        this.ranGen = new Random();
    }


    public void processEntity(Entity e, float dt) {
        TimedEffectComponent timedEffect = timedMapper.get(e);
        PowerupComponent powerup = powerupMapper.get(e);

        if (timedEffect.time == 0) {
            System.out.println("powerup removed");
            e.remove(PowerupComponent.class);
        } else if (powerup.powerupType == PowerupTypes.NOT_ASSIGNED) {
                int l = PowerupTypes.values().length;
                // randomly select a powerup type and effect
                int r = ranGen.nextInt(l);
                PowerupTypes powerupType = PowerupTypes.values()[r];
                powerup.powerupType = powerupType;
        } else if (timedEffect.time > 0) {
            PowerupTypes powerupType;
            e.add(new SpriteComponent(textures));
            //TODO: switch texturerregion
            switch (powerup.powerupType) {
                case NONE:
                    break;
                case SPEED:
                    break;
                case BOMB_AMOUNT:
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

            //Check if we have exploded over a player
            if (player_x == cellPos.x & player_y == cellPos.y) {
                //Assign component to player-entity, remove from tile-entity

                player.add(powerupMapper.get(e));

                e.remove(PowerupComponent.class);
                //essentially removes it, got to be careful since timerender handles removal neatly
                timedEffect.time = 0;
                System.out.println("Got effect: " + powerup.powerupType);

            }
        }

    }
}
