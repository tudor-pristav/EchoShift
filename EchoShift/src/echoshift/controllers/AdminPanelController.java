package echoshift.controllers;

import echoshift.UI.*;
import javafx.scene.Parent;
import javafx.stage.Stage;
import echoshift.UI.AdminLoginView;
import echoshift.models.Session;
import echoshift.models.UserAccount;
import echoshift.models.UserStatistics;
import echoshift.services.AdminLoginService;
import echoshift.services.UserDataRetrievalService;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import echoshift.services.HighScoreManagementService;
import javafx.scene.control.ButtonType;

public class AdminPanelController {
    private final Stage stage;
    private final AdminPanelView view;
    private final HighScoreManagementService highScoreManagementService;
    private final Parent previousRoot;
    public AdminPanelController(Stage stage,AdminPanelView view) {
        this.stage = stage;
        this.view = view;
        this.highScoreManagementService = new HighScoreManagementService();
        this.previousRoot =stage.getScene().getRoot();
        attachHandlers();
    }
    private void attachHandlers() {
        view.getLoginButton().setOnAction(e -> goToManageAccounts());
        view.getInstructionsButton().setOnAction(e -> goToCreateAccounts());
        view.getHighScoresButton().setOnAction(e -> goToResetHighScores());
        view.getSettingsButton().setOnAction(e -> goToSettings());
        view.getExitButton().setOnAction(e -> exitGame());
        view.getLogoutButton().setOnAction(actionEvent -> logOut());
    }

    private void goToManageAccounts(){
        ManageAccountsView manageAccountsView = new ManageAccountsView();

        stage.getScene().setRoot(manageAccountsView.createManageAccountsPage());
        stage.setTitle("Echo Shift - Manage Accounts");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);

        new ManageAccountsController(stage, previousRoot, manageAccountsView);

    }
    private void goToCreateAccounts(){
        CreateAccountView createAccountView = new CreateAccountView();
        stage.getScene().setRoot(createAccountView.createCreateAccountPage());
        stage.setTitle("Echo Shift - Create Account");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        new CreateAccountController(stage, createAccountView);
    }

    private void goToResetHighScores() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Reset High Scores");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to reset all player high scores?");

        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    highScoreManagementService.resetAllHighScores();

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Success");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("All player high scores were reset successfully.");
                    successAlert.showAndWait();

                } catch (RuntimeException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Failed to reset high scores.");
                    errorAlert.showAndWait();
                }
            }
        });
    }
    private void goToSettings() {
        SettingsView settingsView = new SettingsView();
        stage.getScene().setRoot(settingsView.createSettingsPage());
        stage.setTitle("Echo Shift - Settings");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);

        new SettingsController(stage, previousRoot, settingsView);
    }

    private void exitGame(){
        stage.close();
    }

    private void logOut(){
       MainMenuView mainMenuView= new MainMenuView();
        stage.getScene().setRoot(mainMenuView.createMainMenu());
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);

        new MainMenuController(stage, mainMenuView);
        stage.setTitle("Echo Shift");
    }
}
