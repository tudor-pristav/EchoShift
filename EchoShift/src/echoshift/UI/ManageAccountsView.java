package echoshift.UI;

import echoshift.animations.ButtonEffects;
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
import echoshift.models.UserAccount;
import java.util.List;
import java.util.function.Consumer;
import java.util.List;
import java.util.function.Consumer;

/**
 * Builds the Manage Accounts screen.
 * Responsible only for frontend layout and UI elements.
 *
 * @author Tudor Pristav
 * @version 1.0.0
 */
public class ManageAccountsView {

    private final Button backButton;
    private final VBox playerListBox;

    /**
     * Creates reusable controls for the manage accounts page.
     */
    public ManageAccountsView() {
        this.backButton = createButton("Back", 220, 42);
        this.playerListBox = new VBox(10);
        this.playerListBox.setAlignment(Pos.TOP_CENTER);
    }

    /**
     * Builds and returns the full Manage Accounts page.
     *
     * @return the root node for this screen
     */
    public Parent createManageAccountsPage() {
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
     * Creates the center content with title and player list panel.
     *
     * @return the center wrapper
     */
    private StackPane createCenterContent() {
        Label title = createTitleLabel("List of Players", 50);

        ScrollPane scrollPane = createScrollPane(playerListBox);

        VBox panel = new VBox(scrollPane);
        panel.setAlignment(Pos.CENTER);
        panel.setMaxWidth(600);
        panel.setPadding(new Insets(38));
        panel.getStyleClass().add("container");

        VBox centerContent = new VBox(20, title, panel);
        centerContent.setAlignment(Pos.CENTER);

        StackPane centerWrapper = new StackPane(centerContent);
        centerWrapper.setPadding(new Insets(40));

        return centerWrapper;
    }

    /**
     * Replaces the list of displayed player names.
     *
     *
     * @param onPlayerClick callback for clicking a player
     */
    public void setPlayerList(List<UserAccount> accounts, Consumer<UserAccount> onPlayerClick) {
        playerListBox.getChildren().clear();

        for (UserAccount account : accounts) {
            playerListBox.getChildren().add(createPlayerEntry(account, onPlayerClick));
        }
    }

    /**
     * Creates one clickable player row.
     *
     * @param username the username to display
     * @param onPlayerClick callback to run on click
     * @return the player row node
     */
    private Node createPlayerEntry(String username, Consumer<String> onPlayerClick) {
        Button playerButton = new Button(username);
        playerButton.setPrefWidth(320);
        playerButton.setPrefHeight(45);
        playerButton.setFont(Font.font("Verdana", 20));
        playerButton.getStyleClass().add("score-entry");

        playerButton.setOnAction(e -> onPlayerClick.accept(username));

        return playerButton;
    }

    /**
     * Creates the scroll pane for the player list.
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
    private Node createPlayerEntry(UserAccount account, Consumer<UserAccount> onPlayerClick) {
        Button playerButton = new Button(account.getUsername());
        playerButton.setPrefWidth(320);
        playerButton.setPrefHeight(45);
        playerButton.setFont(Font.font("Verdana", 20));
        playerButton.getStyleClass().add("score-entry");

        playerButton.setOnAction(e -> onPlayerClick.accept(account));

        return playerButton;
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
     * Creates a styled button.
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