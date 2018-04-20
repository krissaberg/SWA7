package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;

import java.io.Serializable;

public class RandomMovementComponent implements Component, Serializable {
  public float timeUntilNewVelocity;

  public RandomMovementComponent(float timeUntilNewVelocity) {
    this.timeUntilNewVelocity = timeUntilNewVelocity;
  }
};
