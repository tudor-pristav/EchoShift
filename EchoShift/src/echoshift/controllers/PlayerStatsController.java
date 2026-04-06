package echoshift.controllers;

import echoshift.UI.ManageAccountsView;
import echoshift.UI.PlayerStatsView;
import echoshift.models.UserStatistics;
import echoshift.services.AccountManagementService;
import echoshift.services.UserDataRetrievalService;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Controller responsible for displaying player statistics
 * and handling account actions from the admin side.
 *
 * @author Tudor Mihai Pristav
 */
public class PlayerStatsController {

    private final Stage stage;
    private final Parent manageAccountsRoot;   // where "Back" goes
    private final Parent adminPanelRoot;       // where ManageAccounts "Back" goes
    private final PlayerStatsView view;
    private final String playerId;
    private final String username;

    private final AccountManagementService accountManagementService;
    private final UserDataRetrievalService dataRetrievalService;

    /**
     * Constructs the controller and initializes data and handlers.
     *
     * @param stage the main application stage
     * @param manageAccountsRoot the manage accounts root node
     * @param adminPanelRoot the admin panel root node
     * @param view the player stats view
     * @param playerId the player's unique ID
     * @param username the player's username
     */
    public PlayerStatsController(
            Stage stage,
            Parent manageAccountsRoot,
            Parent adminPanelRoot,
            PlayerStatsView view,
            String playerId,
            String username
    ) {
        this.stage = stage;
        this.manageAccountsRoot = manageAccountsRoot;
        this.adminPanelRoot = adminPanelRoot;
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
        view.getDeleteAccountButton().setOnAction(e -> handleDeleteAccount());
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
     * Reloads the Manage Accounts page after an account is deleted.
     */
    private void reloadManageAccountsPage() {
        ManageAccountsView manageAccountsView = new ManageAccountsView();
        stage.getScene().setRoot(manageAccountsView.createManageAccountsPage());
        stage.setTitle("Echo Shift - Manage Accounts");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);

        new ManageAccountsController(
                stage,
                adminPanelRoot,
                manageAccountsView
        );
        stage.show();
    }

    /**
     * Confirms and deletes the selected player account.
     */
    private void handleDeleteAccount() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Account");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete the account for " + username + "?");

        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                try {
                    boolean deleted = accountManagementService.deleteAccount(playerId);

                    if (deleted) {
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Success");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Account deleted successfully.");
                        successAlert.showAndWait();

                        reloadManageAccountsPage();

                    } else {
                        showError("Account not found. Nothing was deleted.");
                    }

                } catch (IllegalArgumentException e) {
                    showError(e.getMessage());
                } catch (RuntimeException e) {
                    showError("Failed to delete account for " + username);
                }
            }
        });
    }

    /**
     * Displays an error alert with the given message.
     *
     * @param message the error message
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}