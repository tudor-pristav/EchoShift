package echoshift.nightscripts;

import echoshift.backend.Entity;
import echoshift.UI.MapRenderer;
import javafx.animation.AnimationTimer;

/**
 * This class handles the Logic of a Night/level for the Echo Shift game.
 *
 * @author Ho Long Adrian Lee
 */
public class Night {

    private final HourDiff difficulty;
    private final int nightNum;
    private long startTime;
    private int currentHour = 0;

    private AnimationTimer gameTimer;

    private int playerHealth;
    private final Entity entity;
    private final MapRenderer mapRenderer;

    private Runnable nightEndCallback;
    private Runnable healthDecreaseCallback;
    private Runnable healthIncreaseCallback;

    private Runnable onHourChange;


    /**
     * The constructor for the Night class.
     * This keeps a record of the Entity and the map of the Night.
     * This constructor also sets the initial difficulty of the Night.
     *
     * @param nightNum The level selected by the player.
     * @param entity The enemy for this level.
     * @param mapRenderer The graphic instructions for the Map.
     */
    public Night(int nightNum, Entity entity, MapRenderer mapRenderer) {
        this.nightNum = nightNum;
        this.entity = entity;
        this.mapRenderer = mapRenderer;
        this.playerHealth = 3;

        this.difficulty = new HourDiff(0, nightNum);
    }

    /**
     * This method handles the time segments in the game incrementing the time.
     */
    public void start() {
        startTime = System.currentTimeMillis();

        gameTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                long elapsedMs = System.currentTimeMillis() - startTime;

                // Update hour every 60 seconds
                int newHour = (int) (elapsedMs / 60000L);
                if (newHour > currentHour && newHour <= 5) {
                    currentHour = newHour;
                    difficulty.setHourDiff(currentHour, nightNum);
                    System.out.println("Hour is now " + currentHour + " | Difficulty = " + difficulty.getDifficulty());


                    if (onHourChange != null) {
                        onHourChange.run();
                    }
                }
                //Set a minimum timer on the rate at which the entity will move, depending on the difficulty.
                if (elapsedMs - lastUpdate >= (10-difficulty.getDifficulty())*1000) {
                    lastUpdate = elapsedMs;
                    updateEnemies();
                }

                // Night ends after 225 seconds (5 hours)
                if (elapsedMs >= 360000) {
                    stopNight();
                }
            }
        };

        gameTimer.start();
    }


    /**
     * This class prompts the Entity to make a move on the map.
     */
    private void updateEnemies() {
        double currentDiff = difficulty.getDifficulty();

        // Update Entity position and check if entity position causes events.
        if (entity != null) {
            entity.setDiff(currentDiff);
            if (entity.attemptMove()) {
                mapRenderer.updateEntityPosition(entity);
                if (entity.getCurrentRoomId() == 15) {
                    decreaseHealth();
                }
            }
        }
    }

    /**
     * This method ends the Night if something is triggered.
     */
    public void stopNight() {
        stopNight(0);
    }

    /**
     * Method provides the current hour to the caller.
     * @return The current hour of the night.
     */
    public int getCurrentHour(){
        return currentHour;
    }

    public int getNightNum(){
        return nightNum;
    }

    /**
     * This stops the Night with a specific error code.
     *
     * @param code The error code.
     */
    public void stopNight(int code) {
        if (gameTimer != null) {
            gameTimer.stop();
        }

        //TODO Remove printing tests,replace with visual message/popup
        System.out.println("Night " + nightNum + " has ended!");
        if (code == 1){
            System.out.println("You died at hour " + (currentHour+1));
        }
        nightEndCallback.run();
    }

    /**
     *Method updates the player's personal statistics after the night has ended.
     * @param callback Contains player's personal statistics.
     */
    public void setOnNightEnd(Runnable callback) { this.nightEndCallback = callback; }

    /**
     * This changes the visuals for the health bar when the player loses a life.
     *
     * @param callback An object that allows the visuals to reset.
     */
    public void setOnHealthDecrease(Runnable callback) {
        this.healthDecreaseCallback = callback;
    }

    /**
     * This changes the visuals for the health bar when the player gains a life.
     *
     * @param callback An object that allows the visuals to reset.
     */
    public void setOnHealthIncrease(Runnable callback) {
        this.healthIncreaseCallback = callback;
    }

    /**
     * This class removes a life from the player.
     */
    private void decreaseHealth() {
        if (playerHealth > 0) {
            playerHealth -= 1;
        }
        //If player health reaches 0 or below 0, the game ends.
        if (playerHealth <= 0) {
            stopNight(1);
        }
        if (healthDecreaseCallback != null) {
            healthDecreaseCallback.run();
        }
    }

    /**
     * This class adds a life to the player.
     */
    public void addHealth() {
        playerHealth += 1;
        if (healthIncreaseCallback != null) {
            healthIncreaseCallback.run();
        }
    }

    /**
     * This method returns the current health of the player.
     *
     * @return The current health of the player.
     */
    public int getHealth() {
        return playerHealth;
    }

    /**
     * This is used for the instant lure power up it resets the Entity's position to 0.
     */
    public void instantLure(){
        entity.setCurrentRoom(0);
        mapRenderer.updateEntityPosition(entity);
    }

    /**
     * This returns the current difficulty of the level.
     *
     * @return The current Night difficulty.
     */
    public double getCurrentDifficulty() {
        return difficulty.getDifficulty();
    }

    /**
     * This is a listener for the hour changed used to label the hour on the game screen.
     *
     * @param r The listener for the hour change.
     */
    public void setOnHourChange(Runnable r) {
        this.onHourChange = r;
    }

}