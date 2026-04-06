package echoshift.controllers;

import echoshift.UI.ChooseLevelView;
import echoshift.UI.ConfirmLevelView;
import echoshift.UI.PlayerHomeView;
import echoshift.models.Session;
import javafx.scene.Scene;
import javafx.stage.Stage;
import echoshift.nightscripts.*;

import java.io.IOException;

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
    private final int nightNumber;
    /**
     * Creates the controller and attaches handlers.
     *
     * @param stage the main application stage
     * @param view the choose level view
     * @param session the current player session
     */
    public ConfirmLevelController(Stage stage, ConfirmLevelView view, Session session, int nightNumber) {
        this.stage = stage;
        this.view = view;
        this.session = session;
        this.nightNumber = nightNumber;
        attachHandlers();
    }

    /**
     * Attaches all button handlers for the page.
     */
    private void attachHandlers() {
        view.getBackButton().setOnAction(e -> goBack());
        view.getPlayLevelButton().setOnAction(actionEvent -> {
            try {
                playLevel();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
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
    private void playLevel() throws IOException {
        System.out.println("before");
        MainGameplay mainGameplay = new MainGameplay(nightNumber,session);
        mainGameplay.start(stage);
        System.out.println("after");
    }

}