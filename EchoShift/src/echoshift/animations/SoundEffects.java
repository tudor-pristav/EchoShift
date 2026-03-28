package src.echoshift.animations;

import javafx.scene.media.AudioClip;


import java.net.URL;

/**
 * Utility class for playing UI sound effects.
 */
public class SoundEffects {

    private static final String CLICK_SOUND_PATH = "/echoshift/sounds/click_004.mp3";
    private static final AudioClip CLICK_SOUND;

    static {
        URL soundUrl = SoundEffects.class.getResource(CLICK_SOUND_PATH);

        if (soundUrl == null) {
            throw new IllegalStateException("Sound not found: " + CLICK_SOUND_PATH);
        }

        CLICK_SOUND = new AudioClip(soundUrl.toExternalForm());
        CLICK_SOUND.setVolume(0.7);
    }

    /**
     * Plays the button click sound once.
     */
    public static void playClickSound() {
        CLICK_SOUND.play();
    }
}