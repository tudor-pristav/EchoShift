package echoshift.controllers;

import echoshift.UI.ManageAccountsView;
import echoshift.UI.PlayerStatsView;
import echoshift.models.UserAccount;
import echoshift.services.LoginService;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the Manage Accounts screen.
 * Loads all player accounts, displays their names,
 * and handles navigation/actions for account selection.
 *
 * @author Tudor Pristav
 * @version 1.0.0
 */
public class ManageAccountsController {

    private final Stage stage;
    private final Scene previousScene;
    private final ManageAccountsView view;
    private final LoginService loginService;

    /**
     * Creates the controller and initializes the page.
     *
     * @param stage the main application stage
     * @param previousScene the previous scene to return to
     * @param view the manage accounts view
     */
    public ManageAccountsController(Stage stage, Scene previousScene, ManageAccountsView view) {
        this.stage = stage;
        this.previousScene = previousScene;
        this.view = view;
        this.loginService = new LoginService();

        loadPlayers();
        attachHandlers();
    }

    /**
     * Loads all accounts, keeps only player accounts,
     * and sends them to the view.
     */
    private void loadPlayers() {
        List<UserAccount> allAccounts = loginService.returnAccounts();
        view.setPlayerList(allAccounts, this::handlePlayerSelected);
    }


    /**
     * Attaches static button handlers.
     */
    private void attachHandlers() {
        view.getBackButton().setOnAction(e -> goBack());
    }

    /**
     * Handles selecting a player from the list
     * and opens that player's stats page.
     *
     * @param account the selected player account
     */
    private void handlePlayerSelected(UserAccount account) {
        PlayerStatsView playerStatsView = new PlayerStatsView();
        Scene statsScene = new Scene(playerStatsView.createPlayerStatsPage(), 1000, 800);

        new PlayerStatsController(
                stage,
                stage.getScene(),   // ManageAccounts scene
                previousScene,      // AdminPanel scene
                playerStatsView,
                account.getId(),
                account.getUsername()
        );

        stage.setScene(statsScene);
        stage.setTitle("Echo Shift - Player Stats");
        stage.show();
    }

    /**
     * Returns to the previous scene.
     */
    private void goBack() {
        stage.setScene(previousScene);
        stage.setTitle("Echo Shift - Admin Panel");
        stage.show();
    }
}