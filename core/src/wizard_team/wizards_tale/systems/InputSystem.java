package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import wizard_team.wizards_tale.components.BombLayerComponent;
import wizard_team.wizards_tale.components.CellPositionComponent;
import wizard_team.wizards_tale.components.DamagerComponent;
import wizard_team.wizards_tale.components.PositionComponent;
import wizard_team.wizards_tale.components.ReceiveInputComponent;
import wizard_team.wizards_tale.components.SpreadableComponent;
import wizard_team.wizards_tale.components.TimedEffectComponent;
import wizard_team.wizards_tale.components.VelocityComponent;
import wizard_team.wizards_tale.components.constants.Constants;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class InputSystem extends IteratingSystem {
    private ComponentMapper<ReceiveInputComponent> inputMapper =
            ComponentMapper.getFor(ReceiveInputComponent.class);
    private ComponentMapper<PositionComponent> posMapper =
            ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> velMapper =
            ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<CellPositionComponent> cellPosMapper =
            ComponentMapper.getFor(CellPositionComponent.class);
    private ComponentMapper<BombLayerComponent> bombLayerMapper=
            ComponentMapper.getFor(BombLayerComponent.class);
    private ComponentMapper<DamagerComponent> damageMapper =
            ComponentMapper.getFor(DamagerComponent.class);

    private Touchpad touchpad;
    private boolean bombPlaced =false;

    public InputSystem(Touchpad touchpad, Button bombButton) {
        super(Family.all(ReceiveInputComponent.class, PositionComponent.class).get());
        this.touchpad = touchpad;
    }

    private static float maxVel = 50;

    public void processEntity(Entity e, float dt) {
        VelocityComponent vel = velMapper.get(e);
        CellPositionComponent cell = cellPosMapper.get(e);

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

        // TODO: make sure right player if shared system If bomb button has been pressed, place a bomb at current position
        if (bombPlaced) {
            Family playerFamily = Family.all(BombLayerComponent.class).get();
            ImmutableArray<Entity> players = getEngine().getEntitiesFor(playerFamily);
            Family cellFamily = Family.all(CellPositionComponent.class).exclude(BombLayerComponent.class).get();
            ImmutableArray<Entity> cells = getEngine().getEntitiesFor(cellFamily);
            for (Entity p : players) {
                BombLayerComponent bombLayer = bombLayerMapper.get(p);
                CellPositionComponent pPos = cellPosMapper.get(p);
                    for (Entity c : cells) {
                        CellPositionComponent cPos = cellPosMapper.get(c);
                        if (cPos.x == pPos.x & cPos.y == pPos.y) {
                            //Sets bombs cell to be within the current cell/tile of player
                            c.add(new SpreadableComponent(bombLayer.spread));
                            //inherit
                            int damage = bombLayer.damage;
                            c.add(new DamagerComponent(damage));
                            // Renders bomb
                            c.add(new TimedEffectComponent(Constants.DEFAULT_DETONATION_TIME, Constants.EffectTypes.SPREAD));
                            bombPlaced = false;
                            break;
                    }
                }

            }

        }

    }
    public void setBombButtonPressed() {
        bombPlaced = true;
    }
}