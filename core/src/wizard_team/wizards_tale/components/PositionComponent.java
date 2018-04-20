package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;

import java.io.Serializable;

public class PositionComponent implements Component, Serializable {
  public float x;
  public float y;

  public PositionComponent(float x, float y) {
    this.x = x;
    this.y = y;
  }
};
