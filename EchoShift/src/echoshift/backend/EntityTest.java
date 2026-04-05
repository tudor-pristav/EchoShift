package echoshift.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Entity class.
 */
@DisplayName("Entity Tests")
class EntityTest {

    private GameMap gameMap;
    private Entity entity;

    @BeforeEach
    void setUp() {
        gameMap = new GameMap();
        entity = new Entity(gameMap, 0, 1.0);
    }

    // Basic constructor and getter and setter

    @Test
    @DisplayName("Constructor initialises room and difficulty correctly")
    void constructorInitialisesFields() {
        assertEquals(0, entity.getCurrentRoomId());
        // difficulty should be at least 1.0
        assertEquals(1.0, entity.currentDifficulty, 0.01);
    }

    @Test
    @DisplayName("setDiff clamps value to minimum 1.0")
    void setDiffClampsToMinimum() {
        entity.setDiff(0.5);
        assertEquals(1.0, entity.currentDifficulty, 0.01);

        entity.setDiff(-5.0);
        assertEquals(1.0, entity.currentDifficulty, 0.01);

        entity.setDiff(2.5);
        assertEquals(2.5, entity.currentDifficulty, 0.01);
    }

    @Test
    @DisplayName("getCurrentRoomId and setCurrentRoom work correctly")
    void getAndSetCurrentRoom() {
        entity.setCurrentRoom(5);
        assertEquals(5, entity.getCurrentRoomId());
        entity.setCurrentRoom(15);
        assertEquals(15, entity.getCurrentRoomId());
    }

    // Movement Chance

    @Test
    @DisplayName("getMovementChance increases with difficulty and caps at 95")
    void movementChanceCalculation() {
        entity.setDiff(1.0);
        int chance = entity.getMovementChance();
        // formula: min(95, 5 + diff * 8) => 5 + 8 = 13
        assertEquals(13, chance);

        entity.setDiff(5.0);
        assertEquals(45, entity.getMovementChance()); // 5 + 40 = 45

        entity.setDiff(12.0);
        assertEquals(95, entity.getMovementChance()); // caps at 95
    }

    // getRandomNextRoom with filtering

    @Test
    @DisplayName("getRandomNextRoom never returns a room <= current when not lured (if any > current exists)")
    void randomNextRoomFiltersLowerOrEqualRooms() {
        // Node 11 has neighbors [2,8,12]
        entity.setCurrentRoom(11);
        List<Integer> neighbors = gameMap.getConnections(11);
        assertTrue(neighbors.contains(2));
        assertTrue(neighbors.contains(8));
        assertTrue(neighbors.contains(12));

        // getRandomNextRoom should never return 2 or 8
        for (int i = 0; i < 100; i++) {
            int next = entity.getRandomNextRoom(11);
            assertEquals(12, next, "Should only return room 12, got " + next);
        }
    }

    @Test
    @DisplayName("getRandomNextRoom returns only from available neighbors (when all > current)")
    void randomNextRoomSelectsOnlyFromNeighbors() {
        entity.setCurrentRoom(1);
        List<Integer> expectedNeighbors = List.of(2, 3, 5);
        for (int i = 0; i < 100; i++) {
            int next = entity.getRandomNextRoom(1);
            assertTrue(expectedNeighbors.contains(next), "Returned " + next + " not in " + expectedNeighbors);
        }
    }

    @Test
    @DisplayName("getRandomNextRoom returns 0 when no valid neighbors after filtering")
    void randomNextRoomReturnsZeroWhenNoValidNeighbors() {
        entity.setCurrentRoom(15);
        List<Integer> expectedNeighbors = List.of(0);
        for (int i = 0; i < 100; i++) {
            int next = entity.getRandomNextRoom(15);
            assertTrue(expectedNeighbors.contains(next), "Returned " + next + " not in " + expectedNeighbors);
        }
    }

    // ==================== attemptMove (partial test – randomness handled statistically) ====================


    @DisplayName("attemptMove does not crash and respects movement chance (statistical)")
    @RepeatedTest(10)
    void attemptMoveDoesNotCrash() {
        // This test does not assert exact movement but ensures no exceptions
        entity.setDiff(10.0); // high chance (95%)
        int oldRoom = entity.getCurrentRoomId();
        boolean moved = entity.attemptMove();
        // The method may return true or false; we just verify it returns a boolean.
        // We could also check that currentRoomId changes only when moved is true.
        if (moved) {
            int newRoom = entity.getCurrentRoomId();
            assertNotEquals(oldRoom, newRoom);
            // The new room should be a neighbor of old room (and > old if not lured)
            List<Integer> neighbors = gameMap.getConnections(oldRoom);
            assertTrue(neighbors.contains(newRoom));
            // Because isLured is always false, newRoom should be > oldRoom
            assertTrue(newRoom > oldRoom);
        } else {
            assertEquals(oldRoom, entity.getCurrentRoomId());
        }
    }
}
