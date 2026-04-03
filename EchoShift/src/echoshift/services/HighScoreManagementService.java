package echoshift.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import echoshift.models.UserAccount;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Service responsible for resetting all account high scores.
 */
public class HighScoreManagementService {

    private static final String PLAYER_DATA_FOLDER = "data/playerData";
    private final LoginService loginService;

    public HighScoreManagementService() {
        this.loginService = new LoginService();
    }

    /**
     * Resets the high score of all accounts to 0.
     */
    public void resetAllHighScores() {
        List<UserAccount> accounts = loginService.returnAccounts();

        for (UserAccount account : accounts) {
            resetAccountHighScore(account.getId());
        }
    }

    /**
     * Resets one account's high score to 0 in its statistics file.
     *
     * @param accountId the account id
     */
    private void resetAccountHighScore(String accountId) {
        Path path = Paths.get(PLAYER_DATA_FOLDER, accountId + ".json");

        try {
            if (!Files.exists(path)) {
                return;
            }

            String json = Files.readString(path);

            JsonObject rootObject = JsonParser.parseString(json).getAsJsonObject();
            JsonObject statisticsObject = rootObject.getAsJsonObject("statistics");

            if (statisticsObject == null) {
                return;
            }

            statisticsObject.addProperty("highScore", 0);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Files.writeString(path, gson.toJson(rootObject));

        } catch (IOException e) {
            throw new RuntimeException("Failed to reset high score for account id: " + accountId, e);
        }
    }
}