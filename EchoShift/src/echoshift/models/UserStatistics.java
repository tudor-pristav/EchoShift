package echoshift.models;

/**
 * Represents all gameplay statistics and currency for a player.
 */
public class UserStatistics {

    // --- Typing Speed ---
    private double averageWPM;
    private double peakWPM;

    // --- Accuracy & Errors ---
    private double accuracy;
    private int errorCount;

    // --- Time Tracking ---
    private double totalTimePlayed;

    // --- Performance ---
    private int highScore;
    private int highestLevel;

    // --- Activity ---
    private int wordsTyped;

    // --- Currency ---
    private String coins;

    // --- Constructor (default values = 0) ---
    public UserStatistics() {
        this.averageWPM = 0;
        this.peakWPM = 0;
        this.accuracy = 0;
        this.errorCount = 0;
        this.totalTimePlayed = 0;
        this.highScore = 0;
        this.highestLevel = 1;
        this.wordsTyped = 0;
        this.coins = "0";
    }

    // --- Getters ---
    public double getAverageWPM() {
        return averageWPM;
    }

    public double getPeakWPM() {
        return peakWPM;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public double getTotalTimePlayed() {
        return totalTimePlayed;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getHighestLevel() {
        return highestLevel;
    }

    public int getWordsTyped() {
        return wordsTyped;
    }

    public String getCoins() {
        return coins;
    }
}