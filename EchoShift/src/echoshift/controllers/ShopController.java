package echoshift.controllers;

import echoshift.UI.PlayerHomeView;
import echoshift.UI.ShopView;
import echoshift.models.Session;
import echoshift.models.UserStatistics;
import echoshift.services.UserDataSaveService;
import javafx.scene.Scene;
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
    public ShopController(Stage stage, ShopView view, Session session) {
        this.stage = stage;
        this.view = view;
        this.session = session;
        this.saveService = new UserDataSaveService();

        refreshCoinsDisplay();
        attachHandlers();
    }

    /**
     * Attaches button handlers.
     */
    private void attachHandlers() {
        view.getBackButton().setOnAction(e -> goBackToPlayerHome());

        view.getItemOneButton().setOnAction(e -> buyItem("Easy Words", 25));
        view.getItemTwoButton().setOnAction(e -> buyItem("Extra Life", 40));
        view.getItemThreeButton().setOnAction(e -> buyItem("Instant Lure", 35));
        view.getItemFourButton().setOnAction(e -> buyItem("Instant Repair", 30));
    }

    /**
     * Updates the coins label from the current session stats.
     */
    private void refreshCoinsDisplay() {
        int coins = session.getCurrentStatistics().getCoins();
        view.setCoinsText("$" + coins);
    }

    /**
     * Attempts to buy an item.
     *
     * @param itemName item name
     * @param cost item cost
     */
    /**
     * Attempts to buy an item, deducts coins, updates the display,
     * and saves the new statistics to the player's JSON file.
     *
     * @param itemName the item name
     * @param cost the item cost
     */
    private void buyItem(String itemName, int cost) {
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

            refreshCoinsDisplay();
            showInfo(itemName + " purchased successfully.");
        } catch ( IOException e) {
            showError("Could not save updated player data.");
            e.printStackTrace();
        }
    }

    /**
     * Returns to the player home page.
     */
    private void goBackToPlayerHome() {
        PlayerHomeView playerHomeView = new PlayerHomeView(session);
        Scene scene = new Scene(playerHomeView.createPlayerHomePage(), 1000, 700);
        stage.setScene(scene);
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
    /**
     * Refreshes the coins label using the current session statistics.
     */

}