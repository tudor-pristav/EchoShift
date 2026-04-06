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
public class ConfirmLevelController {

    private final Stage stage;
    private final ConfirmLevelView view;
    private final Session session;

    /**
     * Creates the controller and attaches handlers.
     *
     * @param stage the main application stage
     * @param view the choose level view
     * @param session the current player session
     */
    public ConfirmLevelController(Stage stage, ConfirmLevelView view, Session session) {
        this.stage = stage;
        this.view = view;
        this.session = session;

        attachHandlers();
    }

    /**
     * Attaches all button handlers for the page.
     */
    private void attachHandlers() {
        view.getBackButton().setOnAction(e -> goBack());
    }

    /**
     * Navigates back to the player home screen.
     */
    private void goBack() {
        ChooseLevelView chooseLevelView = new ChooseLevelView(session);
        stage.getScene().setRoot(chooseLevelView.createChooseLevelPage());
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        stage.setTitle("Echo Shift - Instructions");
        new ChooseLevelController(stage,chooseLevelView,session);
    }

}