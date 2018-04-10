package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.systems.IteratingSystem;
import wizard_team.wizards_tale.components.PositionComponent;
import wizard_team.wizards_tale.components.VelocityComponent;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.Entity;

public class VelocityMovementSystem extends IteratingSystem {
  private ComponentMapper<PositionComponent> posMapper =
      ComponentMapper.getFor(PositionComponent.class);
  private ComponentMapper<VelocityComponent> velMapper =
      ComponentMapper.getFor(VelocityComponent.class);

  public VelocityMovementSystem() {
    super(Family.all(PositionComponent.class, VelocityComponent.class).get());
  }

  public void processEntity(Entity e, float dt) {
    PositionComponent pos = posMapper.get(e);
    VelocityComponent vel = velMapper.get(e);

    pos.x += vel.v_x * dt;
    pos.y += vel.v_y * dt;
  }
}
