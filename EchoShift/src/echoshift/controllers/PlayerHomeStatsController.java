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
 * Controller for the Player Stats screen.
 */
public class PlayerHomeStatsController {

    private final Stage stage;
    private final Parent manageAccountsRoot;   // where "Back" goes
    private final PlayerHomeStatsView view;
    private final String playerId;
    private final String username;

    private final AccountManagementService accountManagementService;
    private final UserDataRetrievalService dataRetrievalService;

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

    private void attachHandlers() {
        view.getBackButton().setOnAction(e -> goBack());
    }

    /**
     * Back → returns to Manage Accounts (no reload)
     */
    private void goBack() {
        stage.getScene().setRoot(manageAccountsRoot);
        stage.setTitle("Echo Shift - Manage Accounts");
        stage.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}