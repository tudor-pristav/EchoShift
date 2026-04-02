package echoshift.animations;

import javafx.scene.media.AudioClip;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


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

    private static final String MUSIC_PATH = "/echoshift/sounds/background.mp3";

    private static MediaPlayer mediaPlayer;
    private static boolean initialized = false;


    /**
     * Starts the background music if it is not already playing.
     * Safe to call multiple times.
     */
    public static void play() {
        if (!initialized) {
            initializePlayer();
        }

        if (mediaPlayer != null &&
                mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            mediaPlayer.play();
        }
    }

    /**
     * Pauses the background music.
     */
    public static void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    /**
     * Stops the background music and resets it to the beginning.
     */
    public static void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    /**
     * Sets the music volume from 0.0 to 1.0.
     */
    public static void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    /**
     * Creates the shared MediaPlayer once.
     */
    private static void initializePlayer() {
        URL musicUrl = SoundEffects.class.getResource(MUSIC_PATH);

        if (musicUrl == null) {
            System.out.println("Background music file not found: " + MUSIC_PATH);
            return;
        }

        Media media = new Media(musicUrl.toExternalForm());
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // loops forever
        mediaPlayer.setVolume(1);

        initialized = true;
    }
    /**
     * Returns the current music volume as a value between 0.0 and 1.0.
     *
     * @return current music volume
     */
    public static double getVolume() {
        if (mediaPlayer != null) {
            return mediaPlayer.getVolume();
        }
        return 1.0;
    }
}