package echoshift.controllers;

import echoshift.UI.AdminPanelView;
import echoshift.UI.CreateAccountView;
import echoshift.models.UserAccount;
import echoshift.services.AccountCreationService;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the create account screen.
 * Handles user input, account creation requests, and navigation
 * back to the admin panel.
 *
 * @author Tudor Mihai Pristav
 */
public class CreateAccountController {

    private final Stage stage;
    private final CreateAccountView view;
    private final AccountCreationService accountCreationService;

    /**
     * Initializes the controller with the required view and service,
     * then attaches all button event handlers for the screen.
     *
     * @param stage the main application stage used for screen navigation
     * @param view the create account view
     */
    public CreateAccountController(Stage stage, CreateAccountView view) {
        this.stage = stage;
        this.view = view;
        this.accountCreationService = new AccountCreationService();

        attachHandlers();
    }

    /**
     * Attaches handlers for account creation and navigation buttons.
     * This connects the UI controls to their corresponding actions.
     */
    private void attachHandlers() {
        view.getBackButton().setOnAction(e -> goBackToAdminPanel());
        view.getCreateAccountButton().setOnAction(e -> handleCreateAccount());
    }

    /**
     * Reads the entered username and password, then attempts to create
     * a new player account using the account creation service.
     * Displays either a success message or an error alert.
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
     * Returns the user to the admin panel screen
     * and recreates its controller.
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
     * Shows an error alert with the provided message
     * when account creation fails or validation is not met.
     *
     * @param message the message to display in the error alert
     */
    private void showError(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }
}