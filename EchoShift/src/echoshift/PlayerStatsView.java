package echoshift;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class PlayerStatsView {

    public Parent createPlayerStats() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #d9d9d9;");

        // This adds a blank bar to the top of the game for spacing.
        Pane topBar = new Pane();
        topBar.setPadding(new Insets(15, 20, 10, 20));
        topBar.setPrefHeight(80);
        topBar.setStyle("-fx-background-color: #f4f4f4;");
        root.setTop(topBar);

        // Center content
        VBox centerBox = new VBox(18);
        centerBox.setAlignment(Pos.CENTER);


        VBox menuBox = new VBox(18);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setStyle("""
                -fx-background-color: white;
                """);
        menuBox.setMaxWidth(650);
        menuBox.setPadding(new Insets(20,20,20,20));

        centerBox.getChildren().add(menuBox);

        Label title = new Label("Player Stats");
        title.setFont(Font.font("Arial", 40));
        title.setStyle("-fx-text-fill: black;");

        // TODO: add in actual player stats scoreValue.setText(String.valueOf(player.getHighScore()));
        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(40);
        statsGrid.setVgap(12);
        statsGrid.setAlignment(Pos.CENTER);
        statsGrid.setStyle("""
                -fx-font-size: 28px;
                """);

        Label highestWPMLabel = new Label("Highest Words Per Minute");
        Label gamesValue = new Label("24");

        Label averageWPMLabel = new Label("Average Words Per Minute");
        Label scoreValue = new Label("15800");

        Label highestScoreLabel = new Label("Highest Score");
        Label winValue = new Label("72%");

        Label wordsTypedLabel = new Label("Words Typed");
        Label hoursValue = new Label("54");

        Label errorCountLabel = new Label("Error Count");
        Label errorCount = new Label("4");

        Label accuracyLabel = new Label("Accuracy");
        Label accuracy = new Label("93%");

        statsGrid.add(highestWPMLabel, 0, 0);
        statsGrid.add(gamesValue, 1, 0);

        statsGrid.add(averageWPMLabel, 0, 1);
        statsGrid.add(scoreValue, 1, 1);

        statsGrid.add(highestScoreLabel, 0, 2);
        statsGrid.add(winValue, 1, 2);

        statsGrid.add(wordsTypedLabel, 0, 3);
        statsGrid.add(hoursValue, 1, 3);

        statsGrid.add(errorCountLabel, 0, 4);
        statsGrid.add(errorCount, 1, 4);

        statsGrid.add(accuracyLabel, 0, 5);
        statsGrid.add(accuracy, 1, 5);

        menuBox.getChildren().addAll(
                title,
                statsGrid
        );

        root.setCenter(centerBox);

        BorderPane bottomBar = new BorderPane();
        bottomBar.setPadding(new Insets(10, 20, 15, 20));
        bottomBar.setPrefHeight(80);
        bottomBar.setStyle("-fx-background-color: #f4f4f4;");

        Button exitButton = createMenuButton("Exit", 110, 45);
        exitButton.setOnAction(e -> System.exit(0));
        bottomBar.setRight(exitButton);

        Button backButton = createMenuButton("Back", 180, 50);
        // TODO: replace with actual scene switching later
        backButton.setOnAction(e -> System.out.println("Back clicked"));
        bottomBar.setLeft(backButton);

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
        button.setFont(Font.font("Arial", 22));
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

