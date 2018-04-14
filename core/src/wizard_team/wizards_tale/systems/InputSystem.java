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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class InputSystem extends IteratingSystem {
    private ComponentMapper<ReceiveInputComponent> inputMapper =
            ComponentMapper.getFor(ReceiveInputComponent.class);
    private ComponentMapper<PositionComponent> posMapper =
            ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> velMapper =
            ComponentMapper.getFor(VelocityComponent.class);
    private Touchpad touchpad;

    public InputSystem(Touchpad touchpad) {
        super(Family.all(ReceiveInputComponent.class, PositionComponent.class).get());
        this.touchpad = touchpad;
    }

    private static float maxVel = 50;

    public void processEntity(Entity e, float dt) {
        VelocityComponent vel = velMapper.get(e);

        // Get the touchpad vector
        float x = touchpad.getKnobPercentX();
        float y = touchpad.getKnobPercentY();

        Vector2 touchpadDir = new Vector2(x, y);
        float angle = touchpadDir.angle();
        float len = touchpadDir.len();

        // Update the player's velocity accordingly
        if (len > 0) {
            // Right
            if (angle > 315 || angle <= 45) {
                vel.v_x = maxVel;
                vel.v_y = 0;
            }
            // Up
            if (angle > 45 && angle <= 135) {
                vel.v_y = maxVel;
                vel.v_x = 0;
            }
            // Left
            if (angle > 135 && angle <= 225) {
                vel.v_x = -maxVel;
                vel.v_y = 0;
            }
            // Down
            if (angle > 225 && angle < 315) {
                vel.v_y = -maxVel;
                vel.v_x = 0;
            }
        } else {
            vel.v_y = 0;
            vel.v_x = 0;
        }
    }
}
