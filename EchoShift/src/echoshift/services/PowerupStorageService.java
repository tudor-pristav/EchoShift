package echoshift.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import echoshift.models.PlayerPowerups;
import echoshift.models.PowerupType;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handles creation, loading, and updating of player powerup files.
 */
public class PowerupStorageService {

    private static final String POWERUP_FOLDER = "data/powerups";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Builds the path to a player's powerup file.
     *
     * @param playerId the player id
     * @return the file path
     */
    private Path buildPath(String playerId) {
        return Paths.get(POWERUP_FOLDER, playerId + "-powerup.json");
    }

    /**
     * Ensures the powerup folder exists.
     *
     * @throws IOException if the folder cannot be created
     */
    private void ensureFolderExists() throws IOException {
        Files.createDirectories(Paths.get(POWERUP_FOLDER));
    }

    /**
     * Creates a default powerup file for a player if it does not already exist.
     *
     * @param playerId the player id
     * @throws IOException if writing fails
     */
    public void createDefaultPowerupFile(String playerId) throws IOException {
        ensureFolderExists();

        Path path = buildPath(playerId);

        if (Files.exists(path)) {
            return;
        }

        PlayerPowerups powerups = new PlayerPowerups(0,0,0);

        try (Writer writer = Files.newBufferedWriter(path)) {
            gson.toJson(powerups, writer);
        }
    }

    /**
     * Loads the powerup data for a player.
     *
     * @param playerId the player id
     * @return the loaded powerups
     * @throws IOException if reading fails
     */
    public PlayerPowerups loadPowerups(String playerId) throws IOException {
        Path path = buildPath(playerId);

        if (!Files.exists(path)) {
            createDefaultPowerupFile(playerId);
        }

        try (Reader reader = Files.newBufferedReader(path)) {
            PlayerPowerups powerups = gson.fromJson(reader, PlayerPowerups.class);

            if (powerups == null) {
                powerups = new PlayerPowerups(0,0,0);
            }

            return powerups;
        }
    }

    /**
     * Saves the player's powerups.
     *
     * @param playerId the player id
     * @param powerups the powerups to save
     * @throws IOException if writing fails
     */
    public void savePowerups(String playerId, PlayerPowerups powerups) throws IOException {
        ensureFolderExists();

        Path path = buildPath(playerId);

        try (Writer writer = Files.newBufferedWriter(path)) {
            gson.toJson(powerups, writer);
        }
    }

    /**
     * Adds one purchased powerup to the player's inventory.
     *
     * @param playerId the player id
     * @param powerupType the bought powerup
     * @throws IOException if file access fails
     */
    public void addPowerup(String playerId, PowerupType powerupType) throws IOException {
        PlayerPowerups powerups = loadPowerups(playerId);

        switch (powerupType) {
            case EASY_WORDS:
                powerups.setEasyWords(powerups.getEasyWords() + 1);
                break;
            case EXTRA_LIFE:
                powerups.setExtraLife(powerups.getExtraLife() + 1);
                break;
            case INSTANT_LURE:
                powerups.setInstantLure(powerups.getInstantLure() + 1);
                break;
        }

        savePowerups(playerId, powerups);
    }

    /**
     * Uses one powerup if the player owns at least one.
     *
     * @param playerId the player id
     * @param powerupType the powerup to use
     * @return true if one was used, false otherwise
     * @throws IOException if file access fails
     */
    public boolean usePowerup(String playerId, PowerupType powerupType) throws IOException {
        PlayerPowerups powerups = loadPowerups(playerId);
        boolean used = false;

        switch (powerupType) {
            case EASY_WORDS:
                if (powerups.getEasyWords() > 0) {
                    powerups.setEasyWords(powerups.getEasyWords() - 1);
                    used = true;
                }
                break;
            case EXTRA_LIFE:
                if (powerups.getExtraLife() > 0) {
                    powerups.setExtraLife(powerups.getExtraLife() - 1);
                    used = true;
                }
                break;
            case INSTANT_LURE:
                if (powerups.getInstantLure() > 0) {
                    powerups.setInstantLure(powerups.getInstantLure() - 1);
                    used = true;
                }
                break;
        }

        if (used) {
            savePowerups(playerId, powerups);
        }

        return used;
    }

    /**
     * Returns how many of a given powerup the player owns.
     *
     * @param playerId the player id
     * @param powerupType the powerup type
     * @return the owned amount
     * @throws IOException if file access fails
     */
    public int getPowerupCount(String playerId, PowerupType powerupType) throws IOException {
        PlayerPowerups powerups = loadPowerups(playerId);

        switch (powerupType) {
            case EASY_WORDS:
                return powerups.getEasyWords();
            case EXTRA_LIFE:
                return powerups.getExtraLife();
            case INSTANT_LURE:
                return powerups.getInstantLure();
            default:
                return 0;
        }
    }
}