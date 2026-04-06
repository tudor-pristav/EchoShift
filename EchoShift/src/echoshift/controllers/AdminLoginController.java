package echoshift.controllers;

import echoshift.UI.*;
import echoshift.models.PlayerPowerups;
import echoshift.models.Session;
import echoshift.models.UserAccount;
import echoshift.models.UserStatistics;
import echoshift.services.AdminLoginService;
import echoshift.services.PowerupStorageService;
import echoshift.services.UserDataRetrievalService;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for handling admin login and navigation.
 *
 * @author Tudor Mihai Pristav
 */
public class AdminLoginController {

    private final Stage stage;
    private final AdminLoginView view;

    private final AdminLoginService loginService;
    private final UserDataRetrievalService dataService;

    /**
     * Initializes the controller and attaches event handlers.
     *
     * @param stage the main application stage
     * @param view the admin login view
     */
    public AdminLoginController(Stage stage, AdminLoginView view) {
        this.stage = stage;
        this.view = view;

        this.loginService = new AdminLoginService();
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
     * Handles login validation and session creation.
     *
     * @throws IOException if data loading fails
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
     * Navigates to the admin panel.
     *
     * @param session the current session
     */
    private void goToPlayerHome(Session session) {
        AdminPanelView panelView = new AdminPanelView();

        stage.getScene().setRoot(panelView.createMainMenu());
        new AdminPanelController(stage, panelView);

        stage.setTitle("Echo Shift - Admin Panel");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
    }

    /**
     * Navigates back to the main menu.
     */
    private void goToMenu() {
        MainMenuView mainMenu = new MainMenuView();
        new MainMenuController(stage, mainMenu);

        stage.getScene().setRoot(mainMenu.createMainMenu());
        stage.setTitle("Echo Shift");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
    }

    /**
     * Displays an error alert.
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
     * Displays an informational alert.
     *
     * @param message the info message
     */
    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}