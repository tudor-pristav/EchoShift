package echoshift.nightscripts;

import echoshift.UI.MapRenderer;
import echoshift.backend.Entity;
import echoshift.backend.GameMap;

import typing.TypingEngine;
import typing.TypingResult;
import typing.createWordBank;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExtremelyBigTest extends Application {
    private Stage stage;
    private GameMap gameMap;
    private TypingEngine engine;
    private BorderPane root;

    private final Label wordLabel = new Label();
    private final Label statusLabel = new Label("Scanning...");
    private final Label typedLabel = new Label("Typed: ");

    private boolean waitingForNextWord = false;
    private boolean placingLure = false;
    private int selectedNode = -1;
    private int score = 0;

    private Entity entity;
    private MapRenderer renderer;

    private final ArrayList<ImageView> heartArray = new ArrayList<ImageView>();
    private final Image entityImage = new Image("/echoshift/images/entity-tracker.gif", 50, 50, false, false);
    private final Image heart = new Image("/echoshift/images/live.png", 50, 50, false, false);
    private final Image instantHealth = new Image("/echoshift/images/instant-health.png", 50, 50, false, false);
    private final Image lure = new Image("/echoshift/images/instant-lure-icon.png", 50, 50, false, false);
    private final Image easyWord = new Image("/echoshift/images/easier-words-icon.png", 50, 50, false, false);

    private Label scoreLabel;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        gameMap = new GameMap();

        // Example word list
        engine = new TypingEngine(createWordBank.create(1));

        // Live indicator
        HBox liveBox = new HBox();
        liveBox.setSpacing(10);
        for (int i=0; i<3; i++) {
            ImageView liveImage = new ImageView();
            liveImage.setImage(heart);
            heartArray.add(liveImage);
            liveBox.getChildren().add(liveImage);
        }

        // Map and renderer
        GameMap map = new GameMap();
        renderer = new MapRenderer(map);

        entity = new Entity(map, 0, 1.0);

        renderer.addEntity(entity);

        scoreLabel = new Label("Score: " + score);

        // Typing UI
        HBox typingBox = handleTyping();

        // Power-up buttons
        VBox powerUpBar = new VBox();
        powerUpBar.setAlignment(Pos.CENTER_LEFT);
        VBox instantHealthVBox = createPowerUpBox(instantHealth);
        VBox easierWordsVBox = createPowerUpBox(lure);
        VBox instantLureVBox = createPowerUpBox(easyWord);
        powerUpBar.getChildren().addAll(scoreLabel, instantHealthVBox, easierWordsVBox, instantLureVBox);

        scoreLabel.setStyle("""
                -fx-alignment: TOP_RIGHT;
                -fx-background-color: #FFFFFF70;
                -fx-font-size: 18;
                -fx-cursor: hand;
                -fx-border-color: white;
                -fx-border-width: 1;
                -fx-text-alignment: center;
                """);

        // Assemble root
        root = new BorderPane();
        root.setBackground(Background.fill(Color.valueOf("#1f1e33")));
        root.setTop(liveBox);
        root.setCenter(renderer.getMapPane());
        root.setRight(powerUpBar);
        root.setBottom(typingBox);

        // Setup stage
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("EchoShift Integration Test");
        stage.setMinWidth(800);
        stage.setMinHeight(700);
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
        loadNight();
    }

    private VBox createPowerUpBox(Image image) {
        ImageView powerUpImage = new ImageView(image);
        Button powerUpButton = new Button();
        powerUpButton.setGraphic(powerUpImage);
        Label powerUpLabel = new Label("1");
        VBox powerUpVBox = new VBox(powerUpButton, powerUpLabel);
        powerUpButton.setStyle("""
                -fx-background-color: #FFFFFF70;
                """);
        powerUpVBox.setAlignment(Pos.CENTER);
        powerUpVBox.setStyle("""
                -fx-background-color: #FFFFFF70;
                -fx-font-size: 18;
                -fx-cursor: hand;
                -fx-border-color: white;
                -fx-border-width: 1;
                -fx-text-alignment: center;
                """);
        return powerUpVBox;
    }

    private HBox handleTyping() {
        HBox typingBox = new HBox(40, wordLabel, typedLabel, statusLabel);
        wordLabel.setAlignment(Pos.CENTER_LEFT);
        wordLabel.setStyle("""
                -fx-font-size: 28;
                -fx-background-size: 100, 500;
                -fx-background-color: #FFFFFF70;
                -fx-padding: 10, 50, 5, 5;
                """);
        typedLabel.setStyle("""
                -fx-font-size: 28;
                -fx-background-size: 100, 500;
                -fx-background-color: #FFFFFF70;
                -fx-padding: 10, 50, 5, 5;
                """);
        statusLabel.setStyle("""
                -fx-font-size: 28;
                -fx-background-size: 100, 500;
                -fx-background-color: #FFFFFF70;
                -fx-padding: 10, 50, 5, 5;
                """);
        typedLabel.setAlignment(Pos.CENTER_RIGHT);
        typingBox.setAlignment(Pos.CENTER);
        return typingBox;
    }

    private void loadNight() {
        Night currentNight = new Night(3, entity, renderer);
        currentNight.setOnHealthDecrease(() -> {
            int health = currentNight.getHealth();
            updateHearts(health);
            flashBackground(Color.valueOf("#8B0000"));
            if (health <= 0) {
                statusLabel.setText("GAME OVER - You died!");
            }
        });
        currentNight.setOnHealthIncrease(() -> {
            int health = currentNight.getHealth();
            updateHearts(health);
            flashBackground(Color.valueOf("#008B00"));
        });
        currentNight.start();

        stage.setOnCloseRequest(_ -> currentNight.stopNight());
    }

    private void handleTyping(String character) {
        statusLabel.setText("");
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
            if (score > 300){
                score -= 300;
            } else {
                score = 0;
            }
            Platform.runLater(() -> {
               scoreLabel.setText("Score: " + score);
            });
            startPause();
        }

        if (result.isWordCompleted()) {
            if (placingLure) {
                placeLure(selectedNode);
                statusLabel.setText("Lure placed!");
                placingLure = false; // back to scan mode
            } else {
                performScan();
                statusLabel.setText("Scan performed!");
            }
            updateScore(engine);
            Platform.runLater(() -> {
                scoreLabel.setText("Score: " + score);
            });
            startPause();
        }
    }

    private void placeLure(int node) {
        List<Integer> adjacent = gameMap.getConnections(entity.getCurrentRoomId());
        for (Integer i : adjacent) {
            if (i == node) {
                System.out.println("Lure Placed at node " + node);
                entity.setCurrentRoom(node);
                renderer.updateEntityPosition(entity);
            }
        }
    }

    private void performScan() {
        renderer.scan();
        PauseTransition scan = new PauseTransition(Duration.seconds(2)); // adjust duration
        scan.setOnFinished(_ -> renderer.endScan());
        scan.play();
        System.out.println("Scan performed!");
    }

    private void startPause() {
        waitingForNextWord = true;

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(_ -> {
            typedLabel.setText("Typed: ");
            updateWordDisplay();
            waitingForNextWord = false;
        });
        pause.play();
    }

    private void updateHearts(int health) {
        for (int i = 0; i < heartArray.size(); i++) {
            heartArray.get(i).setVisible(i < health);
        }
    }

    private void flashBackground(Color color) {
        root.setBackground(Background.fill(color));
        // Revert after 500 ms
        PauseTransition flashTransition = new PauseTransition(Duration.millis(500));
        flashTransition.setOnFinished(e -> root.setBackground(Background.fill(Color.valueOf("#1f1e33"))));
        flashTransition.play();
    }

    private void updateScore(TypingEngine result) {
        this.score = (result.getChar()*100) - (result.getErrorCount()*300);
    }

    private void updateWordDisplay() {
        wordLabel.setText("Type word: " + engine.getCurrentWord());
    }

    public static void main(String[] args) {
        launch(args);
    }
}