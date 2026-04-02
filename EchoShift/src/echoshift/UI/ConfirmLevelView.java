package echoshift.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * ConfirmLevelView displays the confirmation screen for a selected level,
 * showing level image, title, description, and play/back buttons.
 *
 * @author Matthew Taylor
 */
public class ConfirmLevelView {

    /**
     * Creates the Confirm Level page UI.
     *
     * @param playerName the name of the player.
     * @param levelTitle the title of the level.
     * @param levelDescription the description of the level.
     * @param levelImage the image representing the level cover.
     * @return the root node containing the Confirm Level UI.
     */
    public Parent createConfirmLevelPage(String playerName, String levelTitle, String levelDescription, Image levelImage) {
        BorderPane root = new BorderPane();
        // Try to load background image, fallback to grey background if fails
        try {
            Image bgImg = new Image(getClass().getResource("/echoshift/images/bg2.png").toExternalForm());
            BackgroundImage bgImage = new BackgroundImage(
                    bgImg,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true, false, true)
            );
            root.setBackground(new Background(bgImage));
        } catch (Exception e) {
            root.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
            System.err.println("Failed to load background image: " + e.getMessage());
        }

        // Greeting label at top
        Label greeting = new Label("Good luck, " + playerName + ".");
        greeting.setFont(Font.font("Arial", 28));
        greeting.setStyle("-fx-text-fill: black;");
        BorderPane.setAlignment(greeting, Pos.CENTER_LEFT);
        root.setTop(greeting);
        BorderPane.setMargin(greeting, new Insets(20, 20, 10, 20));

        // Center content: level image and description
        HBox centerBox = new HBox(20);
        centerBox.setPadding(new Insets(20));
        centerBox.setAlignment(Pos.CENTER);

        // Level cover image or grey placeholder if loading fails
        ImageView imageView;
        try {
            imageView = new ImageView(levelImage);
            imageView.setFitWidth(400);
            imageView.setFitHeight(400);
            imageView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Failed to load level image: " + e.getMessage());
            // Create grey rectangle placeholder
            Rectangle placeholder = new Rectangle(400, 400, Color.LIGHTGRAY);
            placeholder.setStroke(Color.DARKGRAY);
            placeholder.setStrokeWidth(1);
            imageView = new ImageView();
            StackPane placeholderPane = new StackPane(placeholder);
            placeholderPane.setPrefSize(400, 400);
            // Wrap placeholderPane in a Pane to add to centerBox later
            centerBox.getChildren().add(placeholderPane);
        }

        // Description box container
        VBox descriptionBox = new VBox(15);
        descriptionBox.setStyle("""
                -fx-background-color: white;
                -fx-border-color: #bdbdbd;
                -fx-border-width: 1;
                -fx-padding: 20;
                """);
        descriptionBox.setPrefWidth(400);

        Label levelTitleLabel = new Label(levelTitle);
        levelTitleLabel.setFont(Font.font("Arial", 24));
        levelTitleLabel.setStyle("-fx-text-fill: black;");

        Label descriptionLabel = new Label(levelDescription);
        descriptionLabel.setFont(Font.font("Arial", 16));
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-text-fill: black;");

        Button playButton = createMenuButton("Play Level", 150, 50);

        descriptionBox.getChildren().addAll(levelTitleLabel, descriptionLabel, playButton);

        // Add imageView or placeholder and descriptionBox to centerBox
        if (imageView.getImage() != null) {
            centerBox.getChildren().addAll(imageView, descriptionBox);
        } else if (!centerBox.getChildren().contains(descriptionBox)) {
            // If placeholder already added, add descriptionBox now
            centerBox.getChildren().add(descriptionBox);
        }
        root.setCenter(centerBox);

        // Bottom bar with Back button
        HBox bottomBar = new HBox();
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setPadding(new Insets(10, 20, 20, 20));

        Button backButton = createMenuButton("Back", 110, 45);
        bottomBar.getChildren().add(backButton);
        root.setBottom(bottomBar);

        // Example action handlers (to be replaced with actual navigation)
        backButton.setOnAction(e -> System.out.println("Back clicked"));
        playButton.setOnAction(e -> System.out.println("Play Level clicked"));

        return root;
    }

    /**
     * Creates a generic menu button with specified size.
     *
     * @param text the text that is displayed.
     * @param width the preferred width.
     * @param height the preferred height.
     * @return the styled menu button.
     */
    private Button createMenuButton(String text, double width, double height) {
        Button button = new Button(text);
        button.setPrefSize(width, height);
        button.setFont(Font.font("Arial", 22));
        button.setStyle("""
                -fx-background-color: white;
                -fx-text-fill: black;
                -fx-border-color: #bdbdbd;
                -fx-border-width: 1;
                -fx-cursor: hand;
                """);
        return button;
    }
}
