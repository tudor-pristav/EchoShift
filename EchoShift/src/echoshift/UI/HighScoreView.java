package echoshift.UI;

import echoshift.animations.ButtonEffects;
import echoshift.models.HighScoreEntry;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

/**
 * Builds the High Scores screen for Echo Shift.
 * This class is responsible only for the frontend layout and UI elements.
 *
 * @author Bob Zhang
 * @author Tudor Pristav
 * @version 1.0.0
 */
public class HighScoreView {

    private final Button backButton;

    /**
     * Creates reusable controls for the high scores page.
     */
    public HighScoreView() {
        this.backButton = createButton("Back", 220, 42);
    }

    /**
     * Builds and returns the full High Scores screen.
     *
     * @param entries the leaderboard entries to display
     * @return the root node for this screen
     */
    public Parent createHighScoresPage(List<HighScoreEntry> entries) {
        BorderPane root = createRootLayout();

        root.getStylesheets().add(
                getClass().getResource("/echoshift/styles/buttonStyle.css").toExternalForm()
        );

        root.setTop(createTopBar());
        root.setCenter(createCenterContent(entries));
        root.setBottom(createBottomBar());

        return root;
    }

    /**
     * Creates the main root layout with background image.
     *
     * @return the root BorderPane
     */
    private BorderPane createRootLayout() {
        BorderPane root = new BorderPane();

        BackgroundImage bg = new BackgroundImage(
                new Image(getClass().getResource("/echoshift/images/bg2.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
        );

        root.setBackground(new Background(bg));
        return root;
    }

    /**
     * Creates the transparent top bar.
     *
     * @return the top bar
     */
    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setPrefHeight(60);
        topBar.setMinHeight(60);
        topBar.getStyleClass().add("top-bar");
        return topBar;
    }

    /**
     * Creates the center content with title and scores panel.
     *
     * @param entries the leaderboard entries
     * @return the center content wrapper
     */
    private StackPane createCenterContent(List<HighScoreEntry> entries) {
        Label title = createTitleLabel("Top Scores", 50);

        VBox scorePanel = createScorePanel(entries);

        VBox centerContent = new VBox(20, title, scorePanel);
        centerContent.setAlignment(Pos.CENTER);

        StackPane centerWrapper = new StackPane(centerContent);
        centerWrapper.setPadding(new Insets(40));

        return centerWrapper;
    }

    /**
     * Creates the scores panel container.
     *
     * @param entries the leaderboard entries
     * @return the scores panel
     */
    private VBox createScorePanel(List<HighScoreEntry> entries) {
        VBox scoreList = createScoreList(entries);
        ScrollPane scrollPane = createScrollPane(scoreList);

        VBox panel = new VBox(scrollPane);
        panel.setAlignment(Pos.CENTER);
        panel.setMaxWidth(600);
        panel.setPadding(new Insets(38));
        panel.getStyleClass().add("container");

        return panel;
    }

    /**
     * Creates the list of score entries.
     *
     * @param entries the leaderboard entries
     * @return the score list
     */
    private VBox createScoreList(List<HighScoreEntry> entries) {
        VBox scoreList = new VBox(10);
        scoreList.setAlignment(Pos.TOP_CENTER);

        for (int i = 0; i < entries.size(); i++) {
            scoreList.getChildren().add(createScoreEntry(i + 1, entries.get(i)));
        }

        return scoreList;
    }

    /**
     * Creates one score entry row.
     *
     * @param rank the rank number
     * @param entry the entry data
     * @return the score row node
     */
    private Node createScoreEntry(int rank, HighScoreEntry entry) {
        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPrefWidth(320);
        row.setPadding(new Insets(10, 14, 10, 14));
        row.getStyleClass().add("score-entry");

        Label rankLabel = createTextLabel("#" + rank, 18, true);
        rankLabel.setMinWidth(45);

        Label userLabel = createTextLabel(entry.getUsername(), 18, false);
        HBox.setHgrow(userLabel, Priority.ALWAYS);

        Label scoreLabel = createTextLabel(String.valueOf(entry.getHighScore()), 18, true);

        row.getChildren().addAll(rankLabel, userLabel, scoreLabel);
        return row;
    }

    /**
     * Creates the scroll pane for the scores list.
     *
     * @param content the content inside the scroll pane
     * @return the configured scroll pane
     */
    private ScrollPane createScrollPane(Node content) {
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportWidth(340);
        scrollPane.setPrefViewportHeight(320);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.getStyleClass().add("shop-scroll-pane");
        return scrollPane;
    }

    /**
     * Creates the transparent bottom bar with back button.
     *
     * @return the bottom bar
     */
    private BorderPane createBottomBar() {
        BorderPane bottomBar = new BorderPane();
        bottomBar.setPrefHeight(60);
        bottomBar.setMinHeight(60);
        bottomBar.setPadding(new Insets(10, 18, 10, 18));
        bottomBar.getStyleClass().add("bottom-bar");

        bottomBar.setLeft(backButton);
        BorderPane.setAlignment(backButton, Pos.CENTER_LEFT);

        return bottomBar;
    }

    /**
     * Creates a title label.
     *
     * @param text the label text
     * @param size the font size
     * @return the styled title label
     */
    private Label createTitleLabel(String text, double size) {
        Label label = new Label(text);
        label.setFont(Font.font("Verdana", FontWeight.NORMAL, size));
        label.setStyle("-fx-text-fill: black;");
        return label;
    }

    /**
     * Creates a regular text label.
     *
     * @param text the label text
     * @param size the font size
     * @param bold whether the text should be bold
     * @return the styled text label
     */
    private Label createTextLabel(String text, double size, boolean bold) {
        Label label = new Label(text);
        if (bold) {
            label.setFont(Font.font("Verdana", FontWeight.BOLD, size));
        } else {
            label.setFont(Font.font("Verdana", size));
        }
        label.setStyle("-fx-text-fill: black;");
        return label;
    }

    /**
     * Creates a button styled through CSS.
     *
     * @param text the button text
     * @param width preferred width
     * @param height preferred height
     * @return the styled button
     */
    private Button createButton(String text, double width, double height) {
        Button button = new Button(text);
        button.setPrefSize(width, height);
        button.setFont(Font.font("Verdana", 16));
        button.getStyleClass().add("button");

        ButtonEffects.hoverAnimation(button);
        ButtonEffects.clickAnimation(button);

        return button;
    }

    /**
     * Returns the back button so the controller can attach behavior.
     *
     * @return the back button
     */
    public Button getBackButton() {
        return backButton;
    }
}