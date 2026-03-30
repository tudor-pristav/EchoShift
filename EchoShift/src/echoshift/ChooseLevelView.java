package echoshift;

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
 * A display of levels where the user may select from.
 * Locked levels are shown with reduced opacity and unclickable.
 *
 * @author Matthew Taylor
 */
public class ChooseLevelView {

    /**
     * Creates the Choose Level page UI.
     *
     * @param playerName the name of the player.
     * @return the root node containing the Choose Level UI.
     */
    public Parent createChooseLevelPage(String playerName) {
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
        Label title = new Label("Pick Your Next Shift, " + playerName + "...");
        title.setFont(Font.font("Arial", 40));
        title.setStyle("-fx-text-fill: black;");
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);
        BorderPane.setMargin(title, new Insets(20, 0, 20, 0));

        // Container for level buttons
        HBox levelButtons = new HBox(20);
        levelButtons.setAlignment(Pos.CENTER);

        // Create buttons for levels
        Button night1 = createLevelButton("Night 1", false);
        Button night2 = createLevelButton("Night 2", true); // initially locked
        Button night3 = createLevelButton("Night 3", true); // initially locked

        levelButtons.getChildren().addAll(night1, night2, night3);
        root.setCenter(levelButtons);

        // Bottom navigation buttons container
        HBox bottomBar = new HBox(20);
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setPadding(new Insets(10, 20, 20, 20));

        Button backButton = createMenuButton("Back", 110, 45);
        Button specialShiftsButton = createMenuButton("Special", 150, 45);

        bottomBar.getChildren().addAll(backButton, specialShiftsButton);
        root.setBottom(bottomBar);

        // Example action handlers (to be replaced with actual navigation)
        backButton.setOnAction(e -> System.out.println("Back clicked"));
        specialShiftsButton.setOnAction(e -> System.out.println("Special Shifts clicked"));
        night1.setOnAction(e -> System.out.println("Night 1 selected"));
        night2.setOnAction(e -> System.out.println("Night 2 selected"));
        night3.setOnAction(e -> System.out.println("Night 3 locked"));

        return root;
    }

    /**
     * Creates a button representing a level.
     *
     * @param text the text to display on the button.
     * @param locked true if the level is locked and should be disabled and dimmed.
     * @return the styled level button.
     */
    private Button createLevelButton(String text, boolean locked) {
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
        if (locked) {
            button.setDisable(true);
            button.setOpacity(0.5);
        }
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
