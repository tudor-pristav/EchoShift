package echoshift.nightscripts;

import echoshift.backend.Entity;
import echoshift.backend.GameMap;
import echoshift.UI.MapRenderer;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Night class using only JUnit 5.
 * JavaFX toolkit is initialized once before all tests.
 */
@DisplayName("Night Tests (JUnit 5 + JavaFX)")
class NightTest {

    private GameMap realGameMap;
    private StubEntity stubEntity;
    private MapRenderer realRenderer;  // real renderer, but JavaFX is started
    private Night night;

    // init javafx
    @BeforeAll
    static void initJavaFX() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> latch.countDown());
        latch.await();
    }

    // stub for testing
    static class StubEntity extends Entity {
        private boolean attemptMoveResult = false;
        private int currentRoom = 0;
        private double lastSetDiff = -1;

        public StubEntity(GameMap gameMap, int startingRoom, double initialDifficulty) {
            super(gameMap, startingRoom, initialDifficulty);
            this.currentRoom = startingRoom;
        }

        @Override
        public boolean attemptMove() {
            if (attemptMoveResult) {
                currentRoom = 15;
                return true;
            }
            return false;
        }

        @Override
        public int getCurrentRoomId() {
            return currentRoom;
        }

        @Override
        public void setDiff(double diff) {
            this.lastSetDiff = diff;
            super.setDiff(diff);
        }

        void setAttemptMoveResult(boolean result) {
            this.attemptMoveResult = result;
        }

        public void setCurrentRoom(int room) {
            this.currentRoom = room;
        }

        double getLastSetDiff() {
            return lastSetDiff;
        }
    }

    // setup
    @BeforeEach
    void setUp() {
        realGameMap = new GameMap();
        stubEntity = new StubEntity(realGameMap, 0, 1.0);
        realRenderer = new MapRenderer(realGameMap);
        night = new Night(3, stubEntity, realRenderer);
        night.setOnNightEnd(() -> {});
        night.setOnHealthDecrease(() -> {});
        night.setOnHealthIncrease(() -> {});
    }

    // Finally, the actual tests
    @Test
    @DisplayName("Constructor sets health to 3")
    void constructorInitialisesHealth() {
        assertEquals(3, night.getHealth());
    }

    @Test
    @DisplayName("addHealth increases health and triggers callback")
    void addHealthIncreasesHealthAndTriggersCallback() {
        final int[] callbackCalled = {0};
        night.setOnHealthIncrease(() -> callbackCalled[0]++);

        night.addHealth();
        assertEquals(4, night.getHealth());
        assertEquals(1, callbackCalled[0]);
    }

    @Test
    @DisplayName("decreaseHealth reduces health and triggers callback (reflection)")
    void decreaseHealthReducesHealthAndTriggersCallback() throws Exception {
        final int[] callbackCalled = {0};
        night.setOnHealthDecrease(() -> callbackCalled[0]++);

        Method decreaseMethod = Night.class.getDeclaredMethod("decreaseHealth");
        decreaseMethod.setAccessible(true);

        decreaseMethod.invoke(night);
        assertEquals(2, night.getHealth());
        assertEquals(1, callbackCalled[0]);
    }

    @Test
    @DisplayName("decreaseHealth stops night when health reaches 0")
    void decreaseHealthStopsNightWhenHealthZero() throws Exception {
        Method decreaseMethod = Night.class.getDeclaredMethod("decreaseHealth");
        decreaseMethod.setAccessible(true);

        decreaseMethod.invoke(night); // 3->2
        decreaseMethod.invoke(night); // 2->1
        decreaseMethod.invoke(night); // 1->0
        assertEquals(0, night.getHealth());
        // Should not go negative
        assertDoesNotThrow(() -> decreaseMethod.invoke(night));
        assertEquals(0, night.getHealth());
    }

    @Test
    @DisplayName("instantLure sets entity room to 0 and updates renderer")
    void instantLureResetsEntityPosition() {
        stubEntity.setCurrentRoom(5);
        night.instantLure();
        assertEquals(0, stubEntity.getCurrentRoomId());
    }

    @Test
    @DisplayName("updateEnemies decreases health when entity reaches room 15")
    void updateEnemiesDecreasesHealthWhenEntityReachesRoom15() throws Exception {
        stubEntity.setAttemptMoveResult(true);

        Method updateEnemies = Night.class.getDeclaredMethod("updateEnemies");
        updateEnemies.setAccessible(true);
        updateEnemies.invoke(night);

        assertEquals(2, night.getHealth());
        assertEquals(night.getCurrentDifficulty(), stubEntity.getLastSetDiff(), 0.01);
    }

    @Test
    @DisplayName("updateEnemies does nothing if entity is null (via reflection)")
    void updateEnemiesHandlesNullEntity() throws Exception {
        var entityField = Night.class.getDeclaredField("entity");
        entityField.setAccessible(true);
        entityField.set(night, null);

        Method updateEnemies = Night.class.getDeclaredMethod("updateEnemies");
        updateEnemies.setAccessible(true);
        assertDoesNotThrow(() -> updateEnemies.invoke(night));
        assertEquals(3, night.getHealth());
    }

    @Test
    @DisplayName("setOnHourChange stores callback")
    void setOnHourChangeStoresCallback() throws Exception {
        Runnable mockCallback = () -> {};
        night.setOnHourChange(mockCallback);
        var field = Night.class.getDeclaredField("onHourChange");
        field.setAccessible(true);
        Runnable stored = (Runnable) field.get(night);
        assertSame(mockCallback, stored);
    }

    @Test
    @DisplayName("getCurrentDifficulty returns a non-negative double")
    void getCurrentDifficultyReturnsDouble() {
        assertTrue(night.getCurrentDifficulty() >= 0);
    }

    @Test
    @DisplayName("stopNight does not throw")
    void stopNightDoesNotThrow() {
        assertDoesNotThrow(() -> night.stopNight());
        assertDoesNotThrow(() -> night.stopNight(1));
    }
}