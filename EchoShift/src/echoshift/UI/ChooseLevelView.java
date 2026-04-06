package echoshift.UI;

import echoshift.animations.ButtonEffects;
import echoshift.models.Session;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

/**
 * Builds the Choose Level screen for Echo Shift.
 * This class is responsible only for the frontend layout and UI elements.
 *
 * @author Matthew Taylor
 */
public class ChooseLevelView {

    private final Session session;

    private final Button backButton;
    private final Button level1Button;
    private final Button level2Button;
    private final Button level3Button;

    /**
     * Creates reusable controls for the choose level page.
     *
     * @param session the current player session
     */
    public ChooseLevelView(Session session) {
        this.session = session;

        this.backButton = createButton("Back", 200, 42);
        this.level1Button = createTransparentLevelButton();
        this.level2Button = createTransparentLevelButton();
        this.level3Button = createTransparentLevelButton();
    }

    /**
     * Builds the page.
     *
     * @return the choose level page
     */
    public Parent createChooseLevelPage() {
        BorderPane root = createRootLayout();

        root.getStylesheets().add(
                getClass().getResource("/echoshift/styles/buttonStyle.css").toExternalForm()
        );
        root.getStylesheets().add(
                getClass().getResource("/echoshift/styles/shopStyle.css").toExternalForm()
        );

        root.setTop(createTopBar());
        root.setCenter(createCenterContent());
        root.setBottom(createBottomBar());

        return root;
    }

    /**
     * Creates the main root layout.
     *
     * @return the root layout
     */
    private BorderPane createRootLayout() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("shop-root");
        return root;
    }

    /**
     * Creates the top bar with the page title.
     *
     * @return the top bar
     */
    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER);
        topBar.setPrefHeight(60);
        topBar.getStyleClass().add("top-bar");

        return topBar;
    }

    /**
     * Creates the center content panel.
     *
     * @return the center content
     */
    private StackPane createCenterContent() {
        int highestUnlockedLevel = getHighestUnlockedLevel();

        Label title = new Label("Select Level");
        title.getStyleClass().add("shop-title");
        title.setStyle("-fx-font-family: Verdana;");

        HBox levelsRow = new HBox(
                30,
                createLevelCard("Night 1", 1, highestUnlockedLevel, level1Button),
                createLevelCard("Night 2", 2, highestUnlockedLevel, level2Button),
                createLevelCard("Night 3", 3, highestUnlockedLevel, level3Button)
        );
        levelsRow.setAlignment(Pos.CENTER);

        VBox panel = new VBox(levelsRow);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(20));
        panel.setMaxWidth(700);
        panel.getStyleClass().add("container");

        // 👇 THIS is the important part
        VBox layout = new VBox(20, title, panel);
        layout.setAlignment(Pos.CENTER);

        StackPane wrapper = new StackPane(layout);
        wrapper.setPadding(new Insets(15));

        return wrapper;
    }
    /**
     * Creates the bottom bar.
     *
     * @return the bottom bar
     */
    private BorderPane createBottomBar() {
        BorderPane bottomBar = new BorderPane();
        bottomBar.getStyleClass().add("bottom-bar");
        bottomBar.setPrefHeight(60);
        bottomBar.setMinHeight(60);
        bottomBar.setPadding(new Insets(10, 18, 10, 18));

        bottomBar.setLeft(backButton);
        BorderPane.setAlignment(backButton, Pos.CENTER_LEFT);

        return bottomBar;
    }

    /**
     * Creates one level card.
     *
     * @param levelName the display name
     * @param levelNumber the level number
     * @param highestUnlockedLevel the highest unlocked level
     * @param button the level button
     * @return the level card
     */
    private VBox createLevelCard(String levelName, int levelNumber, int highestUnlockedLevel, Button button) {
        boolean locked = levelNumber > highestUnlockedLevel;

        Label title = new Label(levelName);
        title.getStyleClass().add("shop-item-title");
        title.setStyle("-fx-font-family: Verdana;");

        ImageView preview = new ImageView(
                new Image(getClass().getResource("/echoshift/images/Map-preview.png").toExternalForm())
        );
        preview.setFitWidth(300);
        preview.setFitHeight(360);
        preview.setPreserveRatio(false);

        StackPane imageStack = new StackPane();
        imageStack.setAlignment(Pos.CENTER);
        imageStack.setPrefSize(300, 360);

        Region grayOverlay = new Region();
        grayOverlay.getStyleClass().add("level-locked-overlay");
        grayOverlay.setPrefSize(300, 360);
        grayOverlay.setVisible(locked);

        Label lockedLabel = new Label("LOCKED");
        lockedLabel.getStyleClass().add("locked-label");
        lockedLabel.setStyle("-fx-font-family: Verdana;");
        lockedLabel.setVisible(locked);

        button.setPrefSize(300, 360);

        if (locked) {
            button.setOnAction(e -> showLockedAlert());
        }

        imageStack.getChildren().addAll(preview, grayOverlay, lockedLabel, button);

        VBox card = new VBox(18, title, imageStack);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("shop-card");

        return card;
    }

    /**
     * Creates a standard styled button.
     *
     * @param text the button text
     * @param width the width
     * @param height the height
     * @return the button
     */
    private Button createButton(String text, double width, double height) {
        Button button = new Button(text);
        button.setPrefSize(width, height);
        button.setStyle("-fx-font-family: Verdana;");
        ButtonEffects.hoverAnimation(button);
        ButtonEffects.clickAnimation(button);
        return button;
    }

    /**
     * Creates a transparent button placed over the level image.
     *
     * @return the transparent level button
     */
    private Button createTransparentLevelButton() {
        Button button = new Button();
        button.getStyleClass().add("level-image-button");
        return button;
    }

    /**
     * Shows the locked-level alert.
     */
    private void showLockedAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Level Locked");
        alert.setHeaderText(null);
        alert.setContentText("Level locked.");
        alert.showAndWait();
    }

    /**
     * Gets the highest unlocked level.
     *
     * @return the highest unlocked level
     */
    private int getHighestUnlockedLevel() {
        try {
            return Math.max(1,session.getCurrentStatistics().getHighestLevel());
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * Gets the back button.
     *
     * @return the back button
     */
    public Button getBackButton() {
        return backButton;
    }

    /**
     * Gets the level 1 button.
     *
     * @return the level 1 button
     */
    public Button getLevel1Button() {
        return level1Button;
    }

    /**
     * Gets the level 2 button.
     *
     * @return the level 2 button
     */
    public Button getLevel2Button() {
        return level2Button;
    }

    /**
     * Gets the level 3 button.
     *
     * @return the level 3 button
     */
    public Button getLevel3Button() {
        return level3Button;
    }
}