package echoshift;

import echoshift.controllers.PlayerLoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

      @Override
    public void start(Stage stage) {
       //MainMenuView mainMenuView = new MainMenuView();
       // PlayerLoginView login = new PlayerLoginView();
        //Scene scene = new Scene(mainMenuView.createMainMenu(), 1000, 700);

        //new PlayerLoginController(stage,login);
        //stage.setScene(new Scene(login.createPlayerLoginPage(), 1280, 720));
        stage.setTitle("Echo Shift");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        //testing to see
    }
    
}