package echoshift.UI;

import echoshift.animations.ButtonEffects;
import echoshift.controllers.MainMenuController;
import echoshift.controllers.PlayerHomeController;
import echoshift.models.Session;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Simple instructions page with:
 * - background image
 * - back button bottom-left
 */
public class InstructionsPlayerHomeView {

    private final Stage stage;
    private final PlayerHomeView playerHomeView;
    private final Button backButton;
    private final Session session;
    public InstructionsPlayerHomeView(Stage stage, Session session) {
        this.stage = stage;
        this.playerHomeView = new PlayerHomeView(session);
        this.backButton = createButton("Back", 200, 42);
        this.session = session;
    }

    /**
     * Builds the page.
     */
    public Parent createPage() {
        BorderPane root = createRootLayout();

        root.getStylesheets().add(
                getClass().getResource("/echoshift/styles/buttonStyle.css").toExternalForm()
        );

        root.setBottom(createBottomBar());
        attachHandlers();

        return root;
    }

    /**
     * Background setup.
     */
    private BorderPane createRootLayout() {
        BorderPane root = new BorderPane();

        BackgroundImage bg = new BackgroundImage(
                new Image(getClass().getResource("/echoshift/images/instructions.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(
                        BackgroundSize.AUTO,
                        BackgroundSize.AUTO,
                        false,
                        false,
                        true,
                        false
                )
        );

        root.setBackground(new Background(bg));
        return root;
    }

    /**
     * Bottom bar with back button.
     */
    private BorderPane createBottomBar() {
        BorderPane bottom = new BorderPane();

        bottom.setPadding(new Insets(20));
        bottom.setMinHeight(80);

        bottom.setLeft(backButton);
        BorderPane.setAlignment(backButton, Pos.CENTER_LEFT);

        return bottom;
    }

    /**
     * Standard project button.
     */
    private Button createButton(String text, double width, double height) {
        Button button = new Button(text);
        button.setPrefSize(width, height);
        button.getStyleClass().add("button");

        ButtonEffects.hoverAnimation(button);
        ButtonEffects.clickAnimation(button);

        return button;
    }

    private void attachHandlers() {
        backButton.setOnAction(e -> goBack());
    }

    private void goBack() {
        stage.getScene().setRoot(playerHomeView.createPlayerHomePage());
        stage.setTitle("Echo Shift - Player Home");
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setMaximized(true);
        new PlayerHomeController(stage,playerHomeView,session);
    }

    public Button getBackButton() {
        return backButton;
    }
}