package echoshift.models;

/**
 * Represents a high score entry with username and score.
 *
 * @author Tudor Mihai Pristav
 */
public class HighScoreEntry {
    private final String username;
    private final int highScore;

    /**
     * Creates a high score entry.
     *
     * @param username player's username
     * @param highScore player's high score
     */
    public HighScoreEntry(String username, int highScore) {
        this.username = username;
        this.highScore = highScore;
    }

    /**
     * Returns the username.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the high score.
     *
     * @return high score value
     */
    public int getHighScore() {
        return highScore;
    }
}