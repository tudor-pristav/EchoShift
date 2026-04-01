package echoshift.UI;

import echoshift.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 * This class creates the main player screen for the Echo Shift game.
 * <br><br>
 * @author Yasmine Suojhayer
 */
public class PlayerScreenView {
    /**
     * This is the constructor for the Player Screen.
     * It returns a BorderPane object that {@link App} uses to create the screen.
     * @return the Player Screen
     */
    public Parent createPlayerScreen() {
        // Create the background for the scene.
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #d9d9d9;");

        // This adds a blank bar to the top of the game for spacing.
        Pane topBar = new Pane();
        topBar.setPadding(new Insets(15, 20, 10, 20));
        topBar.setPrefHeight(80);
        topBar.setStyle("-fx-background-color: #f4f4f4;");
        root.setTop(topBar);

        // Creates a container for the menu buttons and the title.
        VBox leftPanel = new VBox(18);
        leftPanel.setAlignment(Pos.TOP_LEFT);
        leftPanel.setPadding(new Insets(0,0,0,5));

        // Creates a container for the buttons.
        VBox buttonBox = new VBox(18);
        buttonBox.setAlignment(Pos.TOP_LEFT);
        buttonBox.setPadding(new Insets(0, 0, 0, 20));

        // The "title" of the page.
        Label title = new Label("Ready for \nthe night shift?");
        title.setFont(Font.font("Arial", 55));
        title.setStyle("-fx-text-fill: black;");

        // Creates the menu buttons needed for the page.
        Button newGameButton = createMenuButton("New Game", 230, 55);
        Button selectLevelButton = createMenuButton("Select Level", 230, 55);
        Button instructionsButton = createMenuButton("Instructions", 230, 55);
        Button playerStatsButton = createMenuButton("Player Stats", 230, 55);
        Button settingsButton = createMenuButton("Settings", 230, 55);
        Button logoutButton = createMenuButton("Logout", 230, 55);

        // Sets the buttons with the proper screen transition.
        // TODO: replace with actual scene switching later
        newGameButton.setOnAction(e -> System.out.println("New Game clicked"));
        selectLevelButton.setOnAction(e -> System.out.println("Select Level clicked"));
        instructionsButton.setOnAction(e -> System.out.println("Instructions clicked"));
        playerStatsButton.setOnAction(e -> System.out.println("Player Stats clicked"));
        settingsButton.setOnAction(e -> System.out.println("Settings clicked"));
        logoutButton.setOnAction(e -> System.out.println("Logout clicked"));

        // Add buttons to the button container.
        buttonBox.getChildren().addAll(
                newGameButton,
                selectLevelButton,
                instructionsButton,
                playerStatsButton,
                settingsButton,
                logoutButton
        );

        // Add the title and button box to the left panel.
        leftPanel.getChildren().addAll(title, buttonBox);

        //Add the left panel to the scene.
        root.setCenter(leftPanel);

        // Create a container for the shop button and currency amount.
        VBox rightPanel = new VBox(18);
        rightPanel.setAlignment(Pos.TOP_RIGHT);
        rightPanel.setPadding(new Insets(20, 20, 20, 0));

        // Create a container for the currency and shop button.
        VBox currencyBox = new VBox(18);
        currencyBox.setPadding(new Insets(0,0,0,20));
        Label currency = new Label("$000000");
        currency.setFont(Font.font("Arial", 28));
        currency.setStyle("""
                        -fx-background-color: white;
                        -fx-text-fill: black;
                        -fx-border-width: 5px 5px 5px 5px;
                        """);
        currencyBox.getChildren().add(currency);

        // Create the shop button.
        Button shopButton = createMenuButton("Shop", 150, 55);
        // TODO: replace with actual scene switching later
        shopButton.setOnAction(e -> System.out.println("Shop clicked"));

        // Add the currency and shop button to the right panel.
        rightPanel.getChildren().addAll(currencyBox, shopButton);

        // Add the right panel to the scene.
        root.setRight(rightPanel);

        // Create the bottom bar.
        BorderPane bottomBar = new BorderPane();
        bottomBar.setPadding(new Insets(10, 20, 15, 20));
        bottomBar.setPrefHeight(50);
        bottomBar.setStyle("-fx-background-color: #f4f4f4;");

        // Create an exit button for the bottom bar.
        Button exitButton = createMenuButton("Exit", 110, 45);
        exitButton.setOnAction(e -> System.exit(0));

        // Add the exit button to the bottom bar.
        bottomBar.setRight(exitButton);

        // Add the bottom bar to the scene.
        root.setBottom(bottomBar);

        return root;
    }

    /**
     * This method creates the buttons for the page.
     * This ensures all buttons follow a consistent style.
     *
     * @param text The label for the button
     * @param width The width of the button
     * @param height The height of the button
     * @return a button object with the specified label, width, and height
     */
    private Button createMenuButton(String text, double width, double height) {
        Button button = new Button(text);
        button.setPrefSize(width, height);
        button.setFont(Font.font("Arial", 28));
        button.setStyle("""
                -fx-background-color: white;
                -fx-text-fill: black;
                -fx-border-color: #bdbdbd;
                -fx-border-width: 1;
                -fx-background-radius: 0;
                -fx-border-radius: 0;
                -fx-cursor: hand;
                """);
        return button;
    }
}

