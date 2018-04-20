package wizard_team.wizards_tale.components;

import java.io.Serializable;

public enum CollidableType implements Serializable {
    HARD, // Nothing can pass through this
    SOFT  // Soft entities can pass through other soft entities
}
