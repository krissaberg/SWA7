package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.systems.IteratingSystem;

import wizard_team.wizards_tale.components.BoundRectComponent;
import wizard_team.wizards_tale.components.CollisionComponent;
import wizard_team.wizards_tale.components.PositionComponent;
import wizard_team.wizards_tale.components.VelocityComponent;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;

public class VelocityMovementSystem extends IteratingSystem {
  private ComponentMapper<PositionComponent> posMapper =
      ComponentMapper.getFor(PositionComponent.class);

  private ComponentMapper<VelocityComponent> velMapper =
      ComponentMapper.getFor(VelocityComponent.class);

  private ComponentMapper<BoundRectComponent> boundMapper =
      ComponentMapper.getFor(BoundRectComponent.class);

  public VelocityMovementSystem() {
    super(Family.all(PositionComponent.class, VelocityComponent.class).get());
  }

  public void processEntity(Entity e, float dt) {
    PositionComponent pos = posMapper.get(e);
    VelocityComponent vel = velMapper.get(e);

    ImmutableArray<Entity> collidables = getEngine().getEntitiesFor(
            Family.all(BoundRectComponent.class, PositionComponent.class).get()
    );

    pos.x += vel.v_x * dt;
    pos.y += vel.v_y * dt;

    if(boundMapper.has(e)) {
      BoundRectComponent bound = boundMapper.get(e);
      bound.boundRect.setPosition(pos.x, pos.y);
      for(Entity other : collidables) {
        if(e != other) {
            BoundRectComponent otherBound = boundMapper.get(other);
            if(bound.boundRect.overlaps(otherBound.boundRect)) {
              pos.x -= vel.v_x * dt;
              pos.y -= vel.v_y * dt;
              bound.boundRect.setPosition(pos.x, pos.y);
            }
        }
      }
    }

  }
}
