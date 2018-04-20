package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import wizard_team.wizards_tale.components.CellPositionComponent;
import wizard_team.wizards_tale.components.DamagerComponent;
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
        super(Family.all(DamagerComponent.class, CellPositionComponent.class, SpreadableComponent.class).get());
        this.explosionTexture = explosionTexture;
    }


    public void processEntity(Entity e, float dt) {
        CellPositionComponent cellPos = cellPosMapper.get(e);
        SpreadableComponent spreadable = spreadMapper.get(e);
        TimedEffectComponent timedEffect = effectMapper.get(e);

        Family family = Family.all().get();


        if (timedEffect.time != 0) {
            e.add(new SpriteComponent(explosionTexture));
        }

    }
}
