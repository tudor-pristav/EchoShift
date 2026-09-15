import echoshift.backend.GameMap;
import echoshift.models.*;
import echoshift.nightscripts.Night;
import echoshift.services.*;
import typing.*;
import java.nio.file.*;

/** Dependency-free regression checks, run in a disposable data directory. */
public class RegressionTests {
    private static int checks;
    static void check(boolean condition, String message) {
        checks++;
        if (!condition) throw new AssertionError(message);
    }
    public static void main(String[] args) throws Exception {
        TypingEngine engine = new TypingEngine(new String[]{"cat"});
        check(engine.inputChar('C').isCorrect(), "Uppercase accepted");
        check(engine.inputChar('A').isCorrect(), "Mixed case accepted");
        check(engine.inputChar('T').isWordCompleted(), "Uppercase word completes");
        check(engine.inputChar('c').isCorrect(), "Next word does not crash");
        check(engine.inputChar('x').isWordFailed(), "Wrong character fails word");
        check(engine.getWordsCompleted() == 1 && engine.getErrorCount() == 1, "Typing totals");
        check(engine.calculateAccuracy() == 80.0, "Character accuracy");
        for (int difficulty = 1; difficulty <= 3; difficulty++) {
            String[] words = createWordBank.create(difficulty);
            check(words.length > 0, "Word bank packaged");
            for (String word : words) check(!word.isBlank(), "Word is nonempty");
        }
        try {
            new TypingEngine(new String[0]);
            throw new AssertionError("Empty bank should fail clearly");
        } catch (IllegalArgumentException expected) { checks++; }
        UserStatistics stats = new UserStatistics();
        for (int wpm : new int[]{30, 60, 90}) {
            stats.setGamesPlayed(); stats.setAverageWPM(wpm); stats.setAccuracy(90);
        }
        check(stats.getAverageWPM() == 60, "Average across three games");
        check(stats.getAccuracy() == 90, "Stable average accuracy");
        Night night = new Night(1, null, null);
        int[] saves = {0};
        night.setOnNightEnd(() -> saves[0]++);
        night.stopNight(); night.stopNight();
        check(saves[0] == 1, "Night saves only once");
        check(night.hasEnded() && !night.isWon(), "Interrupted shift is not a win");
        new Night(1, null, null).stopNight();
        GameMap map = new GameMap();
        check(!map.getConnections(0).isEmpty(), "Map has start connections");
        UserAccount player = new AccountCreationService().createAccount("regression", "test-only");
        String id = player.getId();
        check(new LoginService().login("regression", "test-only") != null, "New account login");
        check(new LoginService().login("regression", "wrong") == null, "Wrong password rejected");
        check(new UserDataRetrievalService().retrieveStatistics(id).getHighestLevel() == 1, "New player starts at night 1");
        new UserDataSaveService().saveStatistics(id, stats);
        check(new UserDataRetrievalService().retrieveStatistics(id).getAverageWPM() == 60, "Statistics round trip");
        PowerupStorageService store = new PowerupStorageService();
        store.addPowerup(id, PowerupType.INSTANT_LURE);
        check(store.usePowerup(id, PowerupType.INSTANT_LURE), "Purchased lure usable");
        check(!store.usePowerup(id, PowerupType.INSTANT_LURE), "Inventory cannot go negative");
        check(new AccountManagementService().deleteAccount(id), "Account deleted");
        check(!Files.exists(Path.of("data/playerData", id + ".json")), "Statistics removed");
        check(!Files.exists(Path.of("data/powerups", id + "-powerup.json")), "Powerups removed");
        System.out.println("PASS: " + checks + " regression checks");
    }
}
