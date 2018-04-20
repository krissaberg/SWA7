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

    public static final int DEFAULT_DETONATION_TIME = 10;

    //This is a testing dimension, but allows for a 4 hard blocks
    public static final int MAP_X = 5;
    public static final int MAP_Y = 5;

    public static final String MAP_0 =
            "NNNNN\n" +
                    "NHNHN\n" +
                    "NNNNN\n" +
                    "NHNHN\n" +
                    "NNNNN";

    //TODO: for testing only, remove
    public static final int SCREEN_X  = 800;
    public static final int SCREEN_Y = 600;
    public static final int DEFAULT_EXPLOSION_TIME = 10;
    public static final int DEFAULT_BOMB_RANGE = 3;
    public static final int DEFAULT_BOMB_DEPTH = 1; //Solid blocks have a height that is over depth
    public static final int DEFAULT_BOMB_DAMAGE = 2;



    public static final int DEFAULT_BLOCK_HP = 1;


    public enum EffectTypes {SPREAD, VANISH};

    public enum SpreadTypes {FLOW_AROUND_OBSTACLES, FLOW_CARTESIAN, FLOW_IGNORE_OBSTACLES};

    public enum CollidableType {
        HARD, // Nothing can pass through this
        SOFT  // Soft entities can pass through other soft entities
    }


    public enum TimeConstants {

        DEFAULT_BOMB_TIME(10);

        private int time;

        TimeConstants(int time) {
            this.time = time;
        }
    }


}