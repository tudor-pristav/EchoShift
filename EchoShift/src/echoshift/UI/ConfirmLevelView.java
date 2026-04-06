package echoshift.UI;

import echoshift.animations.ButtonEffects;
import echoshift.models.Session;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 * Builds the level confirmation page for Echo Shift.
 * This page shows the selected level preview, level information,
 * the player's name, and a button to start the level.
 */
public class ConfirmLevelView {

    private final Session session;
    private final int levelNumber;

    private final Label playerNameLabel;
    private final Label levelLabel;
    private final Label levelTitleLabel;
    private final Label levelDescriptionLabel;

    private final Button backButton;
    private final Button playLevelButton;

    /**
     * Creates the confirm level view.
     *
     * @param session the current player session
     * @param levelNumber the selected level number
     */
    public ConfirmLevelView(Session session, int levelNumber) {
        this.session = session;
        this.levelNumber = levelNumber;

        String username = session.getCurrentUser().getUsername();

        this.playerNameLabel = createMainLabel("Good luck, " + username + ".", 34);
        this.levelLabel = createMainLabel("Night " + levelNumber, 28);
        this.levelTitleLabel = createMainLabel(getLevelTitle(levelNumber), 26);
        this.levelDescriptionLabel = createDescriptionLabel(getLevelDescription(levelNumber), 18);

        this.backButton = createButton("Back", 180, 48);
        this.playLevelButton = createButton("Play level", 260, 60);
    }

    /**
     * Builds the full confirm level page.
     *
     * @return the page root
     */
    public Parent createConfirmLevelPage() {
        BorderPane root = createRootLayout();

        root.getStylesheets().add(
                getClass().getResource("/echoshift/styles/buttonStyle.css").toExternalForm()
        );
        root.getStylesheets().add(
                getClass().getResource("/echoshift/styles/shopStyle.css").toExternalForm()
        );

        root.setTop(createTopSection());
        root.setCenter(createCenterSection());
        root.setBottom(createBottomSection());

        return root;
    }

