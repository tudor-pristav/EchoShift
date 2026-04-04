package echoshift.backend;

import java.util.List;
import java.util.Objects;

/**
 * Base class for all enemies.
 */
public class Entity {

    protected final GameMap gameMap;
    protected double currentDifficulty = 1.0;
    protected int currentRoomId;

    public Entity(GameMap gameMap, int startingRoomId, double initialDifficulty) {
        this.gameMap = Objects.requireNonNull(gameMap);
        this.currentRoomId = startingRoomId;
        setDiff(initialDifficulty);
    }

    public void setDiff(double diff) {
        this.currentDifficulty = Math.max(1.0, diff);
    }

    /**
     * Attempt to move and print new location when successful.
     */
    public boolean attemptMove() {
        int chance = getMovementChance();

        if (Math.random() * 100 < chance) {
            int nextRoomId = getRandomNextRoom(currentRoomId);
            if (nextRoomId != currentRoomId) {
                currentRoomId = nextRoomId;

                System.out.println("Entity moved to room: " + currentRoomId
                        + " (Difficulty: " + String.format("%.1f", currentDifficulty) + ")");

                return true;
            }
        }
        return false;
    }

    protected int getMovementChance() {
        return (int) Math.min(95, 5 + currentDifficulty * 8);
    }

    protected int getRandomNextRoom(int current) {
        List<Integer> neighbors = gameMap.getConnections(current);
        if (neighbors.isEmpty()) return current;
        return neighbors.get((int) (Math.random() * neighbors.size()));
    }

    public int getCurrentRoomId() {
        return currentRoomId;
    }

    public void setCurrentRoom(int roomId) {
        this.currentRoomId = roomId;
    }
}