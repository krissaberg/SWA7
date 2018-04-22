package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;

import wizard_team.wizards_tale.components.constants.Constants;

/**
 * Created by synnovehalle on 22/04/2018.
 */

public class PowerupComponent implements Component {
    public Constants.PowerupTypes powerupType;
    public int amount;
    public PowerupComponent(Constants.PowerupTypes powerupType, int amount) {
        this.powerupType = powerupType;
        this.amount = amount;
    }
}
