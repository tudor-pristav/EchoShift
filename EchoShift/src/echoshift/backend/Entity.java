package echoshift.backend;

import java.util.List;
import java.util.Objects;

/**
 * Base class for all game enemies (regular enemy and Listener).
 * Difficulty (set by NightDifficulty schedule) controls movement chance.
 */
public class Entity {
    protected final GameMap gameMap;
    protected int currentDifficulty;
    private int currentRoomId;

    public Entity(GameMap gameMap, int startingRoomId, int difficulty) {
        this.currentDifficulty = difficulty;
        this.gameMap = Objects.requireNonNull(gameMap, "GameMap cannot be null");
        this.currentRoomId = startingRoomId;
    }

    public void setDiff(int diff) {
        if (diff < 1) diff = 1;
        this.currentDifficulty = diff;
    }

    /**
     * Entity takes the difficulty to calculate the probability of moving
     * @return Movement Successful
     */
    public boolean attemptMove() {
        int chance = getMovementChance();
        if (Math.random() * 4 < chance) {
            int nextRoomId = getRandomNextRoom(currentRoomId);
            if (!(nextRoomId == currentRoomId)) {
                currentRoomId = nextRoomId;
                return true;
            }
        }
        return false;
    }

    /**
     * Randomly choose a connected room as the candidate to move to
     * @param currentRoomId The ID of the current room
     * @return The ID of the chosen candidate room
     */
    private int getRandomNextRoom(int currentRoomId) {
        List<Integer> possibleRooms = gameMap.getConnections(currentRoomId);
        if (!possibleRooms.isEmpty()) {
            return possibleRooms.get((int)(Math.random() * possibleRooms.size()) + 1);
        }
        return currentRoomId;
    }

    /**
     * Returns probability in percentage
     */
    public int getMovementChance() {
        return currentDifficulty;
    }
}