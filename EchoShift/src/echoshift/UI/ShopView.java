package echoshift.UI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class ShopView {

    private static final String FONT = "Arial";

    // Top bar components
    private Label coinLabel;
    private Button backButton;

    // Toast notification stuff
    private Label toastLabel;
    private StackPane toastContainer;

    /**
     * Builds and returns the full Shop screen.
     *
     * @return the root node for the shop screen
     */
    public Parent createShopScreen() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("shop-root");   // ← moved from inline
        root.getStylesheets().add(
                getClass().getResource("/echoshift/styles/shopStyle.css").toExternalForm()
        );

        BackgroundImage bg = new BackgroundImage(
                new Image(getClass().getResource("/echoshift/images/bg2.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
        );
        root.setBackground(new Background(bg));

        // Title
        Label title = new Label("Shop");
        title.setFont(Font.font(FONT, 36));
        title.getStyleClass().add("shop-title");

        // Coin label
        coinLabel = new Label("$XXXX");
        coinLabel.setFont(Font.font(FONT, 20));
        coinLabel.getStyleClass().add("coin-label");

        // Top bar
        HBox topBar = new HBox(20, title, coinLabel);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(15, 30, 15, 30));
        topBar.getStyleClass().add("top-bar");

        // Shop items grid
        GridPane itemsGrid = new GridPane();
        itemsGrid.setHgap(15);
        itemsGrid.setVgap(15);
        itemsGrid.setPadding(new Insets(30));
        itemsGrid.setAlignment(Pos.TOP_CENTER);

        // Adding shop items (example – you can load real items later)
        for (int i = 0; i < 8; i++) {
            String itemName = "Item " + (i + 1);
            VBox itemBox = createShopItem(itemName, "Level required: 3");

            itemBox.setOnMouseClicked(e -> showPurchaseToast(itemName));
            itemBox.getStyleClass().add("shop-item");   // ← class instead of inline style

            itemsGrid.add(itemBox, i % 4, i / 4);
        }

        // ScrollPane
        ScrollPane scrollPane = new ScrollPane(itemsGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.getStyleClass().add("shop-scroll-pane");

        // Viewport transparency (still needed in code for .viewport lookup)
        Platform.runLater(() -> {
            var viewport = scrollPane.lookup(".viewport");
            if (viewport != null) {
                viewport.setStyle("-fx-background-color: #1f1e3370;");
            }
        });

        // Bottom Bar
        backButton = createButton("Back");

        HBox bottomBar = new HBox(backButton);
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setPadding(new Insets(15, 30, 15, 30));

        Label timerLabel = new Label("2:43");
        timerLabel.setFont(Font.font(FONT, 18));
        timerLabel.getStyleClass().add("timer-label");

        StackPane timerPane = new StackPane(timerLabel);
        timerPane.setPadding(new Insets(0, 30, 0, 0));
        timerPane.setAlignment(Pos.CENTER_RIGHT);

        BorderPane bottomContainer = new BorderPane();
        bottomContainer.setLeft(bottomBar);
        bottomContainer.setRight(timerPane);
        bottomContainer.getStyleClass().add("bottom-bar");

        // Toast notification
        toastLabel = new Label();
        toastLabel.setFont(Font.font(FONT, 16));
        toastLabel.getStyleClass().add("toast-label");   // default success style

        toastContainer = new StackPane(toastLabel);
        toastContainer.setAlignment(Pos.BOTTOM_CENTER);
        toastContainer.setPadding(new Insets(0, 0, 80, 0));
        toastContainer.setOpacity(0.0);
        toastContainer.setMouseTransparent(true);
        toastContainer.getStyleClass().add("toast-container");

        // Main container with toast overlay
        StackPane mainContainer = new StackPane(root, toastContainer);

        // Assemble
        root.setTop(topBar);
        root.setCenter(scrollPane);
        root.setBottom(bottomContainer);

        return mainContainer;
    }

    /**
     * Creates a single shop item box.
     */
    private VBox createShopItem(String itemName, String description) {
        VBox itemBox = new VBox(8);
        itemBox.setAlignment(Pos.CENTER);
        itemBox.setPrefSize(140, 160);

        // Image placeholder
        StackPane imagePane = new StackPane();
        imagePane.getStyleClass().add("shop-item-image");
        imagePane.setPrefSize(110, 90);

        Label placeholder = new Label("🖼");
        placeholder.setFont(Font.font(40));
        placeholder.getStyleClass().add("image-placeholder");
        imagePane.getChildren().add(placeholder);

        // Labels
        Label nameLabel = new Label(itemName);
        nameLabel.setFont(Font.font(FONT, 16));
        nameLabel.getStyleClass().add("shop-item-name");

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font(FONT, 14));
        descLabel.getStyleClass().add("shop-item-desc");
        descLabel.setAlignment(Pos.CENTER);
        descLabel.setWrapText(true);

        itemBox.getChildren().addAll(imagePane, nameLabel, descLabel);
        return itemBox;
    }

    /**
     * Creates a styled button using CSS class.
     */
    private Button createButton(String text) {
        Button btn = new Button(text);
        btn.setFont(Font.font(FONT, 18));
        btn.getStyleClass().add("shop-button");
        btn.setPrefHeight(45);
        return btn;
    }

    // Toast methods remain almost identical (we just change the style classes)
    public void showPurchaseToast(String itemName) {
        if (toastLabel == null || toastContainer == null) return;

        toastLabel.getStyleClass().remove("toast-error");
        toastLabel.getStyleClass().add("toast-success");

        toastLabel.setText(itemName + " purchased!");
        toastContainer.setOpacity(1.0);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.5), e -> {
            toastContainer.setOpacity(0.0);
        }));
        timeline.play();
    }

    public void showErrorToast(String message) {
        if (toastLabel == null || toastContainer == null) return;

        toastLabel.getStyleClass().remove("toast-success");
        toastLabel.getStyleClass().add("toast-error");

        toastLabel.setText(message);
        toastContainer.setOpacity(1.0);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.8), e -> {
            toastContainer.setOpacity(0.0);
            // Reset to success style after hiding
            Platform.runLater(() -> {
                toastLabel.getStyleClass().remove("toast-error");
                toastLabel.getStyleClass().add("toast-success");
            });
        }));
        timeline.play();
    }

    public void updateCoinLabel(String text) {
        if (coinLabel != null) {
            coinLabel.setText(text);
        }
    }

    public Button getBackButton() {
        return backButton;
    }
}