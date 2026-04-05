package echoshift.controllers;

import echoshift.UI.SettingsView;
import echoshift.animations.SoundEffects;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller for the settings page.
 * Handles settings interactions such as volume changes and navigation.
 */
public class SettingsController {

    private final Stage stage;
    private final Parent previousRoot;
    private final SettingsView view;

    /**
     * Creates the settings controller and wires up all event handlers.
     *
     * @param stage the main application stage
     * @param previousRoot the scene to return to when pressing back
     * @param view the settings view
     */
    public SettingsController(Stage stage, Parent previousRoot, SettingsView view) {
        this.stage = stage;
        this.previousRoot = previousRoot;
        this.view = view;

        initializeValues();
        registerHandlers();
    }

    /**
     * Initializes the controls with the current application settings.
     */
    private void initializeValues() {
        double currentVolume = SoundEffects.getVolume();   // 0.0 to 1.0
        view.getVolumeSlider().setValue(currentVolume * 100);
    }

    /**
     * Registers all event handlers for the settings page.
     */
    private void registerHandlers() {
        view.getBackButton().setOnAction(e ->  stage.getScene().setRoot(previousRoot));

        view.getVolumeSlider().valueProperty().addListener((obs, oldVal, newVal) -> {
            double volume = newVal.doubleValue() / 100.0;
            SoundEffects.setVolume(volume);
        });
    }
}