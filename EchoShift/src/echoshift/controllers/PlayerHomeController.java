package echoshift.controllers;

import echoshift.UI.PlayerHomeView;
import echoshift.UI.ShopView;
import echoshift.models.Session;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PlayerHomeController {

    private final Stage stage;
    private final PlayerHomeView view;
    private final Session session;

    public PlayerHomeController(Stage stage, PlayerHomeView view, Session session) {
        this.stage = stage;
        this.view = view;
        this.session = session;

        attachHandlers();
    }

    /**
     * Attaches all button actions for the player home screen.
     */
    private void attachHandlers() {
        view.getNewGameButton().setOnAction(e -> handleNewGame());
        view.getSelectLevelButton().setOnAction(e -> handleSelectLevel());
        view.getInstructionsButton().setOnAction(e -> handleInstructions());
        view.getStatsButton().setOnAction(e -> handleStats());
        view.getSettingsButton().setOnAction(e -> handleSettings());
        view.getLogoutButton().setOnAction(e -> handleLogout());
        view.getShopButton().setOnAction(e -> handleShop());
        view.getExitButton().setOnAction(e -> handleExit());
    }

    private void handleNewGame() {
        System.out.println("Start new game");
        // TODO: open gameplay / level 1 screen
    }

    private void handleSelectLevel() {
        System.out.println("Open level selection");
        // TODO: open select level screen
    }

    private void handleInstructions() {
        System.out.println("Open instructions");
        // TODO: navigate to instructions page
    }

    private void handleStats() {
        System.out.println("Open player stats");
        // TODO: navigate to player statistics screen
    }

    private void handleSettings() {
        System.out.println("Open settings");
        // TODO: navigate to settings screen
    }

    private void handleLogout() {
        System.out.println("Logout");
        // TODO: send player back to main menu
    }

    private void handleShop() {
        ShopView shop = new ShopView(session);
       ShopController shopController = new ShopController(stage,shop,session);
        stage.setScene(new Scene(shop.createShopPage(), 1280, 720));
    }

    private void handleExit() {
        Platform.exit();
    }
}