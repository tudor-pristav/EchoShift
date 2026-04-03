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

public class AdminPanelController {
    private final Stage stage;
    private final AdminPanelView view;

    public AdminPanelController(Stage stage,AdminPanelView view) {
        this.stage = stage;
        this.view = view;

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

    }
    private void goToCreateAccounts(){

    }
    private void goToResetHighScores(){

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
