package echoshift.controllers;

import echoshift.UI.SettingsView;
import echoshift.animations.SoundEffects;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * Controller for the Settings page.
 * Manages volume adjustments and navigation.
 *
 * @author Tudor Mihai Pristav
 */
public class SettingsController {

    private final Stage stage;
    private final Parent previousRoot;
    private final SettingsView view;

    /**
     * Initializes the controller and sets up handlers.
     *
     * @param stage main application stage
     * @param previousRoot previous UI root for navigation
     * @param view settings view
     */
    public SettingsController(Stage stage, Parent previousRoot, SettingsView view) {
        this.stage = stage;
        this.previousRoot = previousRoot;
        this.view = view;

        initializeValues();
        registerHandlers();
    }

    /**
     * Sets initial UI values based on current settings.
     */
    private void initializeValues() {
        double currentVolume = SoundEffects.getVolume();
        view.getVolumeSlider().setValue(currentVolume * 100);
    }

    /**
     * Attaches event handlers for UI interactions.
     */
    private void registerHandlers() {
        view.getBackButton().setOnAction(e -> stage.getScene().setRoot(previousRoot));

        view.getVolumeSlider().valueProperty().addListener((obs, oldVal, newVal) -> {
            double volume = newVal.doubleValue() / 100.0;
            SoundEffects.setVolume(volume);
        });
    }
}