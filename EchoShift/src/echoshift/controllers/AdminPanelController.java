package echoshift.controllers;

import echoshift.UI.*;
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
    public AdminPanelController(Stage stage,AdminPanelView view) {
        this.stage = stage;
        this.view = view;
        this.highScoreManagementService = new HighScoreManagementService();
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
        Scene manageAccountsScene = new Scene(manageAccountsView.createManageAccountsPage(), 1000, 700);

        new ManageAccountsController(stage, stage.getScene(), manageAccountsView);

        stage.setScene(manageAccountsScene);
        stage.setTitle("Echo Shift - Manage Accounts");
        stage.show();
    }
    private void goToCreateAccounts(){

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
    private void goToSettings(){
        SettingsView settingsView = new SettingsView();
        Scene settingsScene = new Scene(settingsView.createSettingsPage(), 1000, 700);

        new SettingsController(stage, stage.getScene(), settingsView);
        stage.setScene(settingsScene);
        stage.setTitle("Echo Shift - Settings");
    }

    private void exitGame(){
        stage.close();
    }
    private void logOut(){
       MainMenuView mainMenuView= new MainMenuView();
        Scene mainMenuScene = new Scene(mainMenuView.createMainMenu(), 1000, 700);

        new MainMenuController(stage, mainMenuView);
        stage.setScene(mainMenuScene);
        stage.setTitle("Echo Shift");
    }
}
