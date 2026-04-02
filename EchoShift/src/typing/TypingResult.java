package typing;
/**
 * TypingResult objects are used to keep track of the user's input.
 * This keeps track of the word being typed and is used to indicate when the word is complete or failed.
 *
 * @author Yasmine Suojhayer
 */
public class TypingResult {

    private boolean correct; // Tracks if the last character is correct.
    private boolean wordCompleted; // Tracks the number of words completed
    private boolean wordFailed; // Tracks if the word has failed
    private int errorDelta; // Tracks the errors made

    /**
     * This is the constructor for the TypingResult class.
     *
     * @param correct A flag tracking the correctness of the last character typed
     * @param wordCompleted A flag tracking if the word is complete
     * @param wordFailed A flag tracking if the user has failed their word.
     * @param errorDelta Tracks the number of errors made.
     */
    public TypingResult(boolean correct, boolean wordCompleted, boolean wordFailed, int errorDelta) {
        this.correct = correct;
        this.wordCompleted = wordCompleted;
        this.wordFailed = wordFailed;
        this.errorDelta = errorDelta;
    }

    /**
     * Returns a boolean indicating if the last letter was correct.
     *
     * @return A flag indicating if the last letter was correct.
     */
    public boolean isCorrect() {
        return correct;
    }

    /**
     * Returns a boolean indicating if the word is complete.
     *
     * @return A flag indicating if the word is complete.
     */
    public boolean isWordCompleted() {
        return wordCompleted;
    }

    /**
     * Returns a boolean indicating if the word has failed.
     *
     * @return A flag indicating if the word has failed.
     */
    public boolean isWordFailed() {
        return wordFailed;
    }

    /**
     * Returns an int indicating the number of errors made.
     *
     * @return The number of errors made.
     */
    public int getErrorDelta() {
        return errorDelta;
    }
}