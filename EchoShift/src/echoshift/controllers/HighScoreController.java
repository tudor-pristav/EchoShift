package echoshift.controllers;

import echoshift.UI.HighScoreView;
import echoshift.UI.MainMenuView;
import echoshift.models.HighScoreEntry;
import echoshift.models.UserAccount;
import echoshift.models.UserStatistics;
import echoshift.services.LoginService;
import echoshift.services.UserDataRetrievalService;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Controller for the high scores page.
 */
public class HighScoreController {

    private final Stage stage;
    private final HighScoreView view;
    private final LoginService loginService;
    private final UserDataRetrievalService userDataRetrievalService;

    public HighScoreController(Stage stage, HighScoreView view) {
        this.stage = stage;
        this.view = view;
        this.loginService = new LoginService();
        this.userDataRetrievalService = new UserDataRetrievalService();

        initializePage();
        attachEvents();
    }

    /**
     * Loads the high scores and shows them in the view.
     */
    private void initializePage() {
        List<HighScoreEntry> highScoreEntries = buildHighScoreEntries();

        stage.getScene().setRoot( view.createHighScoresPage(highScoreEntries));
        stage.setTitle("Echo Shift - High Scores");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
    }

    /**
     * Builds a sorted list of username + high score entries.
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
                // If a player's stats file is missing/corrupt, just give them 0
                entries.add(new HighScoreEntry(account.getUsername(), 0));
            }
        }

        entries.sort(Comparator.comparingInt(HighScoreEntry::getHighScore).reversed());
        return entries;
    }

    /**
     * Attaches button actions.
     */
    private void attachEvents() {
        view.getBackButton().setOnAction(e -> {
            MainMenuView mainMenuView = new MainMenuView();
            stage.getScene().setRoot( mainMenuView.createMainMenu());
            stage.setTitle("Echo Shift");
            stage.setFullScreenExitHint("");
            stage.setFullScreenExitKeyCombination(null);
            stage.setMaximized(true);


            MainMenuController cont = new MainMenuController(stage,mainMenuView);
        });
    }
}