package echoshift.controllers;

import echoshift.UI.ManageAccountsView;
import echoshift.UI.PlayerHomeStatsView;
import echoshift.UI.PlayerStatsView;
import echoshift.models.UserStatistics;
import echoshift.services.AccountManagementService;
import echoshift.services.UserDataRetrievalService;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Controller responsible for displaying a player's statistics
 * and handling navigation back to the Manage Accounts screen.
 *
 * @author Tudor Mihai Pristav
 */
public class PlayerHomeStatsController {

    private final Stage stage;
    private final Parent manageAccountsRoot;   // where "Back" goes
    private final PlayerHomeStatsView view;
    private final String playerId;
    private final String username;

    private final AccountManagementService accountManagementService;
    private final UserDataRetrievalService dataRetrievalService;

    /**
     * Constructs the controller and initializes data and handlers.
     *
     * @param stage the main application stage
     * @param manageAccountsRoot the root node of the manage accounts screen
     * @param view the player stats view
     * @param playerId the player's unique ID
     * @param username the player's username
     */
    public PlayerHomeStatsController(
            Stage stage,
            Parent manageAccountsRoot,
            PlayerHomeStatsView view,
            String playerId,
            String username
    ) {
        this.stage = stage;
        this.manageAccountsRoot = manageAccountsRoot;
        this.view = view;
        this.playerId = playerId;
        this.username = username;

        this.dataRetrievalService = new UserDataRetrievalService();
        this.accountManagementService = new AccountManagementService();

        loadPlayerData();
        attachHandlers();
    }

    /**
     * Loads player statistics and updates the view.
     */
    private void loadPlayerData() {
        try {
            UserStatistics stats = dataRetrievalService.retrieveStatistics(playerId);

            view.setPlayerName(username);
            view.setStats(
                    stats.getPeakWPM(),
                    stats.getAverageWPM(),
                    stats.getAccuracy(),
                    stats.getErrorCount(),
                    stats.getTotalTimePlayed(),
                    stats.getHighScore(),
                    stats.getHighestLevel(),
                    stats.getWordsTyped(),
                    stats.getCoins()
            );

        } catch (RuntimeException e) {
            showError("Could not load statistics for player: " + username);
        }
    }

    /**
     * Attaches UI event handlers.
     */
    private void attachHandlers() {
        view.getBackButton().setOnAction(e -> goBack());
    }

    /**
     * Navigates back to the Manage Accounts screen.
     */
    private void goBack() {
        stage.getScene().setRoot(manageAccountsRoot);
        stage.setTitle("Echo Shift - Manage Accounts");
        stage.show();
    }

    /**
     * Displays an error alert with the given message.
     *
     * @param message the error message to display
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}