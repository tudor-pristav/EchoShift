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
import echoshift.models.Session;

/**
 * View for the Player Home screen.
 * Displays navigation, user info, and actions.
 *
 * @author Tudor Mihai Pristav
 */
public class PlayerHomeView {
    private final Session session;
    private final Label welcomeLabel;
    private final Label coinsLabel;

    private final Button newGameButton;
    private final Button selectLevelButton;
    private final Button instructionsButton;
    private final Button statsButton;

    private final Button settingsButton;
    private final Button logoutButton;

    private final Button shopButton;
    private final Button exitButton;

    /**
     * Initializes the player home view.
     *
     * @param session current user session
     */
    public PlayerHomeView(Session session) {
        this.session = session;

        String username = session.getCurrentUser().getUsername();
        int coins = session.getCurrentStatistics().getCoins();

        this.welcomeLabel = createLabel("Ready for your next\nshift, " + username + "?", 34);
        this.coinsLabel = createSmallLabel("$" + coins, 18);

        this.newGameButton = createButton("New Game", 220, 60);
        this.selectLevelButton = createButton("Select Level", 220, 60);
        this.instructionsButton = createButton("Instructions", 220, 60);
        this.statsButton = createButton("Your Stats", 220, 60);

        this.settingsButton = createButton("Settings", 180, 48);
        this.logoutButton = createButton("Logout", 180, 48);

        this.shopButton = createButton("Shop", 220, 70);
        this.exitButton = createButton("Exit", 180, 48);
    }

    /**
     * Builds and returns the UI page.
     *
     * @return root node
     */
    public Parent createPlayerHomePage() {
        BorderPane root = createRootLayout();

        root.getStylesheets().add(
                getClass().getResource("/echoshift/styles/buttonStyle.css").toExternalForm()
        );

        root.setTop(createTopContainer());
        root.setLeft(createLeftSection());
        root.setRight(createRightSection());
        root.setBottom(createBottomBar());

        BorderPane.setMargin(root.getLeft(), new Insets(20, 0, 20, 20));
        BorderPane.setMargin(root.getRight(), new Insets(20, 20, 20, 0));

        return root;
    }

    /**
     * Creates the background layout.
     *
     * @return root layout
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
     * Creates the top section with welcome text and coins.
     *
     * @return top container
     */
    private VBox createTopContainer() {
        HBox topBar = new HBox();
        topBar.setPrefHeight(60);
        topBar.getStyleClass().add("top-bar");

        VBox topLeft = new VBox(welcomeLabel);
        topLeft.setAlignment(Pos.TOP_LEFT);

        VBox topRight = new VBox(15, coinsLabel, shopButton);
        topRight.setAlignment(Pos.TOP_RIGHT);

        BorderPane contentPane = new BorderPane();
        contentPane.setLeft(topLeft);
        contentPane.setRight(topRight);
        contentPane.setPadding(new Insets(20, 30, 0, 30));

        VBox wrapper = new VBox(topBar, contentPane);
        return wrapper;
    }

    /**
     * Creates the main navigation buttons.
     *
     * @return left section
     */
    private VBox createLeftSection() {
        VBox mainButtons = new VBox(
                18,
                newGameButton,
                selectLevelButton,
                instructionsButton,
                statsButton
        );
        mainButtons.setAlignment(Pos.CENTER_LEFT);

        return mainButtons;
    }

    /**
     * Creates the bottom bar with settings and exit.
     *
     * @return bottom bar
     */
    private BorderPane createBottomBar() {
        VBox bottomLeft = new VBox(12, settingsButton, logoutButton);
        bottomLeft.setAlignment(Pos.BOTTOM_LEFT);

        VBox bottomRight = new VBox(exitButton);
        bottomRight.setAlignment(Pos.BOTTOM_RIGHT);

        BorderPane bottomPane = new BorderPane();
        bottomPane.getStyleClass().add("bottom-bar");
        bottomPane.setPrefHeight(60);
        bottomPane.setMinHeight(60);
        bottomPane.setPadding(new Insets(10, 18, 10, 18));
        bottomPane.setLeft(bottomLeft);
        bottomPane.setRight(bottomRight);

        return bottomPane;
    }

    /**
     * Creates the right spacer section.
     *
     * @return right section
     */
    private VBox createRightSection() {
        VBox rightSpacer = new VBox();
        rightSpacer.setAlignment(Pos.CENTER_RIGHT);
        rightSpacer.setPrefWidth(220);
        return rightSpacer;
    }

    /**
     * Creates a styled label.
     */
    private Label createLabel(String text, float size) {
        Label label = new Label(text);
        label.setFont(Font.font("Verdana", size));
        label.setStyle("-fx-text-fill: black;");
        return label;
    }

    /**
     * Creates a styled small label.
     */
    private Label createSmallLabel(String text, float size) {
        Label label = new Label(text);
        label.setFont(Font.font("Verdana", size));
        label.setStyle(
                "-fx-text-fill: black;" +
                        "-fx-background-color: rgba(255,255,255,0.85);" +
                        "-fx-padding: 8 18 8 18;" +
                        "-fx-background-radius: 4;"
        );
        return label;
    }

    /**
     * Creates a styled button.
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

    /** @return new game button */
    public Button getNewGameButton() {
        return newGameButton;
    }

    /** @return select level button */
    public Button getSelectLevelButton() {
        return selectLevelButton;
    }

    /** @return instructions button */
    public Button getInstructionsButton() {
        return instructionsButton;
    }

    /** @return stats button */
    public Button getStatsButton() {
        return statsButton;
    }

    /** @return settings button */
    public Button getSettingsButton() {
        return settingsButton;
    }

    /** @return logout button */
    public Button getLogoutButton() {
        return logoutButton;
    }

    /** @return shop button */
    public Button getShopButton() {
        return shopButton;
    }

    /** @return exit button */
    public Button getExitButton() {
        return exitButton;
    }
}