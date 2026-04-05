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

/**
 * Builds the Main Menu screen for Echo Shift.
 * This class is responsible only for the frontend layout and UI elements.
 */
public class MainMenuView {

    private final Button loginButton;
    private final Button instructionsButton;
    private final Button highScoresButton;
    private final Button adminLoginButton;
    private final Button settingsButton;
    private final Button exitButton;

    /**
     * Creates reusable controls for the main menu page.
     */
    public MainMenuView() {
        this.loginButton = createButton("Login", 220, 42);
        this.instructionsButton = createButton("Instructions", 220, 42);
        this.highScoresButton = createButton("High Scores", 220, 42);
        this.adminLoginButton = createButton("Admin Login", 220, 42);
        this.settingsButton = createButton("Settings", 220, 42);
        this.exitButton = createButton("Exit", 200, 42);
    }

    /**
     * Builds and returns the full Main Menu screen.
     *
     * @return the root node for this screen
     */
    public Parent createMainMenu() {
        BorderPane root = createRootLayout();
        root.getStylesheets().add(
                getClass().getResource("/echoshift/styles/buttonStyle.css").toExternalForm()
        );

        Label title = createLabel("Echo Shift", 50);
        VBox buttonBox = new VBox(
                20,
                loginButton,
                instructionsButton,
                highScoresButton,
                adminLoginButton,
                settingsButton
        );

        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setMaxWidth(400);
        buttonBox.setPadding(new Insets(60));
        buttonBox.getStyleClass().add("container");

        VBox centerContent = new VBox(20, title, buttonBox);
        centerContent.setAlignment(Pos.CENTER);

        StackPane centerWrapper = new StackPane(centerContent);
        centerWrapper.setPadding(new Insets(40));

        root.setCenter(centerWrapper);

        return root;
    }

    /**
     * Creates the root layout with a background image.
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

        HBox topBar = new HBox();
        topBar.setPrefHeight(60);
        topBar.getStyleClass().add("top-bar");
        root.setTop(topBar);

        BorderPane bottom = new BorderPane();
        bottom.getStyleClass().add("bottom-bar");

        bottom.setPrefHeight(60);
        bottom.setMinHeight(60);

        bottom.setPadding(new Insets(10, 18, 10, 18));

        bottom.setLeft(exitButton);
        BorderPane.setAlignment(exitButton, Pos.CENTER_LEFT);

        root.setBottom(bottom);

        return root;
    }

    /**
     * Creates a styled text label.
     *
     * @param text the label text
     * @param size the font size
     * @return the label
     */
    private Label createLabel(String text, double size) {
        Label label = new Label(text);
        label.setFont(Font.font("Verdana", size));
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

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getInstructionsButton() {
        return instructionsButton;
    }

    public Button getHighScoresButton() {
        return highScoresButton;
    }

    public Button getAdminLoginButton() {
        return adminLoginButton;
    }

    public Button getSettingsButton() {
        return settingsButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
}