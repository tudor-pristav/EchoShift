package echoshift.models;

/**
 * Represents the current user session.
 * Stores user account, statistics, and powerups.
 *
 * @author Tudor Mihai Pristav
 */
public class Session {
    private final UserAccount currentUser;
    private final UserStatistics currentStatistics;
    private PlayerPowerups powerups;

    /**
     * Initializes a session with user data.
     *
     * @param user current user account
     * @param stats user statistics
     * @param powerups user powerups
     */
    public Session(UserAccount user, UserStatistics stats, PlayerPowerups powerups) {
        this.currentUser = user;
        this.currentStatistics = stats;
        this.powerups = powerups;
    }

    /**
     * @return current user account
     */
    public UserAccount getCurrentUser() {
        return currentUser;
    }

    /**
     * @return current user statistics
     */
    public UserStatistics getCurrentStatistics() {
        return currentStatistics;
    }

    /**
     * @return current powerups
     */
    public PlayerPowerups getPowerUps() {
        return powerups;
    }

    /**
     * Updates the current powerups.
     *
     * @param powerups new powerups
     */
    public void setCurrentPowerups(PlayerPowerups powerups) {
        this.powerups = powerups;
    }
}