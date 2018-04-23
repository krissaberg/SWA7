package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.systems.IteratingSystem;

import wizard_team.wizards_tale.components.BombLayerComponent;
import wizard_team.wizards_tale.components.CollideableComponent;
import wizard_team.wizards_tale.components.PowerupComponent;
import wizard_team.wizards_tale.components.ReceiveInputComponent;
import com.badlogic.ashley.core.Component;

import wizard_team.wizards_tale.components.BoundRectComponent;
import wizard_team.wizards_tale.components.CollideableComponent;
import wizard_team.wizards_tale.components.PositionComponent;
import wizard_team.wizards_tale.components.TimedEffectComponent;
import wizard_team.wizards_tale.components.VelocityComponent;
import wizard_team.wizards_tale.components.constants.Constants;

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

  private ComponentMapper<CollideableComponent> collisionMapper =
          ComponentMapper.getFor(CollideableComponent.class);


  public VelocityMovementSystem() {
    super(Family.all(PositionComponent.class, VelocityComponent.class).get());
  }

  private boolean cantPassThrough(Entity e1, Entity e2) {
    // Soft entities can pass through other soft entities
    if(collisionMapper.has(e1) && collisionMapper.has(e2)) {
      CollideableComponent coll1 = collisionMapper.get(e1);
      CollideableComponent coll2 = collisionMapper.get(e2);
      return coll1.collidableType == Constants.CollideableType.HARD ||
              coll2.collidableType == Constants.CollideableType.HARD;
    }
    return false;
  }

  public void processEntity(Entity e, float dt) {

    PositionComponent pos = posMapper.get(e);
    VelocityComponent vel = velMapper.get(e);

    ImmutableArray<Entity> collidables =
            getEngine()
                    .getEntitiesFor(Family.all(BoundRectComponent.class, PositionComponent.class).get());

    // Do the move.
    pos.x += vel.v_x * dt * vel.buff;
    pos.y += vel.v_y * dt * vel.buff;

    // Check if there's a collision. If there is, undo the move.
    if (boundMapper.has(e)) {
      BoundRectComponent bound = boundMapper.get(e);
      bound.boundRect.setPosition(pos.x, pos.y);
      for (Entity other : collidables) {
        if (e != other && cantPassThrough(e, other)) {
          BoundRectComponent otherBound = boundMapper.get(other);
          if (bound.boundRect.overlaps(otherBound.boundRect)) {
            pos.x -= vel.v_x * dt* vel.buff;
            pos.y -= vel.v_y * dt* vel.buff;
            bound.boundRect.setPosition(pos.x, pos.y);
          }
        }
      }
    }
  }
}