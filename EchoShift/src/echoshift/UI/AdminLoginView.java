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

public class AdminLoginView {
    private final TextField usernameField;
    private final PasswordField passwordField;
    private final Button loginButton;
    private final Button menuButton;

    /**
     * Creates the reusable controls for the player login page.
     * @author Tudor Mihai Pristav
     */
    public AdminLoginView() {
        this.usernameField = createUsernameField();
        this.passwordField = createPasswordField();
        this.loginButton = createButton("Login", 170, 42);
        this.menuButton = createButton("Menu", 140, 42);
    }

    /**
     * Builds and returns the full Player Login screen.
     *
     * @return the root node for this screen
     */
    public Parent createPlayerLoginPage() {
        BorderPane root = createRootLayout();

        root.getStylesheets().add(
                getClass().getResource("/echoshift/styles/buttonStyle.css").toExternalForm()
        );

        // 🔹 TOP BAR (NEW)
        HBox topBar = new HBox();
        topBar.setPrefHeight(60);
        topBar.setMinHeight(60);
        topBar.setStyle("-fx-background-color: rgba(31, 30, 51, 0.35);");
        root.setTop(topBar);

        // 🔹 CENTER CONTENT
        Label pageTitle = createFieldLabel("Admin Login", 35);
        VBox loginCard = createLoginCard();

        VBox centerContent = new VBox(20, pageTitle, loginCard);
        centerContent.setAlignment(Pos.CENTER);

        StackPane centerWrapper = new StackPane(centerContent);
        centerWrapper.setPadding(new Insets(40));

        root.setCenter(centerWrapper);

        // 🔹 BOTTOM BAR (UPDATED)
        BorderPane bottomBar = new BorderPane();
        bottomBar.setPrefHeight(60);
        bottomBar.setMinHeight(60);
        bottomBar.setPadding(new Insets(10, 18, 10, 18));
        bottomBar.setStyle("-fx-background-color: rgba(31, 30, 51, 0.35);");

        bottomBar.setLeft(menuButton);
        BorderPane.setAlignment(menuButton, Pos.CENTER_LEFT);

        root.setBottom(bottomBar);

        return root;
    }

    /**
     * Creates the main root layout for the page.
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
     * Creates the centered login card containing labels, fields, and button.
     *
     * @return the login card layout
     */
    private VBox createLoginCard() {

        //"Username" Label
        Label usernameText = createFieldLabel("Username", 15);
        //"Password" Label
        Label passwordText = createFieldLabel("Password", 15);

        //login button alignment
        VBox buttonWrapper = new VBox(loginButton);
        buttonWrapper.setAlignment(Pos.CENTER);

        //custom spacing setting and grouping of the fields
        VBox usernameGroup = new VBox(6, usernameText, usernameField);
        VBox passwordGroup = new VBox(6, passwordText, passwordField);
        VBox loginButtonSpacing = new VBox(130, passwordGroup, buttonWrapper);

        //Main container on page, vertical list
        VBox card = new VBox(40,
                usernameGroup,
                loginButtonSpacing
        );

        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(300));
        card.setMaxWidth(400);
        card.getStyleClass().add("container");
        return card;
    }

    /**
     * Creates a label for a form field.
     *
     * @param text the label text
     * @return the field label
     */
    private Label createFieldLabel(String text, float size) {
        Label label = new Label(text);
        label.setFont(Font.font("Verdana", size));
        label.setStyle("-fx-text-fill: black;");
        return label;
    }

    /**
     * Creates the username text field.
     *
     * @return the username field
     */
    private TextField createUsernameField() {
        TextField field = new TextField();
        field.setPromptText("Username");
        field.setPrefSize(140, 34);
        field.setFont(Font.font("Verdana", 14));
        return field;
    }

    /**
     * Creates the password input field.
     *
     * @return the password field
     */
    private PasswordField createPasswordField() {
        PasswordField field = new PasswordField();
        field.setPromptText("Password");
        field.setPrefSize(140, 34);
        field.setFont(Font.font("Verdana", 14));
        return field;
    }

    /**
     * Creates a primary action button, used here for Login.
     *
     * @param text   button text
     * @param width  preferred width
     * @param height preferred height
     * @return the styled button
     */
    private Button createButton(String text, double width, double height) {
        Button button = new Button(text);
        button.setPrefSize(width, height);
        button.setFont(Font.font("Verdana", 16));

        //linking the style
        button.getStyleClass().add("button");

        //animations
        ButtonEffects.hoverAnimation(button);
        ButtonEffects.clickAnimation(button);
        return button;
    }

    /**
     * Returns the username input field so other classes can read its value.
     *
     * @return the username text field
     */
    public TextField getUsernameField() {
        return usernameField;
    }

    /**
     * Returns the password input field so other classes can read its value.
     *
     * @return the password field
     */
    public PasswordField getPasswordField() {
        return passwordField;
    }

    /**
     * Returns the login button so another class can attach its click behavior.
     *
     * @return the login button
     */
    public Button getLoginButton() {
        return loginButton;
    }

    /**
     * Returns the menu button so another class can attach navigation behavior.
     *
     * @return the menu button
     */
    public Button getMenuButton() {
        return menuButton;
    }

}
