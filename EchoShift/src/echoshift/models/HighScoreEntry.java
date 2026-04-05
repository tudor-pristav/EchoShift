package echoshift.models;

/**
 * Represents one row in the global high score table.
 */
public class HighScoreEntry {
    private final String username;
    private final int highScore;

    public HighScoreEntry(String username, int highScore) {
        this.username = username;
        this.highScore = highScore;
    }

    public String getUsername() {
        return username;
    }

    public int getHighScore() {
        return highScore;
    }
}