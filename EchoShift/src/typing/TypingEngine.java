package typing;

import java.util.Random;

/**
 * This class handles the typing mechanism of a game.
 * <br><br>
 *
 * It checks each input character sent against the word.
 * It also calculates the wpm and accuracy for the session.
 *
 * @author Yasmine Suojhayer
 */
public class TypingEngine {

    private final String[] wordList; // The array of words

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
     * This class checks the character typed by the user against the word they were givern.
     * @param c
     * @return
     */
    public TypingResult inputChar(char c) {

        char lowerC = Character.toLowerCase(c);

        if (wordFailed) {
            return new TypingResult(false, false, true, 0);
        }
        if (typedWord.isEmpty()) {
            startWordTimer(); // Starts only when user actually begins typing.
        }

        int index = typedWord.length();
        char expected = givenWord.charAt(index);

        boolean correct = (lowerC == expected);
        totalCharactersTyped++;

        if (correct) {
            typedWord += c;

            if (typedWord.equals(givenWord)) {
                // Word completed successfully
                wordsCompleted++;
                endWordTimer(); // finalize typing time
                TypingResult result = new TypingResult(true, true, false, 0);
                loadNextWord(); // next word
                return result;
            }

            return new TypingResult(true, false, false, 0);
        }

        // Incorrect letter → word fails
        errorCount++;
        wordFailed = true;
        endWordTimer(); // finalize typing time
        TypingResult result = new TypingResult(false, false, true, 1);
        loadNextWord(); // next word
        return result;
    }

    /** WPM based on total characters typed and cumulative typing time */
    public double calculateWPM() {
        if (typingTime <= 0) return 0;
        double minutes = typingTime / 60.0;
        double wordsTyped = totalCharactersTyped / 5.0;
        return wordsTyped / minutes;
    }

    public double calculateAccuracy() {
        if (totalCharactersTyped == 0) return 100.0;
        return ((double)(totalCharactersTyped - errorCount) / totalCharactersTyped) * 100;
    }

    public String getCurrentWord() {
        return givenWord;
    }

    public int getChar(){
        return totalCharactersTyped;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public int getWordsCompleted() {
        return wordsCompleted;
    }
}
