package echoshift.backend;

import java.util.List;
import java.util.Objects;

/**
 * Base class for all enemies.
 * This class controls the Entity's movement and difficulty.
 *
 * @author Ho Long Adrian Lee
 */
public class Entity {

    protected final GameMap gameMap;
    protected double currentDifficulty = 1.0;
    protected int currentRoomId;
    private int luredTargetRoomId;
    private boolean isLured = false;

    /**
     * This Is the constructor of the Entity class.
     * It provides the Entity with a gameMap object to define the available moves and sets the initial difficulty and room.
     *
     * @param gameMap The Environment the Entity will move through.
     * @param startingRoomId Where the Entity starts at the beginning of the level.
     * @param initialDifficulty The initial difficulty of the level.
     */
    public Entity(GameMap gameMap, int startingRoomId, double initialDifficulty) {
        this.gameMap = Objects.requireNonNull(gameMap);
        this.currentRoomId = startingRoomId;
        setDiff(initialDifficulty);
    }

    /**
     * This method makes sure the assigned difficulty is not below 1.
     *
     * @param diff The new difficulty being set.
     */
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
                        + " (Difficulty: " + String.format("%.1f", currentDifficulty) + ", Time: "+ (10-currentDifficulty)+")");

                return true;
            }
        }
        return false;
    }

    /**
     * This is the chance the Entity will move each time unit.
     *
     * @return An int representing the movement chance.
     */
    protected int getMovementChance() {
        return (int) Math.min(95, 5 + currentDifficulty * 8);
    }

    /**
     * This randomly chooses the next room for the Entity to move to.
     * It forces the Entity to move to a room closer to the player.
     *
     * @param current The ID of the Entity's location node.
     * @return The ID of the node the Entity is moving to.
     */
    protected int getRandomNextRoom(int current) {
        List<Integer> neighbors = gameMap.getConnections(current);
        if (!isLured) neighbors.removeIf(roomId -> roomId <= current);
        if (neighbors.isEmpty()) return 0;
        return neighbors.get((int) (Math.random() * neighbors.size()));
    }

    /**
     * This gets the ID of the current node the Entity is in.
     *
     * @return The ID of the node the Entity is in.
     */
    public int getCurrentRoomId() {
        return currentRoomId;
    }

    /**
     * This method is used to set the Entity's location.
     *
     * @param roomId The ID of the Node the Entity will move to.
     */
    public void setCurrentRoom(int roomId) {
        this.currentRoomId = roomId;
    }
}