package echoshift.nightscripts;

import echoshift.backend.Entity;
import echoshift.UI.MapRenderer;
import javafx.animation.AnimationTimer;

public class Night {

    private final HourDiff difficulty;
    private final int nightNum;
    private long startTime;
    private int currentHour = 0;

    private AnimationTimer gameTimer;

    private int playerHealth;
    private final Entity entity;
    private final MapRenderer mapRenderer;

    private Runnable healthDecreaseCallback;
    private Runnable healthIncreaseCallback;

    public Night(int nightNum, Entity entity, MapRenderer mapRenderer) {
        this.nightNum = nightNum;
        this.entity = entity;
        this.mapRenderer = mapRenderer;
        this.playerHealth = 3;

        this.difficulty = new HourDiff(0, nightNum);
    }

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
                }

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

    private void updateEnemies() {
        double currentDiff = difficulty.getDifficulty();

        // Update Entity
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

    public void stopNight() {
        stopNight(0);
    }

    public void stopNight(int code) {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        System.out.println("Night " + nightNum + " has ended!");
        if (code == 1){
            System.out.println("You died at hour " + (currentHour+1));
        }
    }

    public void setOnHealthDecrease(Runnable callback) {
        this.healthDecreaseCallback = callback;
    }

    public void setOnHealthIncrease(Runnable callback) {
        this.healthIncreaseCallback = callback;
    }

    private void decreaseHealth() {
        playerHealth -= 1;
        if (healthDecreaseCallback != null) {
            healthDecreaseCallback.run();
        }
        if (playerHealth <= 0) {
            stopNight(1);
        }
    }

    private void addHealth() {
        playerHealth += 1;
        if (healthIncreaseCallback != null) {
            healthIncreaseCallback.run();
        }
    }

    public int getHealth() {
        return playerHealth;
    }

    public void instantLure(){
        entity.setCurrentRoom(0);
        mapRenderer.updateEntityPosition(entity);
    }

    public double getCurrentDifficulty() {
        return difficulty.getDifficulty();
    }


}