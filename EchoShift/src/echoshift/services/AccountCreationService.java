package echoshift.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import echoshift.models.AccountsFile;
import echoshift.models.UserStatistics;
import echoshift.models.UserAccount;
import echoshift.models.PlayerStatisticsFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

/**
 * Service responsible for creating new player accounts and default player statistics files.
 */
public class AccountCreationService {

    private static final String ACCOUNTS_PATH = "data/accounts.json";
    private static final String PLAYER_DATA_FOLDER = "data/playerData";
    private static final String ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ID_LENGTH = 10;

    private final Gson gson;
    private final Random random;

    public AccountCreationService() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.random = new Random();
    }

    /**
     * Creates a new player account and corresponding default statistics file.
     *
     * @param username the entered username
     * @param password the entered password
     * @return the newly created account
     * @throws IOException if file reading or writing fails
     * @throws IllegalArgumentException if the input is invalid
     */
    public UserAccount createAccount(String username, String password) throws IOException {
        String cleanUsername = username == null ? "" : username.trim();
        String cleanPassword = password == null ? "" : password.trim();

        validateInputs(cleanUsername, cleanPassword);

        AccountsFile accountsFile = loadAccountsFile();
        List<UserAccount> accounts = accountsFile.getAccounts();

        if (usernameExists(cleanUsername, accounts)) {
            throw new IllegalArgumentException("Username already exists.");
        }

        String uniqueId = generateUniqueId(accounts);

        UserAccount newAccount = new UserAccount(uniqueId, cleanUsername, cleanPassword, "player");
        accounts.add(newAccount);

        saveAccountsFile(accountsFile);
        createDefaultStatisticsFile(uniqueId);
        createDefaultPowerupFile(uniqueId);
        return newAccount;
    }

    /**
     * Validates username and password input.
     *
     * @param username the username
     * @param password the password
     */
    private void validateInputs(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password must not be empty.");
        }
    }

    /**
     * Loads the full accounts.json wrapper object.
     *
     * @return the accounts file object
     * @throws IOException if reading fails
     */
    private AccountsFile loadAccountsFile() throws IOException {
        Path path = Paths.get(ACCOUNTS_PATH);

        if (!Files.exists(path)) {
            return new AccountsFile();
        }

        String json = Files.readString(path).trim();

        if (json.isEmpty()) {
            return new AccountsFile();
        }

        AccountsFile accountsFile = gson.fromJson(json, AccountsFile.class);
        return accountsFile != null ? accountsFile : new AccountsFile();
    }

    /**
     * Saves the accounts wrapper object back to accounts.json.
     *
     * @param accountsFile the wrapper object to save
     * @throws IOException if writing fails
     */
    private void saveAccountsFile(AccountsFile accountsFile) throws IOException {
        Path path = Paths.get(ACCOUNTS_PATH);
        Path parent = path.getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        String json = gson.toJson(accountsFile);
        Files.writeString(path, json);
    }

    /**
     * Returns true if the username already exists.
     *
     * @param username the username to check
     * @param accounts the list of existing accounts
     * @return true if duplicate
     */
    private boolean usernameExists(String username, List<UserAccount> accounts) {
        for (UserAccount account : accounts) {
            if (account.getUsername() != null &&
                    account.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates a unique random ID of length 10.
     *
     * @param accounts the current accounts list
     * @return a unique ID
     */
    private String generateUniqueId(List<UserAccount> accounts) {
        String id;

        do {
            id = generateRandomId();
        } while (idExists(id, accounts));

        return id;
    }

    /**
     * Generates a random 10-character alphanumeric ID.
     *
     * @return random ID
     */
    private String generateRandomId() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < ID_LENGTH; i++) {
            int index = random.nextInt(ALLOWED_CHARS.length());
            builder.append(ALLOWED_CHARS.charAt(index));
        }

        return builder.toString();
    }

    /**
     * Returns true if the given ID already exists.
     *
     * @param id the candidate ID
     * @param accounts the current accounts list
     * @return true if found
     */
    private boolean idExists(String id, List<UserAccount> accounts) {
        for (UserAccount account : accounts) {
            if (account.getId() != null && account.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a default statistics file for the new player.
     *
     * @param playerId the player ID
     * @throws IOException if writing fails
     */
    private void createDefaultStatisticsFile(String playerId) throws IOException {
        Path folderPath = Paths.get(PLAYER_DATA_FOLDER);
        Files.createDirectories(folderPath);

        Path filePath = folderPath.resolve(playerId + ".json");

        PlayerStatisticsFile statisticsFile =
                new PlayerStatisticsFile(new UserStatistics());

        String json = gson.toJson(statisticsFile);
        Files.writeString(filePath, json);
    }
    /**
     * Creates a default powerup file for the new player.
     *
     * @param playerId the player ID
     * @throws IOException if writing fails
     */
    private void createDefaultPowerupFile(String playerId) throws IOException {
        Path folderPath = Paths.get("data/powerups");
        Files.createDirectories(folderPath);

        Path filePath = folderPath.resolve(playerId + "-powerup.json");

        // reuse your model
        echoshift.models.PlayerPowerups powerups = new echoshift.models.PlayerPowerups(0,0,0);

        String json = gson.toJson(powerups);
        Files.writeString(filePath, json);
    }
}