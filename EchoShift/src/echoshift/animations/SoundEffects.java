package echoshift.animations;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

/**
 * Utility class for playing UI sound effects.
 */
public class SoundEffects {

    private static final String CLICK_SOUND_PATH = "/echoshift/sounds/click_004.mp3";

    /**
     * Plays the button click sound once.
     */
    public static void playClickSound() {
        URL soundUrl = SoundEffects.class.getResource(CLICK_SOUND_PATH);

        Media media = new Media(soundUrl.toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setVolume(1);

        mediaPlayer.setOnEndOfMedia(mediaPlayer::dispose);
        mediaPlayer.play();
    }
}