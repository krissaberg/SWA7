package wizard_team.wizards_tale.screens;

import java.util.HashSet;

class GameConfig {
    float gameDuration;
    boolean powerupsActive;
    public String hostUsername = null;
    HashSet<String> users = null;

    public GameConfig() {
        this.gameDuration = 60;
        this.powerupsActive = true;
    }

    public GameConfig(String hostUsername, HashSet users) {
        this.gameDuration = 60;
        this.powerupsActive = true;
        this.hostUsername = hostUsername;
        this.users = users;
    }

    public String print() {
        return String.format("Duration: %3.0f sec\nPowerups active: %b", gameDuration, powerupsActive);
    }
}
