package echoshift.controllers;

import echoshift.UI.ChooseLevelView;
import echoshift.UI.ConfirmLevelView;
import echoshift.models.Session;
import echoshift.nightscripts.MainGameplay;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the level confirmation screen.
 * Handles navigation back to level selection and starts the chosen level
 * when the player confirms they want to play.
 *
 * @author Tudor Mihai Pristav
 */
public class ConfirmLevelController {

    private final Stage stage;
    private final ConfirmLevelView view;
    private final Session session;
    private final int nightNumber;

    /**
     * Initializes the controller with the selected level information
     * and attaches the event handlers for the confirmation screen.
     *
     * @param stage the main application stage used for screen changes
     * @param view the confirmation screen view
     * @param session the current player session
     * @param nightNumber the level number selected by the player
     */
    public ConfirmLevelController(Stage stage, ConfirmLevelView view, Session session, int nightNumber) {
        this.stage = stage;
        this.view = view;
        this.session = session;
        this.nightNumber = nightNumber;
        attachHandlers();
    }

    /**
     * Attaches button handlers for returning to the previous screen
     * or launching the selected level.
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
     * Returns the player to the level selection screen
     * while keeping the current session active.
     */
    private void goBack() {
        ChooseLevelView chooseLevelView = new ChooseLevelView(session);
        stage.getScene().setRoot(chooseLevelView.createChooseLevelPage());
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        stage.setTitle("Echo Shift - Instructions");
        new ChooseLevelController(stage, chooseLevelView, session);
    }

    /**
     * Starts the selected level by launching the main gameplay
     * flow for the chosen night.
     *
     * @throws IOException if the gameplay resources fail to load
     */
    private void playLevel() throws IOException {
        System.out.println("before");
        MainGameplay mainGameplay = new MainGameplay(nightNumber, session);
        mainGameplay.start(stage);
        System.out.println("after");
    }
}