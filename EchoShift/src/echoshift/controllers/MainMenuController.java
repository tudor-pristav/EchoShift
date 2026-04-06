package echoshift.controllers;

import echoshift.UI.*;
import javafx.scene.Parent;
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
        stage.getScene().setRoot(loginView.createPlayerLoginPage());
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        stage.setTitle("Echo Shift - Login");

    }

    private void goToInstructions() {
        InstructionsView instructionsView = new InstructionsView(stage);
        stage.getScene().setRoot(instructionsView.createPage());
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        stage.setTitle("Echo Shift - Instructions");

    }

    private void goToHighScores() {
        HighScoreView highScoreView = new HighScoreView();
        new HighScoreController(stage, highScoreView);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
    }

    private void goToAdminLogin() {
        AdminLoginView adminLoginView = new AdminLoginView();
        new AdminLoginController(stage,adminLoginView);

        stage.getScene().setRoot(adminLoginView.createPlayerLoginPage());
        stage.setTitle("Echo Shift - Admin Login");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);

    }

    private void goToSettings() {
        SettingsView settingsView = new SettingsView();
        Parent previousRoot = stage.getScene().getRoot();
        stage.getScene().setRoot(settingsView.createSettingsPage());
        stage.setTitle("Echo Shift - Settings");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);

        new SettingsController(stage, previousRoot, settingsView);
    }

    private void exitGame() {
        stage.close();
    }
}