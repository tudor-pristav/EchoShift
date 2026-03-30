package echoshift;

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

        // Set background
        BackgroundImage bg = new BackgroundImage(
                new Image(getClass().getResource("/echoshift/images/bg2.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
        );
        root.setBackground(new Background(bg));

        // Top bar
        Label title = new Label("Shop");
        title.setFont(Font.font(FONT, 36));
        title.setStyle("-fx-text-fill: black;");

        // Coin label
        coinLabel = new Label("$XXXX");
        coinLabel.setFont(Font.font(FONT, 20));
        coinLabel.setStyle("""
                -fx-text-fill: black;
                -fx-background-color: #d6d6d6;
                -fx-padding: 8 20;
                """);

        // Top bar assemble
        HBox topBar = new HBox(20, title, coinLabel);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(15, 30, 15, 30));
        topBar.setStyle("-fx-background-color: #1f1e3350;");

        // Shop items
        GridPane itemsGrid = new GridPane();
        itemsGrid.setHgap(15);
        itemsGrid.setVgap(15);
        itemsGrid.setPadding(new Insets(30));
        itemsGrid.setAlignment(Pos.TOP_CENTER);

        // Adding shop items
        for (int i = 0; i < 8; i++) {
            String itemName = "Item " + (i + 1);
            VBox itemBox = createShopItem(itemName, "Level required: 3");

            itemBox.setOnMouseClicked(e -> showPurchaseToast(itemName));

            itemBox.setStyle("""
                    -fx-background-color: #d6d6d6;
                    -fx-padding: 10;
                    -fx-cursor: hand;
                    """);

            itemsGrid.add(itemBox, i % 4, i / 4);
        }

        // Make grid scrollable
        ScrollPane scrollPane = new ScrollPane(itemsGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0;");
        scrollPane.getStyleClass().add("shop-scroll-pane");

        // Scroll pane transparency fix
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

        // Timer
        Label timerLabel = new Label("2:43");
        timerLabel.setFont(Font.font(FONT, 18));
        timerLabel.setStyle("-fx-text-fill: black;");

        StackPane timerPane = new StackPane(timerLabel);
        timerPane.setPadding(new Insets(0, 30, 0, 0));
        timerPane.setAlignment(Pos.CENTER_RIGHT);

        BorderPane bottomContainer = new BorderPane();
        bottomContainer.setLeft(bottomBar);
        bottomContainer.setRight(timerPane);
        bottomContainer.setStyle("-fx-background-color: #1f1e3350;");

        // Toast notification
        toastLabel = new Label();
        toastLabel.setFont(Font.font(FONT, 16));
        toastLabel.setStyle("""
                -fx-background-color: #4ade80;
                -fx-text-fill: #1f1e33;
                -fx-padding: 14 40;
                -fx-background-radius: 50;
                -fx-font-weight: bold;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 3, 3);
                """);

        toastContainer = new StackPane(toastLabel);
        toastContainer.setAlignment(Pos.BOTTOM_CENTER);
        toastContainer.setPadding(new Insets(0, 0, 80, 0));
        toastContainer.setOpacity(0.0);
        toastContainer.setMouseTransparent(true);

        // Main container with toast overlay
        StackPane mainContainer = new StackPane(root, toastContainer);

        // Assemble everything
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
        imagePane.setStyle("""
                -fx-background-color: #1f1e33aa;
                -fx-border-color: #555;
                -fx-border-width: 2;
                -fx-background-radius: 6;
                """);
        imagePane.setPrefSize(110, 90);

        Label placeholder = new Label("🖼");
        placeholder.setFont(Font.font(40));
        placeholder.setStyle("-fx-text-fill: black;");
        imagePane.getChildren().add(placeholder);

        // Labels
        Label nameLabel = new Label(itemName);
        nameLabel.setFont(Font.font(FONT, 16));
        nameLabel.setStyle("-fx-text-fill: black;");

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font(FONT, 14));
        descLabel.setStyle("-fx-text-fill: #666;");
        descLabel.setAlignment(Pos.CENTER);
        descLabel.setWrapText(true);

        itemBox.getChildren().addAll(imagePane, nameLabel, descLabel);
        return itemBox;
    }

    /**
     * Creates a styled button.
     */
    private Button createButton(String text) {
        Button btn = new Button(text);
        btn.setFont(Font.font(FONT, 18));
        btn.setStyle("""
                -fx-background-color: #d6d6d6;
                -fx-text-fill: black;
                -fx-padding: 10 25;
                -fx-border-color: #555;
                -fx-border-width: 2;
                -fx-cursor: hand;
                """);
        btn.setPrefHeight(45);
        return btn;
    }

    /**
     * Shows a success toast notification (green).
     */
    public void showPurchaseToast(String itemName) {
        if (toastLabel == null || toastContainer == null) return;

        toastLabel.setStyle("""
                -fx-background-color: #4ade80;
                -fx-text-fill: #1f1e33;
                -fx-padding: 14 40;
                -fx-background-radius: 50;
                -fx-font-weight: bold;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 3, 3);
                """);

        toastLabel.setText(itemName + " purchased!");
        toastContainer.setOpacity(1.0);

        // Auto hide after 2.5 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.5), e -> {
            toastContainer.setOpacity(0.0);
        }));
        timeline.play();
    }

    /**
     * Shows an error toast notification (red).
     * Used by ShopController for failed purchases, etc.
     */
    public void showErrorToast(String message) {
        if (toastLabel == null || toastContainer == null) return;

        toastLabel.setStyle("""
                -fx-background-color: #ef4444;
                -fx-text-fill: white;
                -fx-padding: 14 40;
                -fx-background-radius: 50;
                -fx-font-weight: bold;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 3, 3);
                """);

        toastLabel.setText(message);
        toastContainer.setOpacity(1.0);

        // Auto hide after 2.8 seconds and reset style
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.8), e -> {
            toastContainer.setOpacity(0.0);

            // Reset to success style for next use
            Platform.runLater(() -> {
                toastLabel.setStyle("""
                        -fx-background-color: #4ade80;
                        -fx-text-fill: #1f1e33;
                        -fx-padding: 14 40;
                        -fx-background-radius: 50;
                        -fx-font-weight: bold;
                        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 3, 3);
                        """);
            });
        }));
        timeline.play();
    }

    /**
     * Updates the coin balance displayed in the top bar.
     * Called by ShopController after login or successful purchase.
     */
    public void updateCoinLabel(String text) {
        if (coinLabel != null) {
            coinLabel.setText(text);
        }
    }

    /**
     * Returns the back button so the controller can attach navigation behavior.
     */
    public Button getBackButton() {
        return backButton;
    }
}