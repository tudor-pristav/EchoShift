package echoshift.UI;

import echoshift.animations.ButtonEffects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 * Builds the Settings screen for Echo Shift.
 * This class is responsible only for the frontend layout and UI elements.
 * @author Bob Zhang
 * @author Tudor Pristav
 */
public class SettingsView {

    private final Button backButton;
    private final Slider volumeSlider;
    private final CheckBox keyboardNavigationCheckBox;
    private final ComboBox<String> fontSizeComboBox;

    /**
     * Creates reusable controls for the settings page.
     */
    public SettingsView() {
        this.backButton = createButton("Back", 200, 42);

        this.volumeSlider = new Slider(0, 100, 100);
        this.keyboardNavigationCheckBox = new CheckBox("Enable");

        this.fontSizeComboBox = new ComboBox<>();
        this.fontSizeComboBox.getItems().addAll("50%", "100%", "150%", "200%");
        this.fontSizeComboBox.setValue("100%");
    }

    /**
     * Builds and returns the full Settings screen.
     *
     * @return the root node for this screen
     */
    public Parent createSettingsPage() {
        BorderPane root = createRootLayout();
        root.getStylesheets().add(
                getClass().getResource("/echoshift/styles/buttonStyle.css").toExternalForm()
        );

        Label title = createLabel("Settings", 50);
        VBox settingsBox = createSettingsBox();

        VBox centerContent = new VBox(20, title, settingsBox);
        centerContent.setAlignment(Pos.CENTER);

        StackPane centerWrapper = new StackPane(centerContent);
        centerWrapper.setPadding(new Insets(40));

        root.setCenter(centerWrapper);

        return root;
    }

    /**
     * Creates the root layout with a background image, top bar, and bottom bar.
     *
     * @return the root BorderPane
     */
    private BorderPane createRootLayout() {
        BorderPane root = new BorderPane();

        BackgroundImage bg = new BackgroundImage(
                new Image(getClass().getResource("/echoshift/images/bg2.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
        );

        root.setBackground(new Background(bg));

        HBox topBar = new HBox();
        topBar.setPrefHeight(60);
        topBar.getStyleClass().add("top-bar");
        root.setTop(topBar);

        BorderPane bottom = new BorderPane();
        bottom.getStyleClass().add("bottom-bar");
        bottom.setPrefHeight(60);
        bottom.setMinHeight(60);
        bottom.setPadding(new Insets(10, 18, 10, 18));

        bottom.setLeft(backButton);
        BorderPane.setAlignment(backButton, Pos.CENTER_LEFT);

        root.setBottom(bottom);

        return root;
    }

    /**
     * Creates the main settings content box.
     *
     * @return the settings container
     */
    private VBox createSettingsBox() {
        VBox settingsBox = new VBox(22);
        settingsBox.setAlignment(Pos.CENTER_LEFT);
        settingsBox.setMaxWidth(750);
        settingsBox.setPadding(new Insets(60));
        settingsBox.setSpacing(20);
        settingsBox.getStyleClass().add("container");

        HBox volumeRow = createVolumeRow();
        HBox keyboardRow = createKeyboardRow();
        HBox fontSizeRow = createFontSizeRow();

        settingsBox.getChildren().addAll(volumeRow, keyboardRow, fontSizeRow);

        return settingsBox;
    }

    /**
     * Creates the volume setting row.
     *
     * @return volume row
     */
    private HBox createVolumeRow() {
        Label volumeLabel = createSettingLabel("Volume");

        VBox sliderBox = new VBox(6);
        sliderBox.setAlignment(Pos.CENTER_LEFT);

        volumeSlider.setPrefWidth(330);

        HBox sliderLabels = new HBox();
        sliderLabels.setPrefWidth(220);

        Label minLabel = new Label("0%");
        Label maxLabel = new Label("100%");
        minLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
        maxLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        sliderLabels.getChildren().addAll(minLabel, spacer, maxLabel);
        sliderBox.getChildren().addAll(volumeSlider, sliderLabels);

        HBox row = new HBox(45, volumeLabel, sliderBox);
        row.setAlignment(Pos.CENTER_LEFT);

        return row;
    }

    /**
     * Creates the keyboard navigation setting row.
     *
     * @return keyboard navigation row
     */
    private HBox createKeyboardRow() {
        Label keyboardLabel = createSettingLabel("Keyboard Navigation");

        keyboardNavigationCheckBox.setStyle(
                "-fx-text-fill: black; -fx-font-size: 15px; -fx-font-weight: bold;"
        );

        HBox row = new HBox(30, keyboardLabel, keyboardNavigationCheckBox);
        row.setAlignment(Pos.CENTER_LEFT);

        return row;
    }

    /**
     * Creates the font size setting row.
     *
     * @return font size row
     */
    private HBox createFontSizeRow() {
        Label fontSizeLabel = createSettingLabel("Font Size");

        fontSizeComboBox.setPrefWidth(160);
        fontSizeComboBox.setStyle(
                "-fx-font-size: 14px;"
        );

        HBox row = new HBox(30, fontSizeLabel, fontSizeComboBox);
        row.setAlignment(Pos.CENTER_LEFT);

        return row;
    }

    /**
     * Creates a styled page title label.
     *
     * @param text the label text
     * @param size the font size
     * @return the label
     */
    private Label createLabel(String text, double size) {
        Label label = new Label(text);
        label.setFont(Font.font("Verdana", size));
        label.setStyle("-fx-text-fill: black;");
        return label;
    }

    /**
     * Creates a styled setting label.
     *
     * @param text the label text
     * @return the label
     */
    private Label createSettingLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Verdana", 20));
        label.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        label.setMinWidth(260);
        return label;
    }

    /**
     * Creates a button styled through CSS.
     *
     * @param text the button text
     * @param width preferred width
     * @param height preferred height
     * @return the styled button
     */
    private Button createButton(String text, double width, double height) {
        Button button = new Button(text);
        button.setPrefSize(width, height);
        button.setFont(Font.font("Verdana", 20));
        button.getStyleClass().add("button");

        ButtonEffects.hoverAnimation(button);
        ButtonEffects.clickAnimation(button);

        return button;
    }

    public Button getBackButton() {
        return backButton;
    }

    public Slider getVolumeSlider() {
        return volumeSlider;
    }

    public CheckBox getKeyboardNavigationCheckBox() {
        return keyboardNavigationCheckBox;
    }

    public ComboBox<String> getFontSizeComboBox() {
        return fontSizeComboBox;
    }
}