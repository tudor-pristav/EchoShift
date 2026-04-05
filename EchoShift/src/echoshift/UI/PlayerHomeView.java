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
 * Builds the Player Home page for EchoShift.
 * Style follows the wireframe:
 * - welcome text top-left
 * - main navigation buttons on the left
 * - currency + shop on the top-right
 * - settings/logout bottom-left
 * - exit bottom-right
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

    public Parent createPlayerHomePage() {
        BorderPane root = createRootLayout();

        root.getStylesheets().add(
                getClass().getResource("/echoshift/styles/buttonStyle.css").toExternalForm()
        );

        root.setTop(createTopSection());
        root.setLeft(createLeftSection());
        root.setRight(createRightSection());
        root.setBottom(createBottomSection());

        BorderPane.setMargin(root.getTop(), new Insets(30, 30, 0, 30));
        BorderPane.setMargin(root.getLeft(), new Insets(20, 0, 20, 20));
        BorderPane.setMargin(root.getRight(), new Insets(20, 20, 20, 0));
        BorderPane.setMargin(root.getBottom(), new Insets(0, 20, 20, 20));

        return root;
    }

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

    private VBox createTopSection() {
        VBox topLeft = new VBox(welcomeLabel);
        topLeft.setAlignment(Pos.TOP_LEFT);

        VBox topRight = new VBox(15, coinsLabel, shopButton);
        topRight.setAlignment(Pos.TOP_RIGHT);

        BorderPane topPane = new BorderPane();
        topPane.setLeft(topLeft);
        topPane.setRight(topRight);

        VBox wrapper = new VBox(topPane);
        return wrapper;
    }

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

    private BorderPane createBottomSection() {
        VBox bottomLeft = new VBox(12, settingsButton, logoutButton);
        bottomLeft.setAlignment(Pos.BOTTOM_LEFT);

        VBox bottomRight = new VBox(exitButton);
        bottomRight.setAlignment(Pos.BOTTOM_RIGHT);

        BorderPane bottomPane = new BorderPane();
        bottomPane.setLeft(bottomLeft);
        bottomPane.setRight(bottomRight);

        return bottomPane;
    }

    private VBox createRightSection() {
        VBox rightSpacer = new VBox();
        rightSpacer.setAlignment(Pos.CENTER_RIGHT);
        rightSpacer.setPrefWidth(220);
        return rightSpacer;
    }

    private Label createLabel(String text, float size) {
        Label label = new Label(text);
        label.setFont(Font.font("Verdana", size));
        label.setStyle("-fx-text-fill: black;");
        return label;
    }

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

    private Button createButton(String text, double width, double height) {
        Button button = new Button(text);
        button.setPrefSize(width, height);
        button.setFont(Font.font("Verdana", 20));
        button.getStyleClass().add("button");

        ButtonEffects.hoverAnimation(button);
        ButtonEffects.clickAnimation(button);

        return button;
    }
    public Button getNewGameButton() {
        return newGameButton;
    }

    public Button getSelectLevelButton() {
        return selectLevelButton;
    }

    public Button getInstructionsButton() {
        return instructionsButton;
    }

    public Button getStatsButton() {
        return statsButton;
    }

    public Button getSettingsButton() {
        return settingsButton;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }

    public Button getShopButton() {
        return shopButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
}