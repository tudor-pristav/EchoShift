package echoshift.controllers;

import echoshift.UI.AdminPanelView;
import echoshift.UI.CreateAccountView;
import echoshift.models.UserAccount;
import echoshift.services.AccountCreationService;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the Create Account page.
 * Handles navigation and account creation actions for this screen.
 */
public class CreateAccountController {

    private final Stage stage;
    private final CreateAccountView view;
    private final AccountCreationService accountCreationService;

    /**
     * Creates the controller and attaches event handlers.
     *
     * @param stage the main application stage
     * @param view the create account view
     */
    public CreateAccountController(Stage stage, CreateAccountView view) {
        this.stage = stage;
        this.view = view;
        this.accountCreationService = new AccountCreationService();

        attachHandlers();
    }

    /**
     * Attaches button event handlers.
     */
    private void attachHandlers() {
        view.getBackButton().setOnAction(e -> goBackToAdminPanel());
        view.getCreateAccountButton().setOnAction(e -> handleCreateAccount());
    }

    /**
     * Handles create account button logic.
     */
    private void handleCreateAccount() {
        String username = view.getUsernameField().getText();
        String password = view.getPasswordField().getText();

        try {
            UserAccount createdAccount = accountCreationService.createAccount(username, password);

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Account created successfully.\nID: " + createdAccount.getId());
            successAlert.showAndWait();

            view.getUsernameField().clear();
            view.getPasswordField().clear();

        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            showError("Failed to create account.");
        }
    }

    /**
     * Navigates back to the admin panel page.
     */
    private void goBackToAdminPanel() {
        AdminPanelView adminPanelView = new AdminPanelView();
        stage.getScene().setRoot(adminPanelView.createMainMenu());
        stage.setTitle("Echo Shift - Admin Panel");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        new AdminPanelController(stage, adminPanelView);
    }

    /**
     * Shows an error alert.
     *
     * @param message the error message
     */
    private void showError(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }
}