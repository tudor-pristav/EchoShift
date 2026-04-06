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
 * View for the Shop screen.
 * Displays items, coins, and purchase options.
 *
 * @author Tudor Mihai Pristav
 */
public class ShopView {

    private final Button backButton;

    private final Button itemOneButton;
    private final Button itemTwoButton;
    private final Button itemThreeButton;
    private final Button itemFourButton;

    private final Label titleLabel;
    private final Label coinsLabel;
    private final Session session;

    private final Label itemOneCountLabel;
    private final Label itemTwoCountLabel;
    private final Label itemThreeCountLabel;

    /**
     * Initializes the shop view.
     *
     * @param session current user session
     */
    public ShopView(Session session) {
        this.backButton = createButton("Back", 200, 42);
        int coins = session.getCurrentStatistics().getCoins();
        this.itemOneButton = createShopButton();
        this.itemTwoButton = createShopButton();
        this.itemThreeButton = createShopButton();
        this.itemFourButton = createShopButton();
        this.session = session;
        this.titleLabel = createTitleLabel("Shop", 50);
        this.coinsLabel = createCoinsLabel("$" + coins, 29);

        this.itemOneCountLabel = createOwnedLabel();
        this.itemTwoCountLabel = createOwnedLabel();
        this.itemThreeCountLabel = createOwnedLabel();
    }

    /**
     * Builds and returns the shop page.
     *
     * @return root node
     */
    public Parent createShopPage() {
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

        BorderPane.setMargin(root.getCenter(), new Insets(20, 0, 20, 0));

        return root;
    }

    /**
     * Creates the root layout with background.
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
        root.setPrefSize(1000, 700);
        root.getStyleClass().add("shop-root");

        return root;
    }

    /**
     * Creates the top bar with coins display.
     */
    private HBox createTopSection() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topBar = new HBox(spacer, coinsLabel);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(14, 18, 14, 18));
        topBar.setMinHeight(60);
        topBar.getStyleClass().add("top-bar");

        return topBar;
    }

    /**
     * Creates the center section with title and items.
     */
    private VBox createCenterSection() {
        VBox centerBox = new VBox(25, titleLabel, createShopPanel());
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(35));
        return centerBox;
    }

    /**
     * Creates the bottom bar with back button.
     */
    private BorderPane createBottomSection() {
        BorderPane bottomBar = new BorderPane();
        bottomBar.setPadding(new Insets(14, 18, 14, 18));
        bottomBar.setMinHeight(60);
        bottomBar.getStyleClass().add("bottom-bar");

        bottomBar.setLeft(backButton);
        BorderPane.setAlignment(backButton, Pos.CENTER_LEFT);

        return bottomBar;
    }

    /**
     * Creates the shop items panel.
     */
    private StackPane createShopPanel() {
        GridPane itemsGrid = new GridPane();
        itemsGrid.setAlignment(Pos.CENTER);
        itemsGrid.setHgap(28);
        itemsGrid.setVgap(28);

        itemsGrid.add(createShopCard(
                "/echoshift/images/easier-words-icon.png",
                "Easy Words: $25",
                itemOneCountLabel,
                itemOneButton
        ), 0, 0);

        itemsGrid.add(createShopCard(
                "/echoshift/images/extra-life-icon.png",
                "Extra Life: $40",
                itemTwoCountLabel,
                itemTwoButton
        ), 1, 0);

        itemsGrid.add(createShopCard(
                "/echoshift/images/instant-lure-icon.png",
                "Instant Lure: $35",
                itemThreeCountLabel,
                itemThreeButton
        ), 2, 0);

        StackPane panel = new StackPane(itemsGrid);
        panel.getStyleClass().add("shop-panel");

        return panel;
    }

    /**
     * Creates a shop item card.
     */
    private VBox createShopCard(String imagePath, String itemName, Label ownedLabel, Button button) {
        ImageView imageView = createItemImage(imagePath);
        Label nameLabel = createCardTitle(itemName, 10);

        VBox card = new VBox(15, imageView, nameLabel, button, ownedLabel);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("shop-card");

        return card;
    }

    /**
     * Creates an item image.
     */
    private ImageView createItemImage(String imagePath) {
        ImageView imageView = new ImageView(
                new Image(getClass().getResource(imagePath).toExternalForm())
        );
        imageView.setFitWidth(110);
        imageView.setFitHeight(110);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    /**
     * Creates a title label.
     */
    private Label createTitleLabel(String text, float size) {
        Label label = new Label(text);
        label.setFont(Font.font("Verdana", size));
        label.getStyleClass().add("shop-title");
        return label;
    }

    /**
     * Creates a coins label.
     */
    private Label createCoinsLabel(String text, float size) {
        Label label = new Label(text);
        label.setFont(Font.font("Verdana", size));
        label.getStyleClass().add("coins");
        return label;
    }

    /**
     * Creates a card title label.
     */
    private Label createCardTitle(String text, float size) {
        Label label = new Label(text);
        label.setFont(Font.font("Verdana", size));
        label.getStyleClass().add("shop-item-title");
        return label;
    }

    /**
     * Creates a styled button.
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
     * Creates an owned count label.
     */
    private Label createOwnedLabel() {
        Label label = new Label("Owned: 0");
        label.getStyleClass().add("owned");
        return label;
    }

    /**
     * Creates a purchase button.
     */
    private Button createShopButton() {
        return createButton("Buy", 130, 40);
    }

    /** @return back button */
    public Button getBackButton() {
        return backButton;
    }

    /** @return item one button */
    public Button getItemOneButton() {
        return itemOneButton;
    }

    /** @return item two button */
    public Button getItemTwoButton() {
        return itemTwoButton;
    }

    /** @return item three button */
    public Button getItemThreeButton() {
        return itemThreeButton;
    }

    /** @return item four button */
    public Button getItemFourButton() {
        return itemFourButton;
    }

    /** @return coins label */
    public Label getCoinsLabel() {
        return coinsLabel;
    }

    /**
     * Updates coins display.
     */
    public void setCoinsText(String text) {
        coinsLabel.setText(text);
    }

    /**
     * Updates item one count.
     */
    public void setItemOneCountText(String text) {
        itemOneCountLabel.setText(text);
    }

    /**
     * Updates item two count.
     */
    public void setItemTwoCountText(String text) {
        itemTwoCountLabel.setText(text);
    }

    /**
     * Updates item three count.
     */
    public void setItemThreeCountText(String text) {
        itemThreeCountLabel.setText(text);
    }
}