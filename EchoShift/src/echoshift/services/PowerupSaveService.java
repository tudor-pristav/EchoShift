package echoshift.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import echoshift.models.PlayerPowerups;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Service responsible for saving player powerups to JSON files.
 * @author Tudor Mihai Pristav
 */
public class PowerupSaveService {

    private static final String BASE_PATH = "data/powerups";

    /**
     * Builds the path to the player's powerup file.
     *
     * @param playerId the player ID
     * @return the full file path
     */
    private String pathConstructor(String playerId) {
        return BASE_PATH + "/" + playerId + "-powerup.json";
    }

    /**
     * Saves the given powerups object to the player's JSON file.
     *
     * @param playerId the player ID
     * @param powerups the powerups to save
     * @throws IOException if the file cannot be written
     */
    public void savePowerups(String playerId, PlayerPowerups powerups) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String path = pathConstructor(playerId);

        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(powerups, writer);
        }
    }
}