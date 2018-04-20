package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import wizard_team.wizards_tale.components.CellPositionComponent;
import wizard_team.wizards_tale.components.CollideableComponent;
import wizard_team.wizards_tale.components.DamagerComponent;
import wizard_team.wizards_tale.components.DestroyableComponent;
import wizard_team.wizards_tale.components.PositionComponent;
import wizard_team.wizards_tale.components.SpreadableComponent;
import wizard_team.wizards_tale.components.SpriteComponent;
import wizard_team.wizards_tale.components.TimedEffectComponent;
import wizard_team.wizards_tale.components.constants.Constants;

import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Texture;

public class ExplosionSystem extends IteratingSystem {
    private final Texture explosionTexture;
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

    public ExplosionSystem(Texture explosionTexture) {
        super(Family.all(DamagerComponent.class).get());
        this.explosionTexture = explosionTexture;
    }


    public void processEntity(Entity e, float dt) {
        CellPositionComponent cellPos = cellPosMapper.get(e);
        SpreadableComponent spreadable = spreadMapper.get(e);
        TimedEffectComponent timedEffect = effectMapper.get(e);
        DamagerComponent damager = damageMapper.get(e);

        e.add(new SpriteComponent(explosionTexture));

        int damage = damager.damage;
        int explosion_x = cellPos.x;
        int explosion_y = cellPos.y;


        Family destroyableFamily = Family.all(DestroyableComponent.class).get();
        ImmutableArray<Entity> destroyables = getEngine().getEntitiesFor(destroyableFamily);

        //For each explosion-entity, iterate over destroyables and check if it's in the way
        for (Entity destroyable : destroyables) {
            DestroyableComponent destroyableComponent = destroyableMapper.get(destroyable);

            //hp = 0 means regular tile, skip all of them
            if (destroyableComponent.hp == 0) { continue;

            //Otherwise there is something that can be destroyed
            } else {
                CellPositionComponent destroyablePos = cellPosMapper.get(destroyable);
                int destroyable_x = destroyablePos.x;
                int destroyable_y = destroyablePos.y;

                //Check if we have exploded over a destroyable
                if (destroyable_x == explosion_x & destroyable_y == explosion_y) {
                    destroyableComponent.hp -= damage;
                    if (destroyableComponent.hp<0) {
                        destroyable.remove(SpriteComponent.class);
                        destroyable.remove(CollideableComponent.class);
                        //Make a regular tile hp
                        destroyable.add(new DestroyableComponent(0));
                        destroyable.add(new CollideableComponent(0));

                    }
                }
            }
        }
    }
}
