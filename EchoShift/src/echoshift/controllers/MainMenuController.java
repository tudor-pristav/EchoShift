package echoshift.controllers;

import echoshift.UI.AdminLoginView;
import echoshift.UI.HighScoreView;
import echoshift.UI.InstructionsView;
import echoshift.UI.MainMenuView;
import echoshift.UI.PlayerLoginView;
import echoshift.UI.SettingsView;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * Controller for the main menu screen.
 * Handles button actions and navigation between different views.
 *
 * @author Tudor Mihai Pristav
 */
public class MainMenuController {

    private final Stage stage;
    private final MainMenuView view;

    /**
     * Creates the controller and initializes event handlers.
     *
     * @param stage the primary stage
     * @param view the main menu view
     */
    public MainMenuController(Stage stage, MainMenuView view) {
        this.stage = stage;
        this.view = view;
        attachHandlers();
    }

    /**
     * Attaches button event handlers to the view.
     */
    private void attachHandlers() {
        view.getLoginButton().setOnAction(e -> goToLogin());
        view.getInstructionsButton().setOnAction(e -> goToInstructions());
        view.getHighScoresButton().setOnAction(e -> goToHighScores());
        view.getAdminLoginButton().setOnAction(e -> goToAdminLogin());
        view.getSettingsButton().setOnAction(e -> goToSettings());
        view.getExitButton().setOnAction(e -> exitGame());
    }

    /**
     * Navigates to the player login screen.
     */
    private void goToLogin() {
        PlayerLoginView loginView = new PlayerLoginView();
        new PlayerLoginController(stage, loginView);
        stage.getScene().setRoot(loginView.createPlayerLoginPage());
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        stage.setTitle("Echo Shift - Login");
    }

    /**
     * Navigates to the instructions screen.
     */
    private void goToInstructions() {
        InstructionsView instructionsView = new InstructionsView(stage);
        stage.getScene().setRoot(instructionsView.createPage());
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        stage.setTitle("Echo Shift - Instructions");
    }

    /**
     * Navigates to the high scores screen.
     */
    private void goToHighScores() {
        HighScoreView highScoreView = new HighScoreView();
        new HighScoreController(stage, highScoreView);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
    }

    /**
     * Navigates to the admin login screen.
     */
    private void goToAdminLogin() {
        AdminLoginView adminLoginView = new AdminLoginView();
        new AdminLoginController(stage, adminLoginView);
        stage.getScene().setRoot(adminLoginView.createPlayerLoginPage());
        stage.setTitle("Echo Shift - Admin Login");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
    }

    /**
     * Navigates to the settings screen.
     */
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

    /**
     * Closes the application.
     */
    private void exitGame() {
        stage.close();
    }
}