    /**
     * Creates the root layout with the shared background image.
     *
     * @return the root layout
     */
    private BorderPane createRootLayout() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("shop-root");
        return root;
    }

    /**
     * Creates the top section with the top bar and player greeting.
     *
     * @return the top section
     */
    private VBox createTopSection() {
        HBox topBar = new HBox();
        topBar.setPrefHeight(60);
        topBar.getStyleClass().add("top-bar");

        VBox content = new VBox(playerNameLabel);
        content.setAlignment(Pos.TOP_LEFT);
        content.setPadding(new Insets(18, 35, 0, 35));

        return new VBox(topBar, content);
    }

    /**
     * Creates the central panel matching the wireframe layout.
     *
     * @return the center section
     */
    private StackPane createCenterSection() {
        VBox leftColumn = createLeftColumn();
        VBox rightColumn = createRightColumn();

        HBox mainContent = new HBox(40, leftColumn, rightColumn);
        mainContent.setAlignment(Pos.CENTER_LEFT);

        HBox outerPanel = new HBox(mainContent);
        outerPanel.setAlignment(Pos.CENTER_LEFT);
        outerPanel.setPadding(new Insets(25));
        outerPanel.setMaxWidth(1100);
        outerPanel.setPrefHeight(430);
        outerPanel.setMaxHeight(430);
        outerPanel.getStyleClass().add("container");

        StackPane wrapper = new StackPane(outerPanel);
        wrapper.setPadding(new Insets(20, 40, 20, 40));

        return wrapper;
    }

    /**
     * Creates the left column with the night label and map preview.
     *
     * @return the left column
     */
    private VBox createLeftColumn() {
        ImageView mapPreview = new ImageView(
                new Image(getClass().getResource("/echoshift/images/content.png").toExternalForm())
        );
        mapPreview.setFitWidth(290);
        mapPreview.setFitHeight(360);
        mapPreview.setPreserveRatio(false);
        mapPreview.setSmooth(true);

        StackPane imageBox = new StackPane(mapPreview);
        imageBox.setPrefSize(290, 360);
        imageBox.setMaxSize(290, 360);
        imageBox.setStyle("""
                -fx-background-color: rgba(255,255,255,0.12);
                -fx-border-color: rgba(0,0,0,0.55);
                -fx-border-width: 1.5;
                -fx-padding: 0;
                """);

        VBox leftColumn = new VBox(18, levelLabel, imageBox);
        leftColumn.setAlignment(Pos.TOP_LEFT);

        return leftColumn;
    }

    /**
     * Creates the right column with the level title, description, and play button.
     *
     * @return the right column
     */
    private VBox createRightColumn() {
        VBox infoPanel = new VBox(25);
        infoPanel.setAlignment(Pos.TOP_LEFT);
        infoPanel.setPrefWidth(560);
        infoPanel.setPrefHeight(360);
        infoPanel.setMinHeight(360);
        infoPanel.setMaxHeight(360);
        infoPanel.setPadding(new Insets(25));
        infoPanel.setStyle("""
                -fx-background-color: rgba(255,255,255,0.20);
                -fx-border-color: rgba(0,0,0,0.35);
                -fx-border-width: 1.5;
                """);

        VBox textGroup = new VBox(18, levelTitleLabel, levelDescriptionLabel);
        textGroup.setAlignment(Pos.TOP_LEFT);

        HBox buttonRow = new HBox(playLevelButton);
        buttonRow.setAlignment(Pos.CENTER);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        infoPanel.getChildren().addAll(textGroup, spacer, buttonRow);

        return infoPanel;
    }

    /**
     * Creates the bottom section with the back button.
     *
     * @return the bottom section
     */
    private BorderPane createBottomSection() {
        BorderPane bottomBar = new BorderPane();
        bottomBar.getStyleClass().add("bottom-bar");
        bottomBar.setPrefHeight(60);
        bottomBar.setMinHeight(60);
        bottomBar.setPadding(new Insets(10, 18, 10, 18));

        bottomBar.setLeft(backButton);
        BorderPane.setAlignment(backButton, Pos.CENTER_LEFT);

        return bottomBar;
    }

    /**
     * Creates a main text label.
     *
     * @param text the label text
     * @param size the font size
     * @return the label
     */
    private Label createMainLabel(String text, float size) {
        Label label = new Label(text);
        label.setFont(Font.font("Verdana", size));
        label.setStyle("-fx-text-fill: black;");
        return label;
    }

    /**
     * Creates a wrapped description label.
     *
     * @param text the label text
     * @param size the font size
     * @return the description label
     */
    private Label createDescriptionLabel(String text, float size) {
        Label label = new Label(text);
        label.setFont(Font.font("Verdana", size));
        label.setWrapText(true);
        label.setMaxWidth(620);
        label.setStyle("-fx-text-fill: black;");
        return label;
    }

    /**
     * Creates a styled button.
     *
     * @param text the button text
     * @param width the preferred width
     * @param height the preferred height
     * @return the button
     */
    private Button createButton(String text, double width, double height) {
        Button button = new Button(text);
        button.setPrefSize(width, height);
        button.setFont(Font.font("Verdana", 20));
        button.getStyleClass().add("button");

        ButtonEffects.hoverAnimation(button);
        ButtonEffects.clickAnimation(button);

        return button;
    }

    /**
     * Returns the level title for the given level.
     *
     * @param levelNumber the level number
     * @return the level title
     */
    private String getLevelTitle(int levelNumber) {
        return switch (levelNumber) {
            case 1 -> "Night 1 – The Breakout";
            case 2 -> "Night 2 – The Hunter Learns";
            case 3 -> "Night 3 – No Escape";
            default -> "Unknown Level";
        };
    }

    /**
     * Returns the description for the given level.
     *
     * @param levelNumber the level number
     * @return the level description
     */
    private String getLevelDescription(int levelNumber) {
        return switch (levelNumber) {
            case 1 -> "The Entity has just escaped. It moves slowly, still learning the layout of the facility and how to hunt. It may stumble and hesitate, but don’t underestimate it.";
            case 2 -> "The Entity is adapting. It moves faster now and has begun to understand how to track you. Safe moments are fewer, and every second could bring it closer.";
            case 3 -> "The Entity has fully adapted to the facility. It moves with terrifying speed and purpose, leaving almost no time to react. Survive the night, if you can.";
            default -> "No description available.";
        };
    }

    /**
     * Gets the back button.
     *
     * @return the back button
     */
    public Button getBackButton() {
        return backButton;
    }

    /**
     * Gets the play level button.
     *
     * @return the play level button
     */
    public Button getPlayLevelButton() {
        return playLevelButton;
    }

    /**
     * Gets the selected level number.
     *
     * @return the level number
     */
    public int getLevelNumber() {
        return levelNumber;
    }
}