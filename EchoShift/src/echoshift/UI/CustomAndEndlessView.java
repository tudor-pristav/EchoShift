package echoshift.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Represents the Custom and Endless mode selection screen.
 * Allows players to choose between Endless and Custom game modes.
 *
 * @author Matthew Taylor
 */
public class CustomAndEndlessView {

    /**
     * Creates the Custom and Endless mode selection page UI.
     *
     * @param playerName the name of the player (currently unused in title)
     * @return the root node containing the Custom and Endless mode UI
     */
    public Parent createCustomEndlessPage(String playerName) {
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
            // If image loading fails, set solid grey background
            root.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
            System.err.println("Failed to load background image: " + e.getMessage());
        }

        // Title label
        Label title = new Label("Special Night Selection");
        title.setFont(Font.font("Arial", 40));
        title.setStyle("-fx-text-fill: black;");
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);
        BorderPane.setMargin(title, new Insets(20, 0, 20, 0));

        // Container for mode buttons
        HBox modeButtons = new HBox(40);
        modeButtons.setAlignment(Pos.CENTER);

        Button endlessButton = createModeButton("Endless");
        Button customButton = createModeButton("Custom");

        modeButtons.getChildren().addAll(endlessButton, customButton);
        root.setCenter(modeButtons);

        // Bottom back button container
        HBox bottomBar = new HBox();
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setPadding(new Insets(10, 20, 20, 20));

        Button backButton = createMenuButton("Back", 110, 45);
        bottomBar.getChildren().add(backButton);
        root.setBottom(bottomBar);

        // Example action handlers (to be replaced with actual navigation)
        backButton.setOnAction(e -> System.out.println("Back clicked"));
        endlessButton.setOnAction(e -> System.out.println("Endless mode selected"));
        customButton.setOnAction(e -> System.out.println("Custom mode selected"));

        return root;
    }

    /**
     * Creates a button for selecting a game mode.
     *
     * @param text the text to display on the button
     * @return the styled mode button
     */
    private Button createModeButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(150, 200);
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

    /**
     * Creates a generic menu button with specified size.
     *
     * @param text   the button text
     * @param width  preferred width
     * @param height preferred height
     * @return the styled menu button
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
