package echoshift.nightscripts;

import echoshift.UI.MapRenderer;
import echoshift.backend.Entity;
import echoshift.backend.GameMap;

import echoshift.models.Session;
import echoshift.models.UserStatistics;
import echoshift.services.UserDataRetrievalService;
import echoshift.services.UserDataSaveService;
import typing.TypingEngine;
import typing.TypingResult;
import typing.createWordBank;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the main gameplay for the Echo Shift project.
 *
 * @author Ho Long Adrian Lee
 * @author Bob Zhang
 * @author Yasmine Suojhayer
 */
public class MainGameplay extends Application {
    private Stage stage;

    private GameMap gameMap; // Object that stores the map layout and the location of the entity.
    private TypingEngine engine; // Object that handles the loading of new words to type, checks accuracy, and records overall stats.
    private Night currentNight; // Represents the current night object, containing the time elapsed and health of the player.
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

    private final ArrayList<ImageView> heartArray = new ArrayList<>();
    private final Image entityImage = new Image("/echoshift/images/entity-tracker.gif", 50, 50, false, false);
    private final Image heart = new Image("/echoshift/images/live.png", 50, 50, false, false);
    private final Image instantHealth = new Image("/echoshift/images/instant-health.png", 50, 50, false, false);
    private final Image lure = new Image("/echoshift/images/instant-lure-icon.png", 50, 50, false, false);
    private final Image easyWord = new Image("/echoshift/images/easier-words-icon.png", 50, 50, false, false);

    private Label scoreLabel;
    private Label hour;

    private UserStatistics stats;

    private int nightNumber;

    private final Session session;

    /**
     * Method the triggers the start of the night, and maintains buttons, stats, ann in game actions.
     *
     * @param nightNumber The stage on which the night will be displayed.
     * @throws IOException If the method is unable to receive input from the player, the method will
     * throw an IOException.
     */
    public MainGameplay(int nightNumber,Session session){
        this.nightNumber = nightNumber;
        this.session = session;
    }
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        gameMap = new GameMap();

        // Example word list
        engine = new TypingEngine(createWordBank.create(nightNumber));
        // Start night
        loadNight(nightNumber);
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

        // Typing UI
        HBox typingBox = handleTyping();

        // Power-up buttons
        VBox powerUpBar = new VBox();
        powerUpBar.setAlignment(Pos.CENTER_LEFT);
        VBox instantHealthVBox = createPowerUpBox(instantHealth,session.getPowerUps().getExtraLife());
        VBox easierWordsVBox = createPowerUpBox(easyWord,session.getPowerUps().getEasyWords());
        VBox instantLureVBox = createPowerUpBox(lure,session.getPowerUps().getInstantLure());
        powerUpBar.getChildren().addAll(instantHealthVBox, instantLureVBox, easierWordsVBox);
        powerUpBar.setSpacing(20);

        //Score display
        scoreLabel = new Label("Score: " + score);
        scoreLabel.setStyle("""
                -fx-background-color: #FFFFFF70;
                -fx-font-size: 18;
                -fx-cursor: hand;
                -fx-border-color: white;
                -fx-border-width: 1;
                -fx-text-alignment: center;
                """);
        //Hour display
        hour = new Label(currentNight.getCurrentHour() + "AM");
        hour.setStyle("""
                -fx-background-color: #FFFFFF70;
                -fx-font-size: 18;
                -fx-cursor: hand;
                -fx-border-color: white;
                -fx-border-width: 1;
                -fx-text-alignment: CENTER;
                -fx-alighnment: RIGHT;
                """);

        currentNight.setOnHourChange(() -> Platform.runLater(() -> hour.setText(currentNight.getCurrentHour() + "AM")));

        //Display both time and score in the top right.
        VBox rightCorner = new VBox(scoreLabel, hour);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topBar = new HBox(10, liveBox, spacer, rightCorner);
        topBar.setAlignment(Pos.CENTER_LEFT);


        // Assemble root
        root = new BorderPane();
        root.setBackground(Background.fill(Color.valueOf("#1f1e33")));
        root.setTop(topBar);
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


