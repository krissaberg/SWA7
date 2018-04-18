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

    public BombSystem(Texture bombTexture) {
        super(Family.all(DamagerComponent.class, CellPositionComponent.class, TimedEffectComponent.class).get());
        this.bombTexture = bombTexture;
    }

    public void processEntity(Entity e, float dt) {
        DamagerComponent damager = damagerMapper.get(e);
        TimedEffectComponent timeEffect = timeEffectMapper.get(e);

        if (timeEffect.time==0) {
            //Bomb is the start of an explosion (this makes it taken in by Explosion system)
            e.add(new SpreadableComponent(damager.getDamage()));
            //For rendering
        } else {
            e.add(new SpriteComponent(this.bombTexture));
        }
    }


}
