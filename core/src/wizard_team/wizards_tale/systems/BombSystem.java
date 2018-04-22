package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Texture;

import wizard_team.wizards_tale.components.CellPositionComponent;
import wizard_team.wizards_tale.components.CollideableComponent;
import wizard_team.wizards_tale.components.DamagerComponent;
import wizard_team.wizards_tale.components.SpreadableComponent;
import wizard_team.wizards_tale.components.SpriteComponent;
import wizard_team.wizards_tale.components.TimedEffectComponent;
import wizard_team.wizards_tale.components.constants.Constants;

/**
 * Created by synnovehalle on 18/04/2018.
 *
 * The bomb system is in charge of rendering a bomb and starting the explosion effect by adding
 */

public class BombSystem extends IteratingSystem {
    private Texture bombTexture;

    private ComponentMapper<CellPositionComponent> cellPosMapper =
            ComponentMapper.getFor(CellPositionComponent.class);
    private ComponentMapper<TimedEffectComponent> timeEffectMapper =
            ComponentMapper.getFor(TimedEffectComponent.class);
    private ComponentMapper<SpreadableComponent> spreadMapper =
            ComponentMapper.getFor(SpreadableComponent.class);


    public BombSystem(Texture bombTexture) {
        super(Family.all(CellPositionComponent.class, SpreadableComponent.class, TimedEffectComponent.class).get());
        this.bombTexture = bombTexture;
    }

    public void processEntity(Entity e, float dt) {
        TimedEffectComponent timeEffect = timeEffectMapper.get(e);
        // Only bomb-explosions (the first) is a spreader type the rest are vanish types

    }
}
