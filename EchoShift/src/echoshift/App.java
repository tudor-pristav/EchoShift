package echoshift;

import echoshift.UI.MainMenuView;
import echoshift.animations.SoundEffects;
import echoshift.controllers.MainMenuController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry point for the EchoShift application.
 * Initializes the UI and launches the main menu.
 *
 * @author Tudor Mihai Pristav
 */
public class App extends Application {

    /**
     * Starts the JavaFX application.
     *
     * @param stage primary stage
     */
    @Override
    public void start(Stage stage) {
        MainMenuView mainMenuView = new MainMenuView();
        Parent root = mainMenuView.createMainMenu();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Echo Shift");
        stage.setMaximized(true);
        stage.show();

        SoundEffects.play();
        new MainMenuController(stage, mainMenuView);
    }

    /**
     * Launches the application.
     *
     * @param args program arguments
     */
    public static void main(String[] args) {
        launch();
    }
}