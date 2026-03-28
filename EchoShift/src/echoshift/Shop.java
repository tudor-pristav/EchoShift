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
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Shop {

    private static final String FONT = "Arial";

    // Toast components (kept as fields so we can reuse them)
    private Label toastLabel;
    private StackPane toastContainer;

    public Parent createShopScreen() {
        BorderPane root = new BorderPane();
        root.setStyle("""
                -fx-background-image: url('assets/bg2.png');
                -fx-background-size: cover;
                -fx-background-repeat: no-repeat;
                -fx-background-position: center;
                """);

        // ==================== TOP BAR ====================
        Label title = new Label("Shop");
        title.setFont(Font.font(FONT, 36));
        title.setStyle("-fx-text-fill: black;");

        Label coinLabel = new Label("$XXXX");
        coinLabel.setFont(Font.font(FONT, 20));
        coinLabel.setStyle("""
                -fx-text-fill: black;\s
                -fx-background-color: #d6d6d6;\s
                -fx-padding: 8 20;
               \s""");

        HBox topBar = new HBox(20, title, coinLabel);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(15, 30, 15, 30));
        topBar.setStyle("-fx-background-color: #1f1e3350;");

        // ==================== CENTER - SHOP ITEMS ====================
        GridPane itemsGrid = new GridPane();
        itemsGrid.setHgap(15);
        itemsGrid.setVgap(15);
        itemsGrid.setPadding(new Insets(30));
        itemsGrid.setAlignment(Pos.TOP_CENTER);

        for (int i = 0; i < 8; i++) {
            String itemName = "Item " + (i + 1);
            VBox itemBox = createShopItem(itemName, "Description\nLevel required: 3");

            // Click handler
            itemBox.setOnMouseClicked(e -> showPurchaseToast(itemName));

            // Item Box Style
            itemBox.setStyle("""
                -fx-background-color: #d6d6d6;
                -fx-padding: 10;
                -fx-cursor: hand;
                """);

            itemsGrid.add(itemBox, i % 4, i / 4);
        }

        ScrollPane scrollPane = new ScrollPane(itemsGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0;");
        scrollPane.getStyleClass().add("shop-scroll-pane");

        Platform.runLater(() -> {
            var viewport = scrollPane.lookup(".viewport");
            if (viewport != null) {
                viewport.setStyle("-fx-background-color: #1f1e3370;");
            }
        });

        // ==================== BOTTOM BAR ====================
        Button backButton = createButton("Back");
        backButton.setOnAction(e -> System.out.println("Back clicked"));

        HBox bottomBar = new HBox(backButton);
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setPadding(new Insets(15, 30, 15, 30));

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

        // ==================== TOAST (Permanent) ====================
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
        toastContainer.setOpacity(0.0);           // Start hidden
        toastContainer.setMouseTransparent(true); // Don't block mouse clicks

        // Main container: BorderPane + Toast on top
        StackPane mainContainer = new StackPane(root, toastContainer);

        // Assemble the BorderPane content
        root.setTop(topBar);
        root.setCenter(scrollPane);
        root.setBottom(bottomContainer);

        return mainContainer;
    }

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

    // ==================== SHOW TOAST ====================
    private void showPurchaseToast(String itemName) {
        if (toastLabel == null || toastContainer == null) return;

        toastLabel.setText(itemName + " purchased!");
        toastContainer.setOpacity(1.0);   // Make visible

        // Auto hide after 2.5 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.5), e -> {
            toastContainer.setOpacity(0.0);
        }));
        timeline.play();
    }
}