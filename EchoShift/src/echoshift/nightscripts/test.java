package echoshift.nightscripts;

import echoshift.UI.MapRenderer;
import echoshift.backend.Entity;
import echoshift.backend.GameMap;
import echoshift.backend.Listener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class test extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameMap gameMap = new GameMap();
        MapRenderer mapRenderer = new MapRenderer(gameMap);

        Entity regularEntity = new Entity(gameMap, 0, 1.0);
        Listener theListener = new Listener(gameMap, 0, 5.0);
        mapRenderer.addEntity(regularEntity);
        mapRenderer.addListener(theListener);

        StackPane root = new StackPane(mapRenderer.getMapPane());
        Scene scene = new Scene(root, 600, 580);

        primaryStage.setTitle("Echo Shift - Night Test (Night 3)");
        primaryStage.setScene(scene);
        primaryStage.show();

        Night currentNight = new Night(3, regularEntity, theListener, mapRenderer);
        currentNight.start();

        primaryStage.setOnCloseRequest(e -> currentNight.stopNight());
    }

    public static void main(String[] args) {
        launch(args);
    }
}