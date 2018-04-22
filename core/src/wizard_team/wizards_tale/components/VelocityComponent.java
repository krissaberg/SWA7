package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;

public class VelocityComponent implements Component {
  public float v_x;
  public float v_y;

  public VelocityComponent() {
    this.v_x = 0;
    this.v_y = 0;
  }

};
