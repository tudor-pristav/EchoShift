package echoshift.backend;

import java.util.*;

public class Listener extends Entity {

    private int currentRoomId;
    private double frozenRemaining;

    private int luredTargetRoomId;
    private boolean isLured = false;

    /**
     * Constructor
     * @param gameMap the shared GameMap (new version)
     * @param startingRoomId Can be any room other than the office
     */
    public Listener(GameMap gameMap, int startingRoomId, double difficulty) {
        super(gameMap, startingRoomId, difficulty);
        this.frozenRemaining = 0.0;
    }


    public void lureTo(int audioDeviceRoomId) {
        this.luredTargetRoomId = audioDeviceRoomId;
        this.isLured = true;
    }

    public void endLure() {
        this.isLured = false;
        this.luredTargetRoomId = -1;
    }

    @Override
    public boolean attemptMove() {
        if (isFrozen()) {
            frozenRemaining -= 1.0;
            if (frozenRemaining < 0) frozenRemaining = 0;
            return false;
        }

        int targetRoomId = isLured ? luredTargetRoomId : 15;
        if (targetRoomId == currentRoomId) {
            return false;
        }

        int chance = getMovementChance();
        if (Math.random() * 100 < chance) {
            int nextRoomId = getNextRoomTowardTarget(targetRoomId);
            if (nextRoomId != currentRoomId && nextRoomId != -1) {
                currentRoomId = nextRoomId;

                // PRINT NEW LOCATION
                System.out.println("Listener moved to room: " + currentRoomId
                        + " (Difficulty: " + String.format("%.1f", currentDifficulty) + ")");

                return true;
            }
        }
        return false;
    }

    /**
     * Helper method that selects the next room for the shortest path to a target (office or the lure target)
     * @param targetId Node ID of the target
     * @return The Node ID of the selected room
     */
    private int getNextRoomTowardTarget(int targetId) {
        List<Integer> path = findShortestPath(currentRoomId, targetId);
        if (path == null || path.size() < 2) {
            return -1;
        }
        return path.get(1);
    }

    /**
     * BFS shortest path
     * @param start The start of the path, usually where the Listener currently is.
     * @param goal The goal of the path, usually the Office or the lure target
     * @return A ordered List of the IDs of the Nodes on the path
     */
    private List<Integer> findShortestPath(int start, int goal) {
        if (start == goal) {
            return List.of(start);
        }

        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> parent = new HashMap<>();

        queue.add(start);
        parent.put(start, null);

        while (!queue.isEmpty()) {
            Integer current = queue.poll();

            for (Integer neighbor : gameMap.getConnections(current)) {
                if (!parent.containsKey(neighbor)) {
                    parent.put(neighbor, current);
                    queue.add(neighbor);

                    if (neighbor.equals(goal)) {
                        // reconstruct path
                        List<Integer> path = new ArrayList<>();
                        int at = goal;
                        while (at>=0) {
                            path.add(0, at);
                            at = parent.get(at);
                        }
                        return path;
                    }
                }
            }
        }
        return null; // no path
    }

    public boolean isFrozen() {
        return frozenRemaining > 0;
    }

    public void freeze(int duration) {
        frozenRemaining = Math.max(frozenRemaining, duration);
    }

    public int getCurrentRoomId() {
        return currentRoomId;
    }

    public void setCurrentRoom(int roomId) {
        this.currentRoomId = roomId;
    }

    public void applyGlitchTo(SystemDevice device) {
        if (device != null && device.isFunctional()) {
            new Glitch(device, currentDifficulty*10);
        }
    }

    /**
     * Returns all directly connected room names from current position.
     */
    public List<Integer> getCurrentConnections() {
        return gameMap.getConnections(currentRoomId);
    }
}