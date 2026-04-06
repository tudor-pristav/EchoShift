package echoshift.models;

/**
 * Represents all gameplay statistics and currency for a player.
 */
public class UserStatistics {

    private double gamesPlayed;

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
    private int coins;

    // --- Powerups ---
    private int numExtraLives;
    private int numInstantLures;
    private int numEasierWords;

    /**
     * Constructor for the class, creates default values for all user stats.
     */
    public UserStatistics() {
        this.gamesPlayed = 0;
        this.averageWPM = 0;
        this.peakWPM = 0;
        this.accuracy = 0;
        this.errorCount = 0;
        this.totalTimePlayed = 0;
        this.highScore = 0;
        this.highestLevel = 1;
        this.wordsTyped = 0;
        this.coins = 0;
    }

    /**
     * Method calculates the user's average word per minute statistic throughout all games played. Called
     * whenever a user finishes a game.
     * @param curWPM The words per minute from them ost previously played game.
     */
    public void setAverageWPM(double curWPM) {
        averageWPM = ((this.getAverageWPM() + curWPM) / gamesPlayed);
    }

    /**
     * Method sets the users highest word per minute statistic.
     * @param curWPM The highest words per minute statistic from a game.
     */
    public void setPeakWPM(double curWPM) {
        if (curWPM > peakWPM) {
            peakWPM = curWPM;
        }
    }

    /**
     * Method sets/updates the users accuracy statistic.
     * @param curAccuracy The accuracy of the user, calculated across all games played.
     */
    public void setAccuracy(double curAccuracy) {
        accuracy = ((this.getAccuracy() + curAccuracy) / gamesPlayed);
    }

    /**
     * Method adds the number of errors from a recently played game and adds them to the previous
     * number of errors.
     * @param numErr The number of errors from the most recently played game.
     */
    public void setErrorCount(int numErr) {
        errorCount += numErr;
    }

    /**
     * Method sets/updates the time the user has played the game.
     * @param curTimePlayed The amount of time the user has played from the most recently played game.
     */
    public void setTotalTimePlayed(double curTimePlayed) {
        totalTimePlayed += curTimePlayed;
    }

    /**
     * Method sets the users highest score statistic.
     * @param curScore The score from the most recently played game.
     */
    public void setHighScore(int curScore) {
        if (curScore > highScore) {
            highScore = curScore;
        }
    }

    /**
     * Method sets the users highest level unlocked that can be played. Called after the user beats a level.
     * @param curLevel The level the user has recently beaten.
     */
    public void setHighestLevel(int curLevel) {
        if (curLevel == highestLevel) {
            highestLevel += 1;
        }
    }

    /**
     * Method sets the total words typed statistic.
     * @param curWords The number of words the user has typed out from the previous game.
     */
    public void setWordsTyped(int curWords) {
        wordsTyped += curWords;
    }

    /**
     * Sets the player's coins.
     * @param coins the new coin amount
     */
    public void setCoins(int coins) {
        this.coins += coins;
    }

    /**
     * Sets the number of games played.
     */
    public void setGamesPlayed(){
        gamesPlayed = gamesPlayed + 1;
    }

    /**
     * Method sets/updates the player's number of extra life powerups.
     * @param change The change in the amount of extra life powerups
     */
    public void setExtraLifeCount(int change){
        numExtraLives += change;
    }

    /**
     * Method sets/updates the player's number of instant lure powerups.
     * @param change The change in the amount of instant lure powerups
     */
    public void setInstantLureCount(int change){
        numInstantLures += change;
    }

    /**
     * Method sets/updates the player's number of easier words powerups.
     * @param change The change in the amount of easier words powerups
     */
    public void setEasierWordsCount(int change){
        numEasierWords += change;
    }

    // --- Getters ---

    /**
     * Method retrieves the average words per minute statistic.
     * @return The average words per minute statistic.
     */
    public double getAverageWPM() {
        return averageWPM;
    }

    /**
     * Method retrieves the highest words per minute statistic.
     * @return The highest words per minute statistic.
     */
    public double getPeakWPM() {
        return peakWPM;
    }

    /**
     * Method retrieves the total accuracy statistic.
     * @return The total accuracy statistic.
     */
    public double getAccuracy() {
        return accuracy;
    }

    /**
     * Method retrieves the total error count statistic.
     * @return The total error count statistic.
     */
    public int getErrorCount() {
        return errorCount;
    }

    /**
     * Method retrieves the total time played statistic.
     * @return The total time played statistic.
     */
    public double getTotalTimePlayed() {
        return totalTimePlayed;
    }

    /**
     * Method retrieves the highest score the user has obtained.
     * @return The highest score belonging to the player.
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Method retrieves the player's highest unlocked level.
     * @return The player's highest unlocked level.
     */
    public int getHighestLevel() {
        return highestLevel;
    }

    /**
     * Method retrieves total words typed statistic.
     * @return The total words typed statistic.
     */
    public int getWordsTyped() {
        return wordsTyped;
    }

    /**
     * Method retrieves the coins the user has.
     * @return The coins currently in the player's inventory.
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Method retrieves the player's number of extra life powerups.
     * @return The player's inventory of extra life powerups.
     */
    public int getNumExtraLives(){
        return numExtraLives;
    }

    /**
     * Method retrieves the player's number of instant lure powerups.
     * @return The player's inventory of instant lure powerups.
     */
    public int getNumInstantLures(){
        return numInstantLures;
    }

    /**
     * Method retrieves the player's number of easier words powerups.
     * @return The player's inventory of easier words powerups.
     */
    public int getNumEasierWords(){
        return numEasierWords;
    }
}