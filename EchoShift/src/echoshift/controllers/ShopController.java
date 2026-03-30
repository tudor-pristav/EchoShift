package echoshift.controllers;

import echoshift.PlayerHomeView;
import echoshift.ShopView;
import echoshift.models.Session;
import echoshift.models.UserAccount;
import echoshift.services.ShopService;
import echoshift.services.UserDataRetrievalService;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller for the Shop screen.
 * Connects the ShopView (UI) with backend services and manages the current user Session.
 */
public class ShopController {

    private final Stage stage;
    private final ShopView view;
    private final Session session;

    private final ShopService shopService;
    private final UserDataRetrievalService dataService;

    /**
     * Constructor following the same style as PlayerLoginController.
     */
    public ShopController(Stage stage, ShopView view, Session session) {
        this.stage = stage;
        this.view = view;
        this.session = session;

        this.shopService = new ShopService();
        this.dataService = new UserDataRetrievalService();

        attachHandlers();
        initializeShopData();
    }

    /**
     * Attaches event handlers from the view.
     * Currently only the Back button is wired (purchase toasts are handled inside ShopView).
     */
    private void attachHandlers() {
        // Back button - navigate to Player Home
        if (view.getBackButton() != null) {
            view.getBackButton().setOnAction(e -> goToPlayerHome());
        }
    }

    /**
     * Loads and displays user-specific data on the shop screen (e.g., coin balance).
     */
    private void initializeShopData() {
        if (session == null || session.getCurrentUser() == null) {
            return;
        }

        UserAccount account = session.getCurrentUser();

        // Update coin label
        view.updateCoinLabel("$" + account.getCoinBalance());
    }

    /**
     * Navigates back to the Player Home screen (same pattern as login).
     */
    private void goToPlayerHome() {
        PlayerHomeView homeView = new PlayerHomeView(session);

        // TODO: Attach PlayerHomeController when implemented
        // new PlayerHomeController(stage, homeView, session);

        stage.setScene(new Scene(homeView.createPlayerHomePage(), 1280, 720));
    }

    /**
     * Handles a real item purchase when you connect it from the view.
     * For now, ShopService is empty — this method prepares the structure.
     *
     * @param itemName  name of the item being purchased
     * @param itemPrice price of the item
     */
    public void handleItemPurchase(String itemName, int itemPrice) {
        if (session == null || session.getCurrentUser() == null) {
            view.showErrorToast("You must be logged in to purchase items.");
            return;
        }

        UserAccount account = session.getCurrentUser();

        // Delegate purchase logic to ShopService
        boolean success = shopService.purchaseItem(account.getId(), itemName, itemPrice);

        if (success) {
            // Refresh displayed coin balance after successful purchase
            initializeShopData();
            view.showPurchaseToast(itemName + " purchased successfully!");
        } else {
            view.showErrorToast("Purchase failed. Not enough coins or item unavailable.");
        }
    }

    // Optional getter for session (useful for other components)
    public Session getSession() {
        return session;
    }
}