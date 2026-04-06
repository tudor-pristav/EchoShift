package echoshift.nightscripts;


/**
 * Difficulty getter and setter for the game Echo Shift.
 * To initialize difficulty using a given hour and day, use constructor HourDiff(hour, night).
 * To set a new difficulty, use setter setHourDiff(hour,night).
 * To get the difficulty, use getter getDifficulty(hour, night).
 * @version 1.0.0
 * @author Bob Zhang
 */
public class HourDiff {
    /**The difficulty value of the game**/
    private double difficulty;

    /**
     * HourDiff constructor creates a new HourDiff object containing a difficulty defined by the difficulty formula and parameters given.
     * @param hour Represents the current hour of the ongoing game.
     * @param night Represents the night the player is currently playing on.
     */
    public HourDiff(int hour, int night){
        difficulty = ((Math.log(hour + 1) / Math.log(2)) / (Math.log(Math.E))) * 0.35 * night + 3;
    }

    /**
     * HourDiff setter will set the difficulty to a new value by processing the given hour and night values.
     * @param hour Represents the current hour of the ongoing game.
     * @param night Represents the current night the player is currently playing on.
     */
    public void setHourDiff(int hour, int night){
        difficulty = ((Math.log(hour + 1) / Math.log(2)) / (Math.log(Math.E))) * 0.35 * night + 3;
    }

    /**
     * HourDiff getter that will retrieve the difficulty double value when called.
     * @return The difficulty value as a double value.
     */
    public double getDifficulty(){
        return difficulty;
    }
}
