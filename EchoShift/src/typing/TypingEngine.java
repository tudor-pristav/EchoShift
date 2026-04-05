package typing;

import java.io.IOException;
import java.util.Random;

/**
 * This class handles the typing mechanism of a game.
 * <br><br>
 *
 * It checks each input character sent against the word.
 * It also calculates the wpm and accuracy for the session.
 * It works closely with the TypingResult class.
 *
 * @author Yasmine Suojhayer
 * @see TypingResult
 */
public class TypingEngine {

    private String[] wordList; // The array of words

    private String givenWord; // The word the user needs to type
    private String typedWord; // The word typed by the user

    private int errorCount; // The number of errors for the session
    private boolean wordFailed; // Flag indicating status
    private int wordsCompleted; // The number of words completed this session
    private int totalCharactersTyped; // The number of chars typed used to calculate wpm

    private double typingTime; // Total time spent typing used for wpm
    private double wordStartTime; // The start time for the word used to remove idle time

    /**
     * The constructor for the typingEngine class.
     * It takes an array of words that will be used to test the players.
     *
     * @param arr An array of words that will act as the word bank for the level.
     */
    public TypingEngine(String[] arr) {
        wordList = arr;
        wordsCompleted = 0;
        totalCharactersTyped = 0;
        typingTime = 0;
        loadNextWord();
    }

    /**
     * This method randomly grabs the next word from the wordList.
     */
    private void loadNextWord() {
        Random rand = new Random();
        givenWord = wordList[rand.nextInt(wordList.length)];
        typedWord = "";
        wordFailed = false;
    }

    /**
     * This method gets the start time for the word.
     */
    private void startWordTimer() {

        wordStartTime = System.currentTimeMillis() / 1000.0;
    }

    /**
     * This method ends the typing timer.
     */
    private void endWordTimer() {
        if (wordStartTime != 0) {
            typingTime += (System.currentTimeMillis() / 1000.0) - wordStartTime;
            wordStartTime = 0;
        }
    }

    /**
     * This class checks the character typed by the user against the word they were given.
     *
     * @param c the character typed by the user
     * @return A TypingResult object staring information about the result.
     */
    public TypingResult inputChar(char c) {

        char lowerC = Character.toLowerCase(c);

        if (wordFailed) {
            return new TypingResult(false, false, true, 0);
        }
        if (typedWord.isEmpty()) {
            startWordTimer(); // Starts the timer only when user begins typing.
        }

        // Checks the same position in the two strings
        int index = typedWord.length();
        char expected = givenWord.charAt(index);

        boolean correct = (lowerC == expected); // Sets the typed char to lowercase for the comparison
        totalCharactersTyped++;

        // Handles if the character is correct
        if (correct) {
            typedWord += c;
            if (typedWord.equals(givenWord)) { // Tracks if the word is completed
                wordsCompleted++;
                endWordTimer(); // finalize typing time
                TypingResult result = new TypingResult(true, true, false, 0);
                loadNextWord(); // next word
                return result;
            }
            return new TypingResult(true, false, false, 0);
        }

        // If the letter is incorrect the word fails
        errorCount++;
        wordFailed = true;
        endWordTimer(); // finalize typing time
        TypingResult result = new TypingResult(false, false, true, 1);
        loadNextWord(); // Loads the next word
        return result;
    }

    /**
     * This class allows the difficulty of the word bank to be changed.
     *
     * @param difficulty This is the difficulty of the word bank chosen.
     * @throws IOException An exception is thrown if the input file is not found.
     */
    public void changeWordBank(int difficulty) throws IOException {
        wordList = createWordBank.create(difficulty);
    }

    /**
     * This calculates the current WPM for the session.
     *
     * @return The user's current WPM.
     */
    public double calculateWPM() {
        if (typingTime <= 0) return 0;
        double minutes = typingTime / 60.0;
        double wordsTyped = totalCharactersTyped / 5.0;
        return wordsTyped / minutes;
    }

    /**
     * This calculates the user's accuracy based on the number of mistyped words.
     *
     * @return The accuracy of the user.
     */
    public double calculateAccuracy() {
        if (totalCharactersTyped == 0) return 100.0;
        return ((double)(totalCharactersTyped - errorCount) / totalCharactersTyped) * 100;
    }

    /**
     * Gets the current word so it can be displayed.
     *
     * @return The current word being typed.
     */
    public String getCurrentWord() {
        return givenWord;
    }

    /**
     * Returns the total number of characters typed this session.
     *
     * @return The number of characters typed.
     */
    public int getChar(){
        return totalCharactersTyped;
    }

    /**
     * Returns the number of errors for the session.
     *
     * @return The number of errors.
     */
    public int getErrorCount() {
        return errorCount;
    }

    /**
     * Returns the number of words completed this session.
     *
     * @return The number of words completed.
     */
    public int getWordsCompleted() {
        return wordsCompleted;
    }
}
