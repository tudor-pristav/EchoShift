package echoshift.controllers;

import echoshift.UI.ChooseLevelView;
import echoshift.UI.ConfirmLevelView;
import echoshift.UI.PlayerHomeView;
import echoshift.models.Session;
import javafx.stage.Stage;

/**
 * Controller for the level selection screen.
 * Handles navigation between levels and player home.
 *
 * @author Tudor Mihai Pristav
 */
public class ChooseLevelController {

    private final Stage stage;
    private final ChooseLevelView view;
    private final Session session;

    /**
     * Initializes the controller and attaches handlers.
     *
     * @param stage the main application stage
     * @param view the choose level view
     * @param session the current session
     */
    public ChooseLevelController(Stage stage, ChooseLevelView view, Session session) {
        this.stage = stage;
        this.view = view;
        this.session = session;

        attachHandlers();
    }

    /**
     * Attaches button event handlers.
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
     * Navigates to player home.
     */
    private void goBackToPlayerHome() {
        PlayerHomeView playerHomeView = new PlayerHomeView(session);

        stage.getScene().setRoot(playerHomeView.createPlayerHomePage());
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        stage.setTitle("Echo Shift - Player Home");

        new PlayerHomeController(stage, playerHomeView, session);
    }

    /**
     * Navigates to level 1 confirmation.
     */
    private void goToLevel1() {
        ConfirmLevelView confirmLevelView = new ConfirmLevelView(session, 1);

        stage.getScene().setRoot(confirmLevelView.createConfirmLevelPage());
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        stage.setTitle("Echo Shift - Night 1");

        new ConfirmLevelController(stage, confirmLevelView, session, 1);
    }

    /**
     * Navigates to level 2 confirmation.
     */
    private void goToLevel2() {
        ConfirmLevelView confirmLevelView = new ConfirmLevelView(session, 2);

        stage.getScene().setRoot(confirmLevelView.createConfirmLevelPage());
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        stage.setTitle("Echo Shift - Night 2");

        new ConfirmLevelController(stage, confirmLevelView, session, 2);
    }

    /**
     * Navigates to level 3 confirmation.
     */
    private void goToLevel3() {
        ConfirmLevelView confirmLevelView = new ConfirmLevelView(session, 3);

        stage.getScene().setRoot(confirmLevelView.createConfirmLevelPage());
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        stage.setTitle("Echo Shift - Night 3");

        new ConfirmLevelController(stage, confirmLevelView, session, 3);
    }

    /**
     * Returns the highest unlocked level.
     *
     * @return highest level (min 1)
     */
    private int getHighestUnlockedLevel() {
        try {
            return Math.max(1, session.getCurrentStatistics().getHighestLevel());
        } catch (Exception e) {
            return 1;
        }
    }
}