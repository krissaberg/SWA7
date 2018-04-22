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

public class ExplosionSystem extends IteratingSystem {
    private final Texture explosionTexture;
    private final Texture bombTexture;
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

    public ExplosionSystem(Texture bombTexture, Texture explosionTexture) {
        super(Family.all(DamagerComponent.class, TimedEffectComponent.class).get());
        this.bombTexture = bombTexture;
        this.explosionTexture = explosionTexture;
    }

    // Handles spreading of an explosion in a certain direction. I.e. left would be dx=-1, dy=0
    public void spread(int depth, int x, int y, int dx, int dy) {
        Family collideableFam = Family.all(CollideableComponent.class).exclude(BombLayerComponent.class).get();
        ImmutableArray<Entity> collideables = getEngine().getEntitiesFor(collideableFam);

        // Moving in a certain direction, we check if there is a collision
        for (int i = 1; i < depth; i++) {
            for (Entity collideable : collideables){
                CellPositionComponent collideablePos = cellPosMapper.get(collideable);

                int collideable_x = collideablePos.x;
                int collideable_y = collideablePos.y;
                int height = collideableMapper.get(collideable).height;

                // Check if player

                // If the collidable is taller, we should stop
                if (collideable_x == x + dx & collideable_y == y + dy) {
                    if (height > depth) {
                        break;
                    } else {
                        int new_depth = depth - height;
                        collideable.remove(CollideableComponent.class);
                        collideable.add(new CollideableComponent(0, Constants.CollideableType.NONE));
                        collideable.add(new SpreadableComponent(new_depth));
                        collideable.add(new DamagerComponent(Constants.DEFAULT_BOMB_DAMAGE));
                        collideable.add(new SpriteComponent(explosionTexture));
                        collideable.add(new TimedEffectComponent(Constants.DEFAULT_EXPLOSION_TIME, Constants.EffectTypes.VANISH));
                    }
                }
                // Check if player is hit

            }
        }
    }

    public void processEntity(Entity e, float dt) {

        CellPositionComponent cellPos = cellPosMapper.get(e);
        TimedEffectComponent timedEffect = effectMapper.get(e);
        DamagerComponent damager = damageMapper.get(e);
        SpreadableComponent spreadable = spreadMapper.get(e);

        int damage = damager.damage;
        int depth = spreadable.depth;
        int x = cellPos.x;
        int y = cellPos.y;

        // Type: Bomb
        if (timedEffect.effect == Constants.EffectTypes.SPREAD) {
            // Check if it is bomb detonation time
            if (timedEffect.time == 0) {
                spread(2, x, y,0,0); // In origin
                spread(depth, x, y,1,0); // Right
                spread(depth, x, y,-1,0); // Left
                spread(depth, x, y,0,1); // Up
                spread(depth, x, y,0,-1); // Down
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
                            if (destroyableComponent.hp == 10) {
                                destroyable.remove(SpriteComponent.class);
                                destroyable.remove(CollideableComponent.class);

                                destroyable.add(new DestroyableComponent(0));
                                destroyable.add(new CollideableComponent(0, Constants.CollideableType.NONE));
                            }
                            destroyableComponent.hp -= damage;
                            if (destroyableComponent.hp < 0) {
                                destroyable.remove(SpriteComponent.class);
                                destroyable.remove(CollideableComponent.class);
                                //Make a regular tile hp
                                destroyable.add(new DestroyableComponent(0));
                                destroyable.add(new CollideableComponent(0, Constants.CollideableType.NONE));
                                destroyable.add(new PowerupComponent(Constants.PowerupTypes.NONE, 0));
                                destroyable.add(new TimedEffectComponent(Constants.POWERUP_TIME, Constants.EffectTypes.VANISH));
                            }
                        }
                    }
                }
            }
        }

    }
}
