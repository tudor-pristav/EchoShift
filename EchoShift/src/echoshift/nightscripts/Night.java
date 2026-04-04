package echoshift.nightscripts;

import echoshift.backend.Entity;
import echoshift.backend.Listener;
import echoshift.UI.MapRenderer;
import javafx.animation.AnimationTimer;

public class Night {

    private final HourDiff difficulty;
    private final int nightNum;
    private long startTime;
    private int currentHour = 0;

    private AnimationTimer gameTimer;

    private final Entity entity;
    private final Listener listener;
    private final MapRenderer mapRenderer;

    public Night(int nightNum, Entity entity, Listener listener, MapRenderer mapRenderer) {
        this.nightNum = nightNum;
        this.entity = entity;
        this.listener = listener;
        this.mapRenderer = mapRenderer;

        this.difficulty = new HourDiff(0, nightNum);
    }

    public void start() {
        startTime = System.currentTimeMillis();

        gameTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                long elapsedMs = System.currentTimeMillis() - startTime;

                // Update hour every 45 seconds
                int newHour = (int) (elapsedMs / 45000L);
                if (newHour > currentHour && newHour <= 5) {
                    currentHour = newHour;
                    difficulty.setHourDiff(currentHour, nightNum);
                    System.out.println("Hour is now " + currentHour + " | Difficulty = " + difficulty.getDifficulty());
                }

                // Move every 1 second for now
                if (elapsedMs - lastUpdate >= 1000) {
                    lastUpdate = elapsedMs;
                    updateEnemies();
                }

                // Night ends after 225 seconds (5 hours)
                if (elapsedMs >= 225000) {
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
            }
        }

        // Update Listener
//        if (listener != null) {
//            listener.setDiff(currentDiff);
//            if (listener.attemptMove()) {
//                mapRenderer.updateListenerPosition(listener);
//            }
//        }
    }

    public void stopNight() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        System.out.println("Night " + nightNum + " has ended!");
        // TODO: Trigger win condition, score, next night, etc.
    }

    public double getCurrentDifficulty() {
        return difficulty.getDifficulty();
    }
}