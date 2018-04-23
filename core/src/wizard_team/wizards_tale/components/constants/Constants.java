package wizard_team.wizards_tale.components.constants;

/**
 * Created by synnovehalle on 14/04/2018.
 */

public class Constants {
    // Map constants
    public static final float CELL_HEIGHT = 100f;
    public static final float CELL_WIDTH = 100f;
    public static final int MAP_X = 8;
    public static final int MAP_Y = 6;

    // Bomb constants
    public static final int DEFAULT_BOMB_RANGE = 2;
    public static final int DEFAULT_BOMB_DEPTH = 2; //Solid blocks have a height that is over depth
    public static final int DEFAULT_BOMB_DAMAGE = 2;

    // Block constants
    public static final int HARD_BLOCK_HEIGHT = 10;
    public static final int SOFT_BLOCK_HEIGHT = 1;
    public static final int DEFAULT_BLOCK_HP = 1;
    public static final int DEFAULT_PLAYER_HP = 10;

    public static int DEFAULT_SPEED = 1;

    // Time constants
    public static int POWERUP_KEEP_TIME = 25;
    public static final int POWERUP_TIME = 50; //how long the powerup is in play, should be less than explosion time (hacky)
    public static final int DEFAULT_DETONATION_TIME = 10;
    public static final int DEFAULT_EXPLOSION_TIME = 5;

    // Enums
    public enum EffectTypes {SPREAD, VANISH};
    public enum PowerupTypes {
        SPEED,
        RANGE,}

    public enum CollideableType {
        HARD, // Nothing can pass through this
        SOFT,  // Soft entities can pass through other soft entities
        NONE
    }
}