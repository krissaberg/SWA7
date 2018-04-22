package wizard_team.wizards_tale.components.constants;

/**
 * Created by synnovehalle on 14/04/2018.
 */

public class Constants {
    public static final int DEFAULT_SPREADABLE_DEPTH = 3;
    public static final int PLAYER_START_X = 100;
    public static final int PLAYER_START_Y = 200;
    public static final float CELL_HEIGHT = 100f;
    public static final float CELL_WIDTH = 100f;



    public static final int MAP_X = 8;
    public static final int MAP_Y = 6;

    public static final String MAP_0 =
            "NNNNN\n" +
                    "NHNHN\n" +
                    "NNNNN\n" +
                    "NHNHN\n" +
                    "NNNNN";
    public static final int DEFAULT_DETONATION_TIME = 10;
    public static final int DEFAULT_EXPLOSION_TIME = 5;
    public static final int POWERUP_TIME = 50; //how long the powerup is in play, should be less than explosion time (hacky)

    public static final int DEFAULT_BOMB_RANGE = 3;
    public static final int DEFAULT_BOMB_DEPTH = 3; //Solid blocks have a height that is over depth
    public static final int DEFAULT_BOMB_DAMAGE = 2;

    public static final int HARD_BLOCK_HEIGHT = 10;
    public static final int SOFT_BLOCK_HEIGHT = 1;

    public static final int DEFAULT_BLOCK_HP = 1;
    public static final int DEFAULT_PLAYER_HP = 1;
    public static final int DEFAULT_MAX_BOMBS = 3;


    public enum EffectTypes {SPREAD, VANISH};
    public enum PowerupTypes {NOT_ASSIGNED, SPEED, BOMB_AMOUNT, NONE};


    public enum SpreadTypes {FLOW_AROUND_OBSTACLES, FLOW_CARTESIAN, FLOW_IGNORE_OBSTACLES};

    public enum CollideableType {
        HARD, // Nothing can pass through this
        SOFT,  // Soft entities can pass through other soft entities
        NONE
    }


    public enum TimeConstants {

        DEFAULT_BOMB_TIME(10);

        private int time;

        TimeConstants(int time) {
            this.time = time;
        }
    }


}