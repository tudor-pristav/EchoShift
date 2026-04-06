package echoshift.controllers;

import echoshift.UI.ManageAccountsView;
import echoshift.UI.PlayerStatsView;
import echoshift.models.UserAccount;
import echoshift.services.LoginService;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.util.List;

/**
 * Controller for the Manage Accounts screen.
 * Handles loading players, selection, and navigation.
 *
 * @author Tudor Mihai Pristav
 */
public class ManageAccountsController {

    private final Stage stage;
    private final Parent previousRoot;
    private final ManageAccountsView view;
    private final LoginService loginService;
    private final Parent currentRoot;

    /**
     * Creates the controller and initializes the page.
     *
     * @param stage the main stage
     * @param previousRoot the previous scene root
     * @param view the manage accounts view
     */
    public ManageAccountsController(Stage stage, Parent previousRoot, ManageAccountsView view) {
        this.stage = stage;
        this.previousRoot = previousRoot;
        this.view = view;
        this.loginService = new LoginService();
        this.currentRoot = stage.getScene().getRoot();

        loadPlayers();
        attachHandlers();
    }

    /**
     * Loads all player accounts and displays them in the view.
     */
    private void loadPlayers() {
        List<UserAccount> allAccounts = loginService.returnAccounts();
        view.setPlayerList(allAccounts, this::handlePlayerSelected);
    }

    /**
     * Attaches button handlers.
     */
    private void attachHandlers() {
        view.getBackButton().setOnAction(e -> goBack());
    }

    /**
     * Opens the selected player's statistics page.
     *
     * @param account the selected account
     */
    private void handlePlayerSelected(UserAccount account) {
        PlayerStatsView playerStatsView = new PlayerStatsView();
        stage.getScene().setRoot(playerStatsView.createPlayerStatsPage());
        stage.setTitle("Echo Shift - Player Statistics");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);

        new PlayerStatsController(
                stage,
                currentRoot,
                previousRoot,
                playerStatsView,
                account.getId(),
                account.getUsername()
        );
    }

    /**
     * Returns to the previous screen.
     */
    private void goBack() {
        stage.getScene().setRoot(previousRoot);
        stage.setTitle("Echo Shift - Admin Panel");
        stage.show();
    }
}