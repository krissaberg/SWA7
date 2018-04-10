package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;

public class RandomMovementComponent implements Component {
  public float timeUntilNewVelocity;

  public RandomMovementComponent(float timeUntilNewVelocity) {
    this.timeUntilNewVelocity = timeUntilNewVelocity;
  }
};
