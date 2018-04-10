package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.systems.IteratingSystem;
import wizard_team.wizards_tale.components.PositionComponent;
import wizard_team.wizards_tale.components.VelocityComponent;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.Entity;
import wizard_team.wizards_tale.components.RandomMovementComponent;
import com.badlogic.gdx.math.MathUtils;

public class RandomWalkerSystem extends IteratingSystem {
  private ComponentMapper<RandomMovementComponent> randomMapper =
      ComponentMapper.getFor(RandomMovementComponent.class);
  private ComponentMapper<VelocityComponent> velMapper =
      ComponentMapper.getFor(VelocityComponent.class);

  public RandomWalkerSystem() {
    super(Family.all(RandomMovementComponent.class, VelocityComponent.class).get());
  }

  public void processEntity(Entity e, float dt) {
    VelocityComponent vel = velMapper.get(e);
    RandomMovementComponent rnd = randomMapper.get(e);

    rnd.timeUntilNewVelocity -= dt;
    if(rnd.timeUntilNewVelocity < 0) {
        vel.v_x = MathUtils.random(10) - 5;
        vel.v_y = MathUtils.random(10) - 5;
        rnd.timeUntilNewVelocity = MathUtils.random(3);
    }
  }
}
