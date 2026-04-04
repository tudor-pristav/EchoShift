package echoshift.UI;

import echoshift.animations.ButtonEffects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 * Builds the Create Account screen for Echo Shift.
 * This class is responsible only for creating the layout and UI elements.
 */
public class CreateAccountView {

    private final TextField usernameField;
    private final TextField passwordField;
    private final Button createAccountButton;
    private final Button backButton;

    /**
     * Creates reusable controls for the create account page.
     */
    public CreateAccountView() {
        this.usernameField = createUsernameField();
        this.passwordField = createPasswordField();
        this.createAccountButton = createButton("Create Account", 190, 42);
        this.backButton = createButton("Back", 140, 42);
    }

    /**
     * Builds and returns the full Create Account page.
     *
     * @return the root node for this screen
     */
    public Parent createCreateAccountPage() {
        BorderPane root = createRootLayout();

        root.getStylesheets().add(
                getClass().getResource("/echoshift/styles/buttonStyle.css").toExternalForm()
        );

        HBox topBar = createTopBar();
        BorderPane bottomBar = createBottomBar();

        Label pageTitle = createFieldLabel("Create New Account", 35);
        VBox accountCard = createAccountCard();

        VBox centerContent = new VBox(20, pageTitle, accountCard);
        centerContent.setAlignment(Pos.CENTER);

        StackPane centerWrapper = new StackPane(centerContent);
        centerWrapper.setPadding(new Insets(40));

        root.setTop(topBar);
        root.setCenter(centerWrapper);
        root.setBottom(bottomBar);

        return root;
    }

    /**
     * Creates the root layout and background image.
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
     * Creates the translucent top bar.
     *
     * @return the top bar
     */
    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setPrefHeight(60);
        topBar.setMinHeight(60);
        topBar.setStyle("-fx-background-color: rgba(31, 30, 51, 0.35);");
        return topBar;
    }

    /**
     * Creates the translucent bottom bar with the back button.
     *
     * @return the bottom bar
     */
    private BorderPane createBottomBar() {
        BorderPane bottomBar = new BorderPane();
        bottomBar.setPrefHeight(60);
        bottomBar.setMinHeight(60);
        bottomBar.setPadding(new Insets(10, 18, 10, 18));
        bottomBar.setStyle("-fx-background-color: rgba(31, 30, 51, 0.35);");

        bottomBar.setLeft(backButton);
        BorderPane.setAlignment(backButton, Pos.CENTER_LEFT);

        return bottomBar;
    }

    /**
     * Creates the centered account creation card.
     *
     * @return the account card
     */
    private VBox createAccountCard() {
        Label usernameText = createFieldLabel("Username", 15);
        Label passwordText = createFieldLabel("Password", 15);

        VBox usernameGroup = new VBox(6, usernameText, usernameField);
        VBox passwordGroup = new VBox(6, passwordText, passwordField);

        VBox buttonWrapper = new VBox(createAccountButton);
        buttonWrapper.setAlignment(Pos.CENTER);

        VBox card = new VBox(22, usernameGroup, passwordGroup, buttonWrapper);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(35));
        card.setMaxWidth(380);
        card.getStyleClass().add("container");

        return card;
    }

    /**
     * Creates a label for the page or a field.
     *
     * @param text the label text
     * @param size the font size
     * @return the label
     */
    private Label createFieldLabel(String text, float size) {
        Label label = new Label(text);
        label.setFont(Font.font("Verdana", size));
        label.setStyle("-fx-text-fill: black;");
        return label;
    }

    /**
     * Creates the username field.
     *
     * @return the username text field
     */
    private TextField createUsernameField() {
        TextField field = new TextField();
        field.setPromptText("Username");
        field.setPrefSize(220, 34);
        field.setFont(Font.font("Verdana", 14));
        return field;
    }

    /**
     * Creates the password field.
     *
     * @return the password field
     */
    private TextField createPasswordField() {
        TextField field = new TextField();
        field.setPromptText("Password");
        field.setPrefSize(220, 34);
        field.setFont(Font.font("Verdana", 14));
        return field;
    }

    /**
     * Creates a styled button.
     *
     * @param text the button text
     * @param width the preferred width
     * @param height the preferred height
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
     * Returns the username field.
     *
     * @return the username field
     */
    public TextField getUsernameField() {
        return usernameField;
    }

    /**
     * Returns the password field.
     *
     * @return the password field
     */
    public TextField getPasswordField() {
        return passwordField;
    }

    /**
     * Returns the create account button.
     *
     * @return the create account button
     */
    public Button getCreateAccountButton() {
        return createAccountButton;
    }

    /**
     * Returns the back button.
     *
     * @return the back button
     */
    public Button getBackButton() {
        return backButton;
    }
}