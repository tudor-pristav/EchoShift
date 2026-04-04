package echoshift.models;

public class PlayerStatisticsFile {
    private UserStatistics statistics;

    public PlayerStatisticsFile() {
        this.statistics = new UserStatistics();
    }

    public PlayerStatisticsFile(UserStatistics statistics) {
        this.statistics = statistics;
    }

    public UserStatistics getStatistics() {
        return statistics;
    }
}