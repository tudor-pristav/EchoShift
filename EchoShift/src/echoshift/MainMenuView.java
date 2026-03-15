package echoshift;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class MainMenuView {

    public Parent createMainMenu() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #d9d9d9;");

        // Top bar with close button
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_RIGHT);
        topBar.setPadding(new Insets(15, 20, 10, 20));
        topBar.setPrefHeight(60);
        topBar.setStyle("-fx-background-color: #f4f4f4;");

        Button closeButton = new Button("X");
        closeButton.setFont(Font.font("Arial", 20));
        closeButton.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: black;
                -fx-cursor: hand;
                """);
        closeButton.setOnAction(e -> System.exit(0));

        topBar.getChildren().add(closeButton);
        root.setTop(topBar);

        // Center content
        VBox centerBox = new VBox(18);
        centerBox.setAlignment(Pos.CENTER);

        Label title = new Label("Echo Shift");
        title.setFont(Font.font("Arial", 64));
        title.setStyle("-fx-text-fill: black;");

        Button loginButton = createMenuButton("Login", 230, 55);
        Button instructionsButton = createMenuButton("Instructions", 230, 55);
        Button highScoresButton = createMenuButton("High Scores", 230, 55);
        Button adminLoginButton = createMenuButton("Admin Login", 230, 55);
        Button settingsButton = createMenuButton("Settings", 180, 50);

        // TODO: replace with actual scene switching later
        loginButton.setOnAction(e -> System.out.println("Login clicked"));
        instructionsButton.setOnAction(e -> System.out.println("Instructions clicked"));
        highScoresButton.setOnAction(e -> System.out.println("High Scores clicked"));
        adminLoginButton.setOnAction(e -> System.out.println("Admin Login clicked"));
        settingsButton.setOnAction(e -> System.out.println("Settings clicked"));

        centerBox.getChildren().addAll(
                title,
                loginButton,
                instructionsButton,
                highScoresButton,
                adminLoginButton,
                settingsButton
        );

        root.setCenter(centerBox);

        // Bottom bar with exit button and timer
        BorderPane bottomBar = new BorderPane();
        bottomBar.setPadding(new Insets(10, 20, 15, 20));
        bottomBar.setPrefHeight(80);
        bottomBar.setStyle("-fx-background-color: #f4f4f4;");

        Button exitButton = createMenuButton("Exit", 110, 45);
        exitButton.setOnAction(e -> System.exit(0));

        Label timerLabel = new Label("TEST");
        timerLabel.setFont(Font.font("Arial", 28));
        timerLabel.setStyle("-fx-text-fill: black;");

        bottomBar.setRight(exitButton);
        bottomBar.setBottom(timerLabel);
        BorderPane.setAlignment(timerLabel, Pos.BOTTOM_RIGHT);

        root.setBottom(bottomBar);

        return root;
    }

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