package echoshift;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class App extends Application {

      @Override
    public void start(Stage stage) {
        MainMenuView mainMenuView = new MainMenuView();

        Scene scene = new Scene(mainMenuView.createMainMenu(), 1000, 700);

        stage.setTitle("Echo Shift");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    // testing the push feature  - Matthew
}