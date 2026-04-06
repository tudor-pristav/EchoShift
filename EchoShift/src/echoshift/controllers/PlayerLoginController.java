package echoshift.controllers;

import echoshift.UI.MainMenuView;
import echoshift.UI.PlayerLoginView;
import echoshift.models.PlayerPowerups;
import echoshift.models.Session;
import echoshift.models.UserAccount;
import echoshift.models.UserStatistics;
import echoshift.services.LoginService;
import echoshift.services.PowerupStorageService;
import echoshift.services.UserDataRetrievalService;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import echoshift.UI.PlayerHomeView;

import java.io.IOException;

/**
 * Controller responsible for handling player login
 * and navigation from the login screen.
 *
 * @author Tudor Mihai Pristav
 */
public class PlayerLoginController {
    private final Stage stage;
    private final PlayerLoginView view;

    private final LoginService loginService;
    private final UserDataRetrievalService dataService;

    /**
     * Constructs the controller and attaches event handlers.
     *
     * @param stage the main application stage
     * @param view the player login view
     */
    public PlayerLoginController(Stage stage, PlayerLoginView view) {
        this.stage = stage;
        this.view = view;

        this.loginService = new LoginService();
        this.dataService = new UserDataRetrievalService();
        attachHandlers();
    }

    /**
     * Attaches UI event handlers.
     */
    private void attachHandlers() {
        view.getLoginButton().setOnAction(e -> {
            try {
                handleLogin();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        view.getMenuButton().setOnAction(e -> goToMenu());
    }

    /**
     * Validates credentials, loads player data,
     * and creates the session on successful login.
     *
     * @throws IOException if player data cannot be loaded
     */
    private void handleLogin() throws IOException {

        String username = view.getUsernameField().getText().trim();
        String password = view.getPasswordField().getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter both username and password.");
            return;
        }

        UserAccount account = loginService.login(username, password);

        if (account == null) {
            showError("Invalid username or password.");
            return;
        }

        UserStatistics stats = dataService.retrieveStatistics(account.getId());
        PowerupStorageService powerupStorageService = new PowerupStorageService();
        PlayerPowerups powerups = powerupStorageService.loadPowerups(account.getId());

        Session session = new Session(account, stats, powerups);

        goToPlayerHome(session);
    }

    /**
     * Navigates to the player home screen.
     *
     * @param session the current player session
     */
    private void goToPlayerHome(Session session) {

        PlayerHomeView homeView = new PlayerHomeView(session);

        stage.getScene().setRoot(homeView.createPlayerHomePage());
        stage.setTitle("Echo Shift - Player Home");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);

        new PlayerHomeController(stage, homeView, session);
    }

    /**
     * Navigates back to the main menu.
     */
    private void goToMenu() {
        MainMenuView mainMenuView = new MainMenuView();
        MainMenuController mainMenuController = new MainMenuController(stage, mainMenuView);
        stage.getScene().setRoot(mainMenuView.createMainMenu());
        stage.setTitle("Echo Shift");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
    }

    /**
     * Displays a login error alert.
     *
     * @param message the error message
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays an information alert.
     *
     * @param message the information message
     */
    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}