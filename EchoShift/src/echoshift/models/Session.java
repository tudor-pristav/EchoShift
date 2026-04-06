package echoshift.models;

public class Session {
    private final UserAccount currentUser;
    private final UserStatistics currentStatistics;
    private PlayerPowerups powerups;

    public Session(UserAccount user, UserStatistics stats, PlayerPowerups powerups) {
        this.currentUser = user;
        this.currentStatistics = stats;
        this.powerups = powerups;
    }

    public UserAccount getCurrentUser() {
        return currentUser;
    }

    public UserStatistics getCurrentStatistics() {
        return currentStatistics;
    }

    public PlayerPowerups getPowerUps(){
        return powerups;
    }
    public void setCurrentPowerups(PlayerPowerups powerups) {
        this.powerups = powerups;
    }
}
