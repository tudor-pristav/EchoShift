package echoshift.controllers;

import echoshift.UI.ChooseLevelView;
import echoshift.UI.ConfirmLevelView;
import echoshift.UI.PlayerHomeView;
import echoshift.models.Session;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller for the Choose Level page.
 * Handles navigation between the level selector and other player pages.
 *
 * @author Tudor
 */
public class ChooseLevelController {

    private final Stage stage;
    private final ChooseLevelView view;
    private final Session session;

    /**
     * Creates the controller and attaches handlers.
     *
     * @param stage the main application stage
     * @param view the choose level view
     * @param session the current player session
     */
    public ChooseLevelController(Stage stage, ChooseLevelView view, Session session) {
        this.stage = stage;
        this.view = view;
        this.session = session;

        attachHandlers();
    }

    /**
     * Attaches all button handlers for the page.
     */
    private void attachHandlers() {
        view.getBackButton().setOnAction(e -> goBackToPlayerHome());

        view.getLevel1Button().setOnAction(e -> goToLevel1());

        if (getHighestUnlockedLevel() >= 2) {
            view.getLevel2Button().setOnAction(e -> goToLevel2());
        }

        if (getHighestUnlockedLevel() >= 3) {
            view.getLevel3Button().setOnAction(e -> goToLevel3());
        }
    }

    /**
     * Navigates back to the player home screen.
     */
    private void goBackToPlayerHome() {
        PlayerHomeView playerHomeView = new PlayerHomeView(session);
        stage.getScene().setRoot(playerHomeView.createPlayerHomePage());
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        stage.setTitle("Echo Shift - Player Home");
        new PlayerHomeController(stage,playerHomeView,session);
    }

    /**
     * Starts level 1.
     * Replace this with your real Level 1 page/controller.
     */
    private void goToLevel1() {
        ConfirmLevelView confirmLevelView = new ConfirmLevelView(session,1);
        stage.getScene().setRoot(confirmLevelView.createConfirmLevelPage());
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        stage.setTitle("Echo Shift - Night 3");
        new ConfirmLevelController(stage,confirmLevelView,session);

        // Example:
        // Level1View level1View = new Level1View(session);
        // Scene scene = new Scene(level1View.createLevel1Page(), 1000, 700);
        // stage.setScene(scene);
        // new Level1Controller(stage, level1View, session);
    }

    /**
     * Starts level 2.
     * Replace this with your real Level 2 page/controller.
     */
    private void goToLevel2() {
        ConfirmLevelView confirmLevelView = new ConfirmLevelView(session,2);
        stage.getScene().setRoot(confirmLevelView.createConfirmLevelPage());
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        stage.setTitle("Echo Shift - Night 2");

        // Example:
        // Level2View level2View = new Level2View(session);
        // Scene scene = new Scene(level2View.createLevel2Page(), 1000, 700);
        // stage.setScene(scene);
        // new Level2Controller(stage, level2View, session);
    }

    /**
     * Starts level 3.
     * Replace this with your real Level 3 page/controller.
     */
    private void goToLevel3() {
        ConfirmLevelView confirmLevelView = new ConfirmLevelView(session,3);
        stage.getScene().setRoot(confirmLevelView.createConfirmLevelPage());
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        stage.setTitle("Echo Shift - Night 3");

        // Example:
        // Level3View level3View = new Level3View(session);
        // Scene scene = new Scene(level3View.createLevel3Page(), 1000, 700);
        // stage.setScene(scene);
        // new Level3Controller(stage, level3View, session);
    }

    /**
     * Gets the highest unlocked level for the current player.
     *
     * @return the highest unlocked level, minimum 1
     */
    private int getHighestUnlockedLevel() {
        try {
            return Math.max(1, session.getCurrentStatistics().getHighestLevel());
        } catch (Exception e) {
            return 1;
        }
    }
}