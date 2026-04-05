package echoshift.models;

public class Session {
    private final UserAccount currentUser;
    private final UserStatistics currentStatistics;

    public Session(UserAccount user, UserStatistics stats) {
        this.currentUser = user;
        this.currentStatistics = stats;
    }

    public UserAccount getCurrentUser() {
        return currentUser;
    }

    public UserStatistics getCurrentStatistics() {
        return currentStatistics;
    }
}
