package wizard_team.wizards_tale.components.constants;

/**
 * Created by synnovehalle on 14/04/2018.
 */

public class Constants {
    public static final int DEFAULT_SPREADABLE_DEPTH = 3;
    public static final int PLAYER_START_X = 100;
    public static final int PLAYER_START_Y = 200;
    private static final float CELL_HEIGHT = 100f;
    private static final float CELL_WIDTH = 100f;

    public enum EffectTypes {SPREAD, VANISH};

    public enum TimeConstants {

        DEFAULT_BOMB_TIME(10);

        private int time;

        TimeConstants(int time) {
            this.time = time;
        }
    }


}