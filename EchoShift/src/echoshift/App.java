package echoshift;

import com.sun.tools.javac.Main;
import echoshift.UI.MainMenuView;
import echoshift.UI.*;
import echoshift.UI.ShopView;
import echoshift.animations.SoundEffects;
import echoshift.controllers.PlayerLoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import echoshift.controllers.*;
public class App extends Application {



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
    public static void main(String[] args) {
        launch();
    }
    
}