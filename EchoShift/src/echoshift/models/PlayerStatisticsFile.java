package echoshift.models;

/**
 * Wrapper class for storing player statistics in JSON.
 * Matches structure:
 * {
 *   "statistics": { ... }
 * }
 */
public class PlayerStatisticsFile {

    private UserStatistics statistics;

    public PlayerStatisticsFile(UserStatistics statistics) {
        this.statistics = statistics;
    }

    public UserStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(UserStatistics statistics) {
        this.statistics = statistics;
    }
}