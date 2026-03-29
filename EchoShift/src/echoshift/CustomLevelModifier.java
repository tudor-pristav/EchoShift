package echoshift;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class CustomLevelModifier {

    private static final String FONT = "Arial";

    public Parent createModifier() {
        BorderPane root = new BorderPane();
        root.setStyle("""
                -fx-background-image: url('assets/bg2.png');
                -fx-background-size: cover;
                -fx-background-repeat: no-repeat;
                -fx-background-position: center;
                """);

        // Top Bar
        Label confirmationTitle = new Label("Are you sure, player_name?");
        confirmationTitle.setFont(Font.font(FONT, 28));
        confirmationTitle.setStyle("-fx-text-fill: white;");

        HBox titleBar = new HBox(confirmationTitle);
        titleBar.setAlignment(Pos.CENTER_LEFT);
        titleBar.setPadding(new Insets(25, 40, 15, 40));
        titleBar.setStyle("""
                -fx-background-color: #1f1e3388;\s
                -fx-padding: 25;\s
                """);

        // Center Panel
        VBox mainPanel = new VBox(20);
        mainPanel.setStyle("""
                -fx-background-color: #1f1e3388;
                -fx-background-radius: 12;
                -fx-padding: 25;
                """);
        mainPanel.setMaxWidth(720);
        mainPanel.setAlignment(Pos.CENTER);

        // Left: Map Preview
        VBox mapBox = new VBox(10);
        mapBox.setAlignment(Pos.CENTER);

        Label mapLabel = new Label("Custom");
        mapLabel.setFont(Font.font(FONT, 18));
        mapLabel.setStyle("-fx-text-fill: white;");

        StackPane mapPreview = new StackPane();
        mapPreview.setStyle("""
                -fx-background-color: #2a2a2a;
                -fx-border-color: #555;
                -fx-border-width: 3;
                -fx-background-radius: 8;
                """);
        mapPreview.setPrefSize(220, 220);

        Label placeholder = new Label("🖼");
        placeholder.setFont(Font.font(80));
        placeholder.setStyle("-fx-text-fill: #666;");
        mapPreview.getChildren().add(placeholder);

        mapBox.getChildren().addAll(mapLabel, mapPreview);

        // Right: Settings
        VBox settingsBox = new VBox(18);
        settingsBox.setAlignment(Pos.TOP_LEFT);

        Label settingsTitle = new Label("Settings");
        settingsTitle.setFont(Font.font(FONT, 18));
        settingsTitle.setStyle("-fx-text-fill: white;");

        HBox setting1 = createSettingRow("Modifiable setting 1", "Default");
        HBox setting2 = createSettingRow("Modifiable setting 2", "Default");

        settingsBox.getChildren().addAll(settingsTitle, setting1, setting2);

        HBox contentHBox = new HBox(40, mapBox, settingsBox);
        contentHBox.setAlignment(Pos.CENTER);

        mainPanel.getChildren().add(contentHBox);

        // Nav buttons
        Button backButton = createBottomButton("Back");
        Button playButton = createBottomButton("Play");

        HBox buttonBox = new HBox(20, backButton, playButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 10, 0));

        // Bottom bar
        Label timerLabel = new Label("2:43");
        timerLabel.setFont(Font.font(FONT, 22));
        timerLabel.setStyle("-fx-text-fill: white;");

        BorderPane bottomBar = new BorderPane();
        bottomBar.setStyle("-fx-background-color: #1f1e3350;");
        bottomBar.setPadding(new Insets(12, 40, 12, 40));
        bottomBar.setRight(timerLabel);
        BorderPane.setAlignment(timerLabel, Pos.CENTER_RIGHT);

        // Assembling
        VBox centerArea = new VBox(20, mainPanel, buttonBox);
        centerArea.setAlignment(Pos.CENTER);

        root.setTop(titleBar);
        root.setCenter(centerArea);
        root.setBottom(bottomBar);

        return root;
    }

    // Button creation helper
    private Button createBottomButton(String text) {
        Button btn = new Button(text);
        btn.setFont(Font.font(FONT, 18));
        btn.setPrefSize(180, 50);
        btn.setStyle("""
                -fx-background-color: #d6d6d6;
                -fx-text-fill: black;
                -fx-border-color: #aaaaaa;
                -fx-border-width: 1;
                -fx-background-radius: 8;
                -fx-cursor: hand;
                """);
        return btn;
    }

    // Setting row creation helper
    private HBox createSettingRow(String labelText, String buttonText) {
        // New HBox
        HBox row = new HBox(30);
        row.setAlignment(Pos.CENTER_LEFT);

        // Setting label
        Label label = new Label(labelText);
        label.setFont(Font.font(FONT, 17));
        label.setStyle("-fx-text-fill: white;");

        // Setting button
        Button btn = new Button(buttonText);
        btn.setFont(Font.font(FONT, 16));
        btn.setStyle("""
                -fx-background-color: #d6d6d6;
                -fx-text-fill: black;
                -fx-padding: 8 20;
                -fx-background-radius: 6;
                -fx-border-color: #555;
                -fx-border-width: 1;
                -fx-cursor: hand;
                """);
        btn.setPrefWidth(140);

        // Assemble
        row.getChildren().addAll(label, btn);
        return row;
    }
}