package echoshift.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import echoshift.models.PlayerStatisticsFile;
import echoshift.models.UserStatistics;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Service responsible for saving player statistics to JSON files.
 */
public class UserDataSaveService {

    private static final String BASE_PATH = "data/playerData";

    /**
     * Builds the path to the player's statistics file.
     *
     * @param playerId the player ID
     * @return the full file path
     */
    private String pathConstructor(String playerId) {
        return BASE_PATH + "/" + playerId + ".json";
    }

    /**
     * Saves the given statistics object to the player's JSON file.
     *
     * @param playerId the player ID
     * @param statistics the statistics to save
     * @throws IOException if the file cannot be written
     */
    public void saveStatistics(String playerId, UserStatistics statistics) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String path = pathConstructor(playerId);

        // wrap it
        PlayerStatisticsFile wrapper = new PlayerStatisticsFile(statistics);

        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(wrapper, writer);
        }
    }
}