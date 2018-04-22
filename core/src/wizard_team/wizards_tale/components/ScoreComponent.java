package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;

public class ScoreComponent implements Component {
    public int deaths;
    public int kills;

    public ScoreComponent(int deaths, int kills) {
        this.deaths = deaths;
        this.kills = kills;
    }
}
