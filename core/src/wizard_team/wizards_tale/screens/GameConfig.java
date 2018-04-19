package wizard_team.wizards_tale.screens;

class GameConfig {
    float gameDuration;
    boolean powerupsActive;
    public String hostUsername;

    public GameConfig() {
        this.gameDuration = 60;
        this.powerupsActive = true;
    }

    public String print() {
        return String.format("Duration: %3.0f sec\nPowerups active: %b", gameDuration, powerupsActive);
    }
}
