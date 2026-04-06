package echoshift.models;

/**
 * Wrapper for player statistics JSON structure.
 * Contains a single statistics object.
 *
 * @author Tudor Mihai Pristav
 */
public class PlayerStatisticsFile {

    private UserStatistics statistics;

    /**
     * Initializes the wrapper with player statistics.
     *
     * @param statistics player statistics
     */
    public PlayerStatisticsFile(UserStatistics statistics) {
        this.statistics = statistics;
    }

    /**
     * Returns the stored statistics.
     *
     * @return user statistics
     */
    public UserStatistics getStatistics() {
        return statistics;
    }

    /**
     * Sets the player statistics.
     *
     * @param statistics user statistics
     */
    public void setStatistics(UserStatistics statistics) {
        this.statistics = statistics;
    }
}