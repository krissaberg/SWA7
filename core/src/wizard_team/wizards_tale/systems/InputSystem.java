package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import wizard_team.wizards_tale.components.PositionComponent;
import wizard_team.wizards_tale.components.RandomMovementComponent;
import wizard_team.wizards_tale.components.ReceiveInputComponent;
import wizard_team.wizards_tale.components.VelocityComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class InputSystem extends IteratingSystem {
  private ComponentMapper<ReceiveInputComponent> inputMapper =
      ComponentMapper.getFor(ReceiveInputComponent.class);
  private ComponentMapper<PositionComponent> posMapper =
      ComponentMapper.getFor(PositionComponent.class);
  private Touchpad touchpad;

  public InputSystem(Touchpad touchpad) {
    super(Family.all(ReceiveInputComponent.class, PositionComponent.class).get());
    this.touchpad = touchpad;
  }

  public void processEntity(Entity e, float dt) {
    PositionComponent pos = posMapper.get(e);
    pos.x += touchpad.getKnobPercentX();
    pos.y += touchpad.getKnobPercentY();
  }
}
