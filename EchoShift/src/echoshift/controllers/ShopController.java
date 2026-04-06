package echoshift.controllers;

import echoshift.UI.PlayerHomeView;
import echoshift.UI.ShopView;
import echoshift.models.PowerupType;
import echoshift.models.Session;
import echoshift.models.UserStatistics;
import echoshift.services.PowerupStorageService;
import echoshift.services.UserDataSaveService;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the Shop page.
 * Handles purchases, coin updates, and powerup management.
 *
 * @author Tudor Mihai Pristav
 */
public class ShopController {

    private final Stage stage;
    private final ShopView view;
    private final Session session;
    private final UserDataSaveService saveService;
    private final PowerupStorageService powerupStorageService;

    /**
     * Initializes the controller and sets up UI data and handlers.
     *
     * @param stage main application stage
     * @param view shop view
     * @param session current user session
     */
    public ShopController(Stage stage, ShopView view, Session session) {
        this.stage = stage;
        this.view = view;
        this.session = session;
        this.saveService = new UserDataSaveService();
        this.powerupStorageService = new PowerupStorageService();

        refreshCoinsDisplay();
        refreshPowerupDisplay();
        attachHandlers();
    }

    /**
     * Attaches button event handlers.
     */
    private void attachHandlers() {
        view.getBackButton().setOnAction(e -> goBackToPlayerHome());

        view.getItemOneButton().setOnAction(e -> buyItem("Easy Words", 25, PowerupType.EASY_WORDS));
        view.getItemTwoButton().setOnAction(e -> buyItem("Extra Life", 40, PowerupType.EXTRA_LIFE));
        view.getItemThreeButton().setOnAction(e -> buyItem("Instant Lure", 35, PowerupType.INSTANT_LURE));
    }

    /**
     * Updates the displayed coin amount.
     */
    private void refreshCoinsDisplay() {
        int coins = session.getCurrentStatistics().getCoins();
        view.setCoinsText("$" + coins);
    }

    /**
     * Purchases a powerup if sufficient coins exist.
     *
     * @param itemName name of the item
     * @param cost cost in coins
     * @param powerupType type of powerup
     */
    private void buyItem(String itemName, int cost, PowerupType powerupType) {
        UserStatistics stats = session.getCurrentStatistics();
        int currentCoins = stats.getCoins();

        if (currentCoins < cost) {
            showError("Not enough coins to buy " + itemName + ".");
            return;
        }

        stats.setCoins(currentCoins - cost);

        try {
            String playerId = session.getCurrentUser().getId();

            saveService.saveStatistics(playerId, stats);
            powerupStorageService.addPowerup(playerId, powerupType);

            refreshCoinsDisplay();
            refreshPowerupDisplay();

            showInfo(itemName + " purchased successfully.");
        } catch (IOException e) {
            showError("Could not save updated player data.");
            e.printStackTrace();
        }
    }

    /**
     * Navigates back to the player home page.
     */
    private void goBackToPlayerHome() {
        PlayerHomeView playerHomeView = new PlayerHomeView(session);
        stage.getScene().setRoot(playerHomeView.createPlayerHomePage());
        stage.setTitle("Echo Shift - Player Statistics");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        new PlayerHomeController(stage, playerHomeView, session);
    }

    /**
     * Displays an information alert.
     *
     * @param message message to display
     */
    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Shop");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays an error alert.
     *
     * @param message error message
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Shop Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Updates displayed powerup counts.
     */
    private void refreshPowerupDisplay() {
        try {
            String playerId = session.getCurrentUser().getId();

            int easyWordsCount = powerupStorageService.getPowerupCount(playerId, PowerupType.EASY_WORDS);
            int extraLifeCount = powerupStorageService.getPowerupCount(playerId, PowerupType.EXTRA_LIFE);
            int instantLureCount = powerupStorageService.getPowerupCount(playerId, PowerupType.INSTANT_LURE);

            view.setItemOneCountText("Owned: " + easyWordsCount);
            view.setItemTwoCountText("Owned: " + extraLifeCount);
            view.setItemThreeCountText("Owned: " + instantLureCount);

        } catch (IOException e) {
            showError("Could not load powerup counts.");
            e.printStackTrace();
        }
    }
}