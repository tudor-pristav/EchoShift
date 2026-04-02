package echoshift;

import echoshift.UI.MapRenderer;
import echoshift.backend.GameMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MapTestDriver extends Application {

    private GameMap gameMap;
    private MapRenderer renderer;

    @Override
    public void start(Stage primaryStage) {
        gameMap = new GameMap();
        renderer = new MapRenderer(gameMap);

        // Root layout
        BorderPane root = new BorderPane();
        root.setCenter(renderer.getMapPane());

        // Scene setup (match approximate size of your map image)
        Scene scene = new Scene(root, 1000, 700);
        scene.setFill(Color.BLACK);

        primaryStage.setTitle("Echo Shift - Map Test Driver");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}