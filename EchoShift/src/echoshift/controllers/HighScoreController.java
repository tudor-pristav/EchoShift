package echoshift.controllers;

import echoshift.UI.HighScoreView;
import echoshift.UI.MainMenuView;
import echoshift.models.HighScoreEntry;
import echoshift.models.UserAccount;
import echoshift.models.UserStatistics;
import echoshift.services.LoginService;
import echoshift.services.UserDataRetrievalService;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Controller for the high scores screen.
 * Loads player score data, displays the ranked scoreboard,
 * and handles navigation back to the main menu.
 *
 * @author Tudor Mihai Pristav
 */
public class HighScoreController {

    private final Stage stage;
    private final HighScoreView view;
    private final LoginService loginService;
    private final UserDataRetrievalService userDataRetrievalService;

    /**
     * Initializes the controller, prepares the required services,
     * loads the high score data, and connects UI event handlers.
     *
     * @param stage the main application stage used for screen changes
     * @param view the high score view
     */
    public HighScoreController(Stage stage, HighScoreView view) {
        this.stage = stage;
        this.view = view;
        this.loginService = new LoginService();
        this.userDataRetrievalService = new UserDataRetrievalService();

        initializePage();
        attachEvents();
    }

    /**
     * Builds the high score page using the latest player score data
     * and updates the main stage to display the screen.
     */
    private void initializePage() {
        List<HighScoreEntry> highScoreEntries = buildHighScoreEntries();

        stage.getScene().setRoot(view.createHighScoresPage(highScoreEntries));
        stage.setTitle("Echo Shift - High Scores");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
    }

    /**
     * Creates a list of high score entries by reading all player accounts
     * and retrieving the saved high score for each one.
     * The returned list is sorted from highest to lowest score.
     *
     * @return a sorted list of high score entries
     */
    private List<HighScoreEntry> buildHighScoreEntries() {
        List<UserAccount> accounts = loginService.returnAccounts();
        List<HighScoreEntry> entries = new ArrayList<>();

        for (UserAccount account : accounts) {
            try {
                UserStatistics stats = userDataRetrievalService.retrieveStatistics(account.getId());
                int highScore = stats.getHighScore();

                entries.add(new HighScoreEntry(account.getUsername(), highScore));
            } catch (RuntimeException e) {
                entries.add(new HighScoreEntry(account.getUsername(), 0));
            }
        }

        entries.sort(Comparator.comparingInt(HighScoreEntry::getHighScore).reversed());
        return entries;
    }

    /**
     * Attaches the back button action for returning to the main menu.
     * Rebuilds the main menu screen and reconnects its controller.
     */
    private void attachEvents() {
        view.getBackButton().setOnAction(e -> {
            MainMenuView mainMenuView = new MainMenuView();
            stage.getScene().setRoot(mainMenuView.createMainMenu());
            stage.setTitle("Echo Shift");
            stage.setFullScreenExitHint("");
            stage.setFullScreenExitKeyCombination(null);
            stage.setMaximized(true);

            new MainMenuController(stage, mainMenuView);
        });
    }
}