package echoshift.nightscripts;

import echoshift.UI.MapRenderer;
import echoshift.backend.Entity;
import echoshift.backend.GameMap;
import echoshift.backend.Listener;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import typing.TypingEngine;
import typing.TypingResult;
import typing.createWordBank;

import java.io.IOException;

public class test extends Application {
    private TypingEngine engine;

    private Label wordLabel = new Label();
    private Label statusLabel = new Label("Scanning...");
    private Label typedLabel = new Label("Typed: ");

    private boolean waitingForNextWord = false;
    private boolean placingLure = false;
    private int selectedNode = -1;

    private Entity entity;
    private MapRenderer renderer;

    @Override
    public void start(Stage stage) {
        GameMap gameMap = new GameMap();

        // Example word list
        String[] words = {"echo","shadow","signal","night","lure"};
        engine = new TypingEngine(words);

        // Map and renderer
        GameMap map = new GameMap();
        renderer = new MapRenderer(map);

        entity = new Entity(map, 0, 1.0);
        Listener listener = new Listener(map, 0, 3.0);

        renderer.addEntity(entity);
        renderer.addListener(listener);

        // Typing UI
        VBox typingBox = new VBox(10, wordLabel, typedLabel, statusLabel);
        BorderPane root = new BorderPane();
        root.setCenter(renderer.getMapPane());
        root.setBottom(typingBox);

        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("EchoShift Integration Test");
        stage.show();

        updateWordDisplay();

        // Node click handler for lure placement
        renderer.setNodeClickHandler(nodeID -> {
            System.out.println("Player selected node " + nodeID);
            placingLure = true;
            selectedNode = nodeID;
        });

        // Typing input
        scene.setOnKeyTyped(e -> handleTyping(e.getCharacter()));

        // Start night
        Night currentNight = new Night(3, entity, listener, renderer);
        currentNight.start();

        stage.setOnCloseRequest(e -> currentNight.stopNight());

    }

    private void handleTyping(String character) {
        if (waitingForNextWord) return;

        char c = character.charAt(0);
        TypingResult result = engine.inputChar(c);

        typedLabel.setText(typedLabel.getText() + c);

        if (result.isCorrect())
            statusLabel.setText("Correct");
        else
            statusLabel.setText("Incorrect");

        if (result.isWordFailed()) {
            statusLabel.setText("WORD FAILED");
            startPause();
        }

        if (result.isWordCompleted()) {
            if (placingLure) {
                placeLure(selectedNode);
                statusLabel.setText("Lure placed!");
                placingLure = false; // back to scan mode
            } else {
                performScan();
            }
            startPause();
        }
    }

    private void placeLure(int node) {
        System.out.println("Lure Placed at node " + node);
        entity.setCurrentRoom(node);
        renderer.updateEntityPosition(entity);
    }

    private void performScan() {
        System.out.println("Scanning...");
        statusLabel.setText("Scanning...");

        PauseTransition scan = new PauseTransition(Duration.seconds(2)); // adjust duration
        scan.setOnFinished(e -> {
            System.out.println("Scan ended");
            statusLabel.setText("Scan ended");
        });
        scan.play();
    }

    private void startPause() {
        waitingForNextWord = true;

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            typedLabel.setText("Typed: ");
            updateWordDisplay();
            waitingForNextWord = false;
        });
        pause.play();
    }

    private void updateWordDisplay() {
        wordLabel.setText("Type word: " + engine.getCurrentWord());
    }

    public static void main(String[] args) {
        launch(args);
    }
}