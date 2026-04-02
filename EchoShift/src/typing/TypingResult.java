package typing;
/**
 * TypingResult objects are used to keep track of the user's input.
 * This keeps track
 */
public class TypingResult {

    private boolean correct;
    private boolean wordCompleted;
    private boolean wordFailed;
    private int errorDelta;

    public TypingResult(boolean correct, boolean wordCompleted, boolean wordFailed, int errorDelta) {
        this.correct = correct;
        this.wordCompleted = wordCompleted;
        this.wordFailed = wordFailed;
        this.errorDelta = errorDelta;
    }

    public boolean isCorrect() {
        return correct;
    }

    public boolean isWordCompleted() {
        return wordCompleted;
    }

    public boolean isWordFailed() {
        return wordFailed;
    }

    public int getErrorDelta() {
        return errorDelta;
    }
}