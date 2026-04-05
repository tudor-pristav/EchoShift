package echoshift.UI;

import echoshift.animations.ButtonEffects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Builds the Player Stats screen.
 * Responsible only for frontend layout and UI elements.
 *
 * @author Tudor Pristav
 * @version 1.0.0
 */
public class PlayerHomeStatsView {

    private final Button backButton;

    private final Label titleLabel;
    private final Label peakWpmLabel;
    private final Label averageWpmLabel;
    private final Label accuracyLabel;
    private final Label errorCountLabel;
    private final Label totalTimePlayedLabel;
    private final Label highScoreLabel;
    private final Label highestLevelLabel;
    private final Label wordsTypedLabel;
    private final Label coinsLabel;

    /**
     * Creates reusable controls for the player stats page.
     */
    public PlayerHomeStatsView() {
        this.backButton = createButton("Back", 220, 42);

        this.titleLabel = createTitleLabel("Player Stats", 42);

        this.peakWpmLabel = createStatLabel("Top words per minute: 0");
        this.averageWpmLabel = createStatLabel("Average words per minute: 0");
        this.accuracyLabel = createStatLabel("Accuracy: 0%");
        this.errorCountLabel = createStatLabel("Error count: 0");
        this.totalTimePlayedLabel = createStatLabel("Total time played: 0");
        this.highScoreLabel = createStatLabel("High score: 0");
        this.highestLevelLabel = createStatLabel("Highest level reached: 1");
        this.wordsTypedLabel = createStatLabel("Words typed: 0");
        this.coinsLabel = createStatLabel("Coins: 0");
    }

    /**
     * Builds and returns the full player stats screen.
     *
     * @return the root node for this screen
     */
    public Parent createPlayerStatsPage() {
        BorderPane root = createRootLayout();

        root.getStylesheets().add(
                getClass().getResource("/echoshift/styles/buttonStyle.css").toExternalForm()
        );

        root.setTop(createTopBar());
        root.setCenter(createCenterContent());
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
     * Creates the center content with title and stats panel.
     *
     * @return the center content wrapper
     */
    private StackPane createCenterContent() {
        VBox statsList = new VBox(
                18,
                peakWpmLabel,
                averageWpmLabel,
                accuracyLabel,
                errorCountLabel,
                totalTimePlayedLabel,
                highScoreLabel,
                highestLevelLabel,
                wordsTypedLabel,
                coinsLabel
        );
        statsList.setAlignment(Pos.TOP_LEFT);

        VBox panel = new VBox(statsList);
        panel.setAlignment(Pos.CENTER_LEFT);
        panel.setMaxWidth(600);
        panel.setPadding(new Insets(38));
        panel.getStyleClass().add("container");

        VBox centerContent = new VBox(20, titleLabel, panel);
        centerContent.setAlignment(Pos.CENTER);

        StackPane centerWrapper = new StackPane(centerContent);
        centerWrapper.setPadding(new Insets(40));

        return centerWrapper;
    }

    /**
     * Creates the transparent bottom bar with navigation buttons.
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
     * Updates the page title with the selected username.
     *
     * @param username the player's username
     */
    public void setPlayerName(String username) {
        titleLabel.setText(username + "'s Stats and Settings");
    }

    /**
     * Updates the displayed stats.
     */
    public void setStats(
            double peakWpm,
            double averageWpm,
            double accuracy,
            int errorCount,
            double totalTimePlayed,
            int highScore,
            int highestLevel,
            int wordsTyped,
            int coins
    ) {
        peakWpmLabel.setText("Top words per minute: " + formatDouble(peakWpm));
        averageWpmLabel.setText("Average words per minute: " + formatDouble(averageWpm));
        accuracyLabel.setText("Accuracy: " + formatDouble(accuracy) + "%");
        errorCountLabel.setText("Error count: " + errorCount);
        totalTimePlayedLabel.setText("Total time played: " + formatDouble(totalTimePlayed));
        highScoreLabel.setText("High score: " + highScore);
        highestLevelLabel.setText("Highest level reached: " + highestLevel);
        wordsTypedLabel.setText("Words typed: " + wordsTyped);
        coinsLabel.setText("Coins: " + coins);
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
        label.setWrapText(true);
        return label;
    }

    /**
     * Creates a stat label.
     *
     * @param text the label text
     * @return the styled stat label
     */
    private Label createStatLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Verdana", 22));
        label.setStyle("-fx-text-fill: black;");
        label.setWrapText(true);
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
     * Formats doubles so they do not look ugly on screen.
     *
     * @param value the value to format
     * @return formatted text
     */
    private String formatDouble(double value) {
        return String.format("%.2f", value);
    }

    public Button getBackButton() {
        return backButton;
    }

}