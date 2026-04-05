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
 * Handles coin display and purchasing power-ups.
 */
public class ShopController {

    private final Stage stage;
    private final ShopView view;
    private final Session session;
    private final UserDataSaveService saveService;
    private final PowerupStorageService powerupStorageService;

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
     * Attaches button handlers.
     */
    private void attachHandlers() {
        view.getBackButton().setOnAction(e -> goBackToPlayerHome());

        view.getItemOneButton().setOnAction(e -> buyItem("Easy Words", 25, PowerupType.EASY_WORDS));
        view.getItemTwoButton().setOnAction(e -> buyItem("Extra Life", 40, PowerupType.EXTRA_LIFE));
        view.getItemThreeButton().setOnAction(e -> buyItem("Instant Lure", 35, PowerupType.INSTANT_LURE));
    }

    /**
     * Updates the coins label from the current session stats.
     */
    private void refreshCoinsDisplay() {
        int coins = session.getCurrentStatistics().getCoins();
        view.setCoinsText("$" + coins);
    }

    /**
     * Attempts to buy an item, deducts coins, saves updated stats,
     * and adds the purchased powerup to the player's powerup file.
     *
     * @param itemName the item name
     * @param cost the item cost
     * @param powerupType the powerup type to add
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
     * Returns to the player home page.
     */
    private void goBackToPlayerHome() {
        PlayerHomeView playerHomeView = new PlayerHomeView(session);
        stage.getScene().setRoot(playerHomeView.createPlayerHomePage());
        stage.setTitle("Echo Shift - Player Statistics");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        new PlayerHomeController(stage,playerHomeView,session);
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Shop");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Shop Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

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