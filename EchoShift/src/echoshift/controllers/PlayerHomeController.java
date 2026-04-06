package echoshift.controllers;

import echoshift.UI.*;
import echoshift.models.Session;
import echoshift.services.UserDataSaveService;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

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

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Reset");
            confirm.setHeaderText(null);
            confirm.setContentText("Are you sure you want to reset your highest level to 1?");

            confirm.showAndWait().ifPresent(response -> {
                if (response == javafx.scene.control.ButtonType.OK) {
                    // do the reset
                    session.getCurrentStatistics().setHighestLevel(1);

                    try {
                        new UserDataSaveService().saveStatistics(
                                session.getCurrentUser().getId(),
                                session.getCurrentStatistics()
                        );

                        showSuccess("Highest level reset successfully.");

                    } catch (IOException ex) {
                        showError("Could not reset highest level.");
                        ex.printStackTrace();
                    }
                }
            });
    }
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void handleSelectLevel() {
        ChooseLevelView chooseLevelView = new ChooseLevelView(session);
        stage.getScene().setRoot(chooseLevelView.createChooseLevelPage());
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        stage.setTitle("Echo Shift - Instructions");
        new ChooseLevelController(stage,chooseLevelView,session);
    }

    private void handleInstructions() {
      InstructionsPlayerHomeView instructionsPlayerHomeView = new InstructionsPlayerHomeView(stage,session);
        stage.getScene().setRoot(instructionsPlayerHomeView.createPage());
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        stage.setTitle("Echo Shift - Instructions");

    }

    private void handleStats() {
        Parent previous = stage.getScene().getRoot();
        PlayerHomeStatsView playerHomeStatsView = new PlayerHomeStatsView();
        stage.getScene().setRoot(playerHomeStatsView.createPlayerStatsPage());
        stage.setTitle("Echo Shift - Personal Statistics");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        new PlayerHomeStatsController(stage,previous,playerHomeStatsView,session.getCurrentUser().getId(),session.getCurrentUser().getUsername());
    }

    private void handleSettings() {
        Parent previous = stage.getScene().getRoot();
        SettingsView settingsView = new SettingsView();
        stage.getScene().setRoot(settingsView.createSettingsPage());
        stage.setTitle("Echo Shift - Settings");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);

        new SettingsController(stage,previous, settingsView);
    }

    private void handleLogout() {
        MainMenuView mainMenuView = new MainMenuView();
        MainMenuController mainMenuController = new MainMenuController(stage,mainMenuView);
        stage.getScene().setRoot(mainMenuView.createMainMenu());
        stage.setTitle("Echo Shift");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
    }

    private void handleShop() {
        ShopView shop = new ShopView(session);
        stage.getScene().setRoot(shop.createShopPage());
        stage.setTitle("Echo Shift - Shop");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
       ShopController shopController = new ShopController(stage,shop,session);
    }

    private void handleExit() {
        Platform.exit();
    }
}