package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import wizard_team.wizards_tale.components.BombLayerComponent;
import wizard_team.wizards_tale.components.CellPositionComponent;
import wizard_team.wizards_tale.components.CollideableComponent;
import wizard_team.wizards_tale.components.DamagerComponent;
import wizard_team.wizards_tale.components.DestroyableComponent;
import wizard_team.wizards_tale.components.PowerupComponent;
import wizard_team.wizards_tale.components.SpreadableComponent;
import wizard_team.wizards_tale.components.SpriteComponent;
import wizard_team.wizards_tale.components.TimedEffectComponent;
import wizard_team.wizards_tale.components.constants.Constants;

import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public class ExplosionSystem extends IteratingSystem {
    private final Texture explosionTexture;
    private final Texture bombTexture;
    private Random ranGen;
    private ComponentMapper<CellPositionComponent> cellPosMapper =
            ComponentMapper.getFor(CellPositionComponent.class);

    private ComponentMapper<SpreadableComponent> spreadMapper =
            ComponentMapper.getFor(SpreadableComponent.class);

    private ComponentMapper<TimedEffectComponent> effectMapper =
            ComponentMapper.getFor(TimedEffectComponent.class);

    private ComponentMapper<DamagerComponent> damageMapper =
            ComponentMapper.getFor(DamagerComponent.class);

    private ComponentMapper<DestroyableComponent> destroyableMapper =
            ComponentMapper.getFor(DestroyableComponent.class);

    private ComponentMapper<CollideableComponent> collideableMapper =
            ComponentMapper.getFor(CollideableComponent.class);

    private ComponentMapper<BombLayerComponent> bombLayerMapper =
            ComponentMapper.getFor(BombLayerComponent.class);

    public ExplosionSystem(Texture bombTexture, Texture explosionTexture) {
        super(Family.all(DamagerComponent.class, TimedEffectComponent.class).get());
        this.bombTexture = bombTexture;
        this.explosionTexture = explosionTexture;
        this.ranGen = new Random();
    }

    public boolean canFlowOver(Entity collideable, int depth) {
        CellPositionComponent collideablePos = cellPosMapper.get(collideable);
        if (collideableMapper.get(collideable).height > depth) {
            return false;
        }
        return true;
    }

    public boolean isDestroyable(Entity collideable, int damage) {
        if (destroyableMapper.get(collideable).hp > damage) {
            return false;
        }
        return true;
    }

    public Entity getOncomingCollideable(int start_x, int start_y, int dx, int dy) {
        Family collideableFam = Family.all(CollideableComponent.class).exclude(BombLayerComponent.class).get();
        ImmutableArray<Entity> collideables = getEngine().getEntitiesFor(collideableFam);

        for (Entity collideable : collideables) {
            CellPositionComponent collideablePos = cellPosMapper.get(collideable);
            int collideable_x = collideablePos.x;
            int collideable_y = collideablePos.y;
            int height = collideableMapper.get(collideable).height;
            // If the collidable is taller, we should stop
            if (collideable_x == start_x + dx & collideable_y == start_y + dy) {
                return collideable;
            }
        }
        System.out.println("should never happen: didnt find collideable");
        return new Entity();

    }

    // Handles spreading of an explosion in a certain direction. I.e. left would be dx=-1, dy=0
    public void spread(int damage, int range, int depth, int start_x, int start_y, int dx, int dy) {

        for (int i = 0; i < range ; i ++) {
            int xMove = start_x + dx*i;
            int yMove = start_y + dy*i;
            if ((0 <= xMove & xMove < Constants.MAP_X) & (0<=yMove & yMove < Constants.MAP_Y)) {
                Entity collideable = getOncomingCollideable(start_x, start_y, dx*i, dy*i);
                if (canFlowOver(collideable, depth)) {
                    if (isDestroyable(collideable, damage)) {
                        range -= 1;
                        collideable.remove(CollideableComponent.class);
                        collideable.add(new CollideableComponent(0, Constants.CollideableType.NONE));
                        collideable.add(new SpreadableComponent(depth));
                        collideable.add(new DamagerComponent(Constants.DEFAULT_BOMB_DAMAGE));
                        collideable.add(new SpriteComponent(explosionTexture));
                        collideable.add(new TimedEffectComponent(Constants.DEFAULT_EXPLOSION_TIME, Constants.EffectTypes.VANISH));
                    }
                } else {
                    break; // can't flow over, stop
                }
            }
        }
    }

    public void processEntity(Entity e, float dt) {

        CellPositionComponent cellPos = cellPosMapper.get(e);
        TimedEffectComponent timedEffect = effectMapper.get(e);
        DamagerComponent damager = damageMapper.get(e);
        SpreadableComponent spreadable = spreadMapper.get(e);

        Family playerFamily = Family.all(BombLayerComponent.class).get();
        ImmutableArray<Entity> players = getEngine().getEntitiesFor(playerFamily);

        int damageBuff=1;
        int rangeBuff=1;

        for (Entity player : players) {
            BombLayerComponent p = bombLayerMapper.get(player);
            damageBuff = p.damageBuff;
            rangeBuff = p.rangeBuff;
        }

        int damage = damager.damage * damageBuff;
        int depth = spreadable.depth;
        int range = Constants.DEFAULT_BOMB_RANGE + rangeBuff;

        int x = cellPos.x;
        int y = cellPos.y;

        // Type: Bomb
        if (timedEffect.effect == Constants.EffectTypes.SPREAD) {
            // Check if it is bomb detonation time
            if (timedEffect.time == 0) {
                spread(damage, range, depth, x, y,1,0); // Right
                spread(damage, range, depth, x, y,-1,0); // Left
                spread(damage, range, depth, x, y,0,1); // Up
                spread(damage, range, depth, x, y,0,-1); // Down

            } else {
                // If not detonation, render as bomb, time down handled by TimedRenderSystem
                e.add(new SpriteComponent(this.bombTexture));
            }

            // Type: Explosion
        } else if (timedEffect.effect == Constants.EffectTypes.VANISH) {
            if (timedEffect.time == 0) {

                Family destroyableFamily = Family.all(DestroyableComponent.class).get();
                ImmutableArray<Entity> destroyables = getEngine().getEntitiesFor(destroyableFamily);

                //For each explosion-entity, iterate over destroyables and check if it's in the way
                for (Entity destroyable : destroyables) {
                    DestroyableComponent destroyableComponent = destroyableMapper.get(destroyable);

                    //hp = 0 means regular tile, skip all of them
                    if (destroyableComponent.hp == 0) {
                        continue;
                    }
                    //Otherwise there is something that can be destroyed
                    else {
                        CellPositionComponent destroyablePos = cellPosMapper.get(destroyable);
                        int destroyable_x = destroyablePos.x;
                        int destroyable_y = destroyablePos.y;

                        //Check if we have exploded over a destroyable
                        if (destroyable_x == cellPos.x & destroyable_y == cellPos.y) {
                            destroyableComponent.hp -= damage;
                            if (destroyableComponent.hp < 0) {
                                destroyable.remove(SpriteComponent.class);
                                destroyable.remove(CollideableComponent.class);
                                //Make a regular tile hp
                                destroyable.add(new DestroyableComponent(0));
                                destroyable.add(new CollideableComponent(0, Constants.CollideableType.NONE));
                                int l = Constants.PowerupTypes.values().length;
                                // randomly select a powerup type and effect
                                int r = ranGen.nextInt(l); //TODO: change
                                System.out.println("random int: " + r);
                                if (r < l) {
                                    Constants.PowerupTypes randPower = Constants.PowerupTypes.values()[r];
                                    destroyable.add(new PowerupComponent(randPower, 2));
                                    destroyable.add(new TimedEffectComponent(Constants.POWERUP_TIME, Constants.EffectTypes.VANISH));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
