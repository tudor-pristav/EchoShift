package echoshift.controllers;

import echoshift.UI.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuController {

    private final Stage stage;
    private final MainMenuView view;

    public MainMenuController(Stage stage, MainMenuView view) {
        this.stage = stage;
        this.view = view;

        attachHandlers();
    }

    private void attachHandlers() {
        view.getLoginButton().setOnAction(e -> goToLogin());
        view.getInstructionsButton().setOnAction(e -> goToInstructions());
        view.getHighScoresButton().setOnAction(e -> goToHighScores());
        view.getAdminLoginButton().setOnAction(e -> goToAdminLogin());
        view.getSettingsButton().setOnAction(e -> goToSettings());
        view.getExitButton().setOnAction(e -> exitGame());
    }

    // --- Navigation methods ---

    private void goToLogin() {
        PlayerLoginView loginView = new PlayerLoginView();
        new PlayerLoginController(stage, loginView);
        stage.setScene(new Scene(loginView.createPlayerLoginPage(), 1280, 720));
        stage.setTitle("Echo Shift - Login");
    }

    private void goToInstructions() {

    }

    private void goToHighScores() {
        HighScoreView highScoreView = new HighScoreView();
        new HighScoreController(stage, highScoreView);
    }

    private void goToAdminLogin() {
        System.out.println("Admin Login clicked");
    }

    private void goToSettings() {
        System.out.println("Settings clicked");
    }

    private void exitGame() {
        stage.close();
    }
}