        //Setup powerup buttons and their functionality
        instantHealthVBox.setOnMouseClicked(_ -> this.currentNight.addHealth());
        easierWordsVBox.setOnMouseClicked(_ -> {
            try {
                engine.changeWordBank(1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        instantLureVBox.setOnMouseClicked(_ -> currentNight.instantLure());
    }


    /**
     * Method that generates buttons for powerups using a given image.
     *
     * @param image Image that represents the current powerup that will display on screen.
     * @return A VBox object that will be displayed on the button.
     */
    private VBox createPowerUpBox(Image image, int no) {
        ImageView powerUpImage = new ImageView(image);

        //Number of a specific powerup a user has on hand.
        Label powerUpLabel = new Label(String.valueOf(no));
        VBox powerUpVBox = new VBox(powerUpImage, powerUpLabel);

        //Format powerup buttons.
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

    /**
     * Method that generates backgrounds that will be overlaid by typing functionalities.
     *
     * @return An HBox object that will represent the typing interface for the user.
     */
    private HBox handleTyping() {
        //Format the HBox to be returned that handles the interface for typing.
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

    /**
     * Method that creates a new night object with set parameters, and handles game ending events.
     */
    private void loadNight(int nightNumber) {
        //Remember to change the first parameter to a variable that matches the current night
        currentNight = new Night(nightNumber, entity, renderer);

        // TODO: Fix stat saves.
        currentNight.setOnNightEnd(() -> {
            stats = new UserStatistics();
            stats.setGamesPlayed();

            stats.setAverageWPM(engine.calculateWPM());
            stats.setPeakWPM(engine.calculateWPM());
            stats.setAccuracy(engine.calculateAccuracy());
            stats.setErrorCount(engine.getErrorCount());
            stats.setTotalTimePlayed(currentNight.getCurrentHour());
            stats.setHighScore(score);
            stats.setHighestLevel(currentNight.getNightNum());
            stats.setCoins(score/1000);
            UserDataSaveService save = new UserDataSaveService();
            try {
                save.saveStatistics(session.getCurrentUser().getId(), stats);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        //Both visual and in game statistics adjustments when a player's health changes.
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

        //Properly ends the night to avoid errors.
        stage.setOnCloseRequest(_ -> currentNight.stopNight());
    }

    /**
     * Method handles the typing aspect of the game, including getting new words to type, checking
     * the accuracy of the word, adjusting the score, and events that take place after completing a word.
     *
     * @param character Represents the word that will be loaded onto the game for the player to type.
     */
    private void handleTyping(String character) {
        statusLabel.setText("");
        if (waitingForNextWord) return;

        //Prepare the next correct character that should be typed by the player.
        char c = character.charAt(0);
        TypingResult result = engine.inputChar(c);

        typedLabel.setText(typedLabel.getText() + c);

        //Check if each character is input correctly and display each check's result.
        if (result.isCorrect())
            statusLabel.setText("Correct");
        else
            statusLabel.setText("Incorrect");

        //Adjust night score on the mistyping of a word.
        if (result.isWordFailed()) {
            statusLabel.setText("WORD FAILED");
            if (score > 300){
                score -= 300;
            } else {
                score = 0;
            }
            Platform.runLater(() -> scoreLabel.setText("Score: " + score));
            //Reload a new word to be typed.
            startPause();
        }

        //Adjust night score on the completion of a word.
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
            Platform.runLater(() -> scoreLabel.setText("Score: " + score));
            //Reload new word to be typed.
            startPause();
        }
    }

    /**
     *  The method will check if the entity is next to the selected node, and will set the entity's position
     *  to that node if the entity is in a node adjacent to the current node.
     *  @param node The node that the entity will be lured to, if the entity is in a node adjacent to this one.
     */
    private void placeLure(int node) {
        //Retrieve all nodes adjacent to the node the entity is currently at.
        List<Integer> adjacent = gameMap.getConnections(entity.getCurrentRoomId());
        for (Integer i : adjacent) {
            //Check if any of the nodes adjacent to the entity are the ones that the user wishes to lure to.
            //If an adjacent node meets this criteria, the entity is move to this node.
            if (i == node) {
                System.out.println("Lure Placed at node " + node);
                entity.setCurrentRoom(node);
                renderer.updateEntityPosition(entity);
            }
        }
    }

    /**
     *  Method will reveal the entity's position for 2 seconds on the map when called.
     */
    private void performScan() {
        renderer.scan();
        PauseTransition scan = new PauseTransition(Duration.seconds(2));
        scan.setOnFinished(_ -> renderer.endScan());
        scan.play();

        System.out.println("Scan performed!");
    }

    /**
     *  Method temporarily stops the typing functionalities in order to display a new word on screen and reset the typing user interface.
     */
    private void startPause() {
        waitingForNextWord = true;
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        //Refresh the interface with a new word to be type, and clear the typing box.
        pause.setOnFinished(_ -> {
            typedLabel.setText("Typed: ");
            updateWordDisplay();
            waitingForNextWord = false;
        });
        pause.play();
    }

    /**
     * Method displays the number of hearts the player will see on screen, with each heart representing a life.
     *
     * @param health The number of lives the player has remaining.
     */
    private void updateHearts(int health) {
        for (int i = 0; i < heartArray.size(); i++) {
            heartArray.get(i).setVisible(i < health);
        }
    }

    /**
     *Method temporarily changes the background colour to reflect the event of damage or healing the player receives.
     *
     * @param color Represents the colour that will be temporarily displayed on the background.
     */
    private void flashBackground(Color color) {
        root.setBackground(Background.fill(color));
        // Revert background to normal after 500 ms.
        PauseTransition flashTransition = new PauseTransition(Duration.millis(500));
        flashTransition.setOnFinished(_ -> root.setBackground(Background.fill(Color.valueOf("#1f1e33"))));
        flashTransition.play();
    }

    /**
     * Updates the score for the current word.
     *
     * @param result Represents the word encapsulated by the TypingEngine object
     * that was typed by the player.
     */
    private void updateScore(TypingEngine result) {
        this.score = (result.getChar()*100) - (result.getErrorCount()*300);
    }

    /**
     * Displays the current word for the user to type.
     */
    private void updateWordDisplay() {
        wordLabel.setText("Type word: " + engine.getCurrentWord());
    }

    public static void main(String[] args) {
        launch(args);
    }
}