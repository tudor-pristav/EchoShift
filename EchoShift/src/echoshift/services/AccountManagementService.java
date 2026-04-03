package echoshift.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import echoshift.models.UserAccount;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for account management operations such as deleting accounts.
 *
 * This service removes a player account from accounts.json
 * and deletes the corresponding player data file.
 *
 * Only accounts with role "player" can be deleted.
 *
 * @author Tudor Pristav
 * @version 1.0.0
 */
public class AccountManagementService {

    private static final String ACCOUNTS_PATH = "data/accounts.json";
    private static final String PLAYER_DATA_FOLDER = "data/playerData";

    /**
     * Deletes the account with the given id if it exists and has role "player".
     *
     * @param playerId the id of the account to delete
     * @return true if the account was deleted, false if not found
     * @throws IllegalArgumentException if the account is not a player account
     */
    public boolean deleteAccount(String playerId) {
        try {
            List<UserAccount> accounts = loadAccounts();
            UserAccount targetAccount = findAccountById(accounts, playerId);

            if (targetAccount == null) {
                return false;
            }

            if (!isPlayerAccount(targetAccount)) {
                throw new IllegalArgumentException("Only player accounts can be deleted.");
            }

            removeAccountFromAccountsFile(playerId);
            deletePlayerDataFile(playerId);

            return true;

        } catch (IOException e) {
            throw new RuntimeException("Failed to delete account with id: " + playerId, e);
        }
    }

    /**
     * Loads all accounts from accounts.json.
     *
     * @return list of user accounts
     * @throws IOException if the file cannot be read
     */
    private List<UserAccount> loadAccounts() throws IOException {
        List<UserAccount> accounts = new ArrayList<>();

        String json = Files.readString(Paths.get(ACCOUNTS_PATH));
        JsonObject rootObject = JsonParser.parseString(json).getAsJsonObject();
        JsonArray accountsArray = rootObject.getAsJsonArray("accounts");

        Gson gson = new Gson();

        for (int i = 0; i < accountsArray.size(); i++) {
            accounts.add(gson.fromJson(accountsArray.get(i), UserAccount.class));
        }

        return accounts;
    }

    /**
     * Finds an account by id.
     *
     * @param accounts the list of accounts
     * @param playerId the id to search for
     * @return the matching account, or null if not found
     */
    private UserAccount findAccountById(List<UserAccount> accounts, String playerId) {
        for (UserAccount account : accounts) {
            if (account.getId().equals(playerId)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Checks whether the account is a player account.
     *
     * @param account the account to check
     * @return true if role is "player"
     */
    private boolean isPlayerAccount(UserAccount account) {
        return account.getRole() != null && account.getRole().equalsIgnoreCase("player");
    }

    /**
     * Removes the account entry from accounts.json.
     *
     * @param playerId the id of the account to remove
     * @throws IOException if the file cannot be read or written
     */
    private void removeAccountFromAccountsFile(String playerId) throws IOException {
        Path path = Paths.get(ACCOUNTS_PATH);
        String json = Files.readString(path);

        JsonObject rootObject = JsonParser.parseString(json).getAsJsonObject();
        JsonArray accountsArray = rootObject.getAsJsonArray("accounts");

        JsonArray updatedAccountsArray = new JsonArray();

        for (int i = 0; i < accountsArray.size(); i++) {
            JsonObject accountObject = accountsArray.get(i).getAsJsonObject();
            String id = accountObject.get("id").getAsString();

            if (!id.equals(playerId)) {
                updatedAccountsArray.add(accountObject);
            }
        }

        rootObject.add("accounts", updatedAccountsArray);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Files.writeString(path, gson.toJson(rootObject));
    }

    /**
     * Deletes the corresponding player data file if it exists.
     *
     * @param playerId the id of the player
     * @throws IOException if deletion fails
     */
    private void deletePlayerDataFile(String playerId) throws IOException {
        Path playerDataPath = Paths.get(PLAYER_DATA_FOLDER, playerId + ".json");
        Files.deleteIfExists(playerDataPath);
    }
}