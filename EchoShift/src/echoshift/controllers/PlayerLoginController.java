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

public class PlayerLoginController {
    private final Stage stage;
    private final PlayerLoginView view;

    private final LoginService loginService;
    private final UserDataRetrievalService dataService;

    public PlayerLoginController(Stage stage, PlayerLoginView view) {
        this.stage = stage;
        this.view = view;

        this.loginService = new LoginService();
        this.dataService = new UserDataRetrievalService();
        attachHandlers();
    }

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

        //  Load stats AFTER login
        UserStatistics stats = dataService.retrieveStatistics(account.getId());
        PowerupStorageService powerupStorageService = new PowerupStorageService();
        PlayerPowerups powerups = powerupStorageService.loadPowerups(account.getId());

        Session session = new Session(account, stats, powerups);


        //  Navigate
        goToPlayerHome(session);
    }

    private void goToPlayerHome(Session session) {

        // create next page
        PlayerHomeView homeView = new PlayerHomeView(session);

        stage.getScene().setRoot(homeView.createPlayerHomePage());
        stage.setTitle("Echo Shift - Player Home");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        // attach controller
        new PlayerHomeController(stage, homeView, session);

        // switch scene
    }

    private void goToMenu() {
        MainMenuView mainMenuView = new MainMenuView();
        MainMenuController mainMenuController = new MainMenuController(stage,mainMenuView);
        stage.getScene().setRoot(mainMenuView.createMainMenu());
        stage.setTitle("Echo Shift");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
