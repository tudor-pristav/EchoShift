package echoshift.services;

import com.google.gson.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import echoshift.models.UserAccount;

/**
 * Service responsible for handling user authentication.
 *
 * <p>This class provides functionality to:
 * <ul>
 *     <li>Load user accounts from a local JSON file</li>
 *     <li>Validate login credentials (username and password)</li>
 * </ul>
 *
 * <p>Accounts are stored locally in a JSON file located at {@code data/accounts.json}.
 * This implementation uses plain-text password comparison as allowed by the project specification.
 */
public class LoginService {

    /**
     * Path to the JSON file containing all registered user accounts.
     */
    private static final String AccountsPath = "data/accounts.json";

    /**
     * Attempts to authenticate a user using the provided username and password.
     *
     * <p>This method loads all accounts from storage and checks for a matching
     * username and password combination.
     *
     * @param username the username entered by the user
     * @param password the password entered by the user
     * @return the matching {@link UserAccount} if authentication is successful;
     * {@code null} if the username is not found or the password is incorrect
     */
    public UserAccount login(String username, String password) {

        List<UserAccount> accounts = loadAccounts();

        for (UserAccount acc : accounts) {
            if (acc.getUsername().equals(username)) {
                if (acc.getPassword().equals(password)) {
                    return acc; // successful login
                }
            }
        }

        return null; // login failed
    }

    /**
     * Loads all user accounts from the JSON file and converts them into
     * a list of {@link UserAccount} objects.
     *
     * <p>The method performs the following steps:
     * <ol>
     *     <li>Reads the JSON file as a string</li>
     *     <li>Parses the string into a JSON object</li>
     *     <li>Extracts the "accounts" array</li>
     *     <li>Converts each JSON element into a {@link UserAccount}</li>
     * </ol>
     *
     * @return a list of all user accounts; returns an empty list if no accounts exist
     * @throws RuntimeException if the file cannot be read
     */
    private List<UserAccount> loadAccounts() {

        List<UserAccount> accounts = new ArrayList<>();

        try {
            // Read entire JSON file as a string
            String json = Files.readString(Paths.get(AccountsPath));

            // Parse string into JSON object
            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

            // Extract "accounts" array
            JsonArray accArr = obj.getAsJsonArray("accounts");

            // Convert each JSON element into a UserAccount object
            for (JsonElement acc : accArr) {
                accounts.add(new Gson().fromJson(acc, UserAccount.class));
            }
            return accounts;

        } catch (IOException e) {
            throw new RuntimeException("Failed to load accounts from file", e);
        }
    }
    public  List<UserAccount> returnAccounts(){
        List<UserAccount> fullAccounts = loadAccounts();
        return fullAccounts;
    }
}