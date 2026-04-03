package echoshift.controllers;

import echoshift.UI.AdminLoginView;
import echoshift.UI.AdminPanelView;
import echoshift.UI.PlayerLoginView;
import echoshift.models.Session;
import echoshift.models.UserAccount;
import echoshift.models.UserStatistics;
import echoshift.services.AdminLoginService;
import echoshift.services.UserDataRetrievalService;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import echoshift.UI.PlayerHomeView;

public class AdminLoginController {
    private final Stage stage;
    private final AdminLoginView view;

    private final AdminLoginService loginService;
    private final UserDataRetrievalService dataService;

    public AdminLoginController(Stage stage, AdminLoginView view) {
        this.stage = stage;
        this.view = view;

        this.loginService = new AdminLoginService();
        this.dataService = new UserDataRetrievalService();
        attachHandlers();
    }

    private void attachHandlers() {
        view.getLoginButton().setOnAction(e -> handleLogin());
        view.getMenuButton().setOnAction(e -> goToMenu());
    }

    private void handleLogin() {

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

        //  Create session
        Session session = new Session(account, stats);

        //  Navigate
        goToPlayerHome(session);
    }

    private void goToPlayerHome(Session session) {

        // create next page
       AdminPanelView panelView = new AdminPanelView();

        // attach controller
        new AdminPanelController(stage, panelView);

        // switch scene
        stage.setScene(new Scene(panelView.createMainMenu(), 1280, 720));
    }

    private void goToMenu() {
        // replace with your real menu navigation later
        showInfo("Return to main menu here.");
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
