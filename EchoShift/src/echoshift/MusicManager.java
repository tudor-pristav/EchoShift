package echoshift;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;

/**
 * MusicManager controls playing and stopping music by
 * utilizing MusicEffectType.
 * <p>
 * It supports MP3 and WAV files using JavaFX MediaPlayer
 * and Java Sound API, respectively.
 * </p>
 *
 * @author Matthew Taylor
 */
public class MusicManager {

    /** Boolean indicating whether the music manager is muted.*/
    private boolean isMuted = false;

    /** A map holding preloaded WAV audio clips keyed by their MusicEffectType.*/
    private final Map<MusicEffectType, Clip> wavClips = new EnumMap<>(MusicEffectType.class);

    /** An instance of MediaPlayer. It plays MP3 music tracks.*/
    private MediaPlayer mediaPlayer;

    /** The music track that is currently playing.*/
    private MusicEffectType currentMusic;

    /**
     * MusicManager constructor. Initializes and
     * loads all music directly into the instance,
     * making an efficient system.
     */
    public MusicManager() {
        loadWavMusic();
    }

    /**
     * Loads WAV files into Clips for quick playback.
     */
    private void loadWavMusic() {
        for (MusicEffectType effect : MusicEffectType.values()) {
            if ("wav".equals(effect.getFileExtension())) {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResource("/music/" + effect.getFilename()));
                    clip.open(ais);
                    wavClips.put(effect, clip);
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    System.err.println("Failed to load WAV music: " + effect.getFilename());
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Play music track by MusicEffectType.
     * Uses JavaFX MediaPlayer for MP3 files,
     * Java Sound API for WAV files.
     *
     * @param effect the MusicEffectType representing the music track to play.
     */
    public void playMusic(MusicEffectType effect) {
        if (isMuted) return;

        stopMusic(); // Stop any currently playing music

        currentMusic = effect;
        String ext = effect.getFileExtension();

        if ("mp3".equals(ext)) {
            playMp3(effect.getFilename());
        } else if ("wav".equals(ext)) {
            playWav(effect);
        } else {
            System.err.println("Unsupported music format: " + effect.getFilename());
        }
    }

    /**
     * Plays music track (WAV). Loops continuously.
     *
     * @param effect the MusicEffectType whose WAV clip should be played.
     */
    private void playWav(MusicEffectType effect) {
        Clip clip = wavClips.get(effect);
        if (clip != null) {
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.err.println("WAV music clip not loaded: " + effect.name());
        }
    }

    /**
     * Plays music track (MP3). Loops continuously.
     *
     * @param filename the name of the music track.
     */
    private void playMp3(String filename) {
        try {
            URL resource = getClass().getResource("/music/" + filename);
            if (resource == null) {
                System.err.println("MP3 resource not found: " + filename);
                return;
            }

            // Stop and dispose previous mediaPlayer if exists
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            }

            Media media = new Media(resource.toExternalForm());
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely

            mediaPlayer.setOnError(() -> {
                System.err.println("MediaPlayer error: " + mediaPlayer.getError());
            });

            mediaPlayer.setOnEndOfMedia(() -> {
                System.out.println("Media ended, restarting...");
                mediaPlayer.seek(javafx.util.Duration.ZERO);
            });

            mediaPlayer.setOnReady(() -> {
                System.out.println("Media is ready. Duration: " + mediaPlayer.getMedia().getDuration().toSeconds() + " seconds");
                mediaPlayer.play();
            });

            // Remove direct play() call here to wait for ready event
            // mediaPlayer.play();

        } catch (Exception e) {
            System.err.println("Failed to play MP3: " + filename);
            e.printStackTrace();
        }
    }

    /**
     * Stops the current music that is playing.
     */
    public void stopMusic() {
        // Stop WAV clips
        if (currentMusic != null && "wav".equals(currentMusic.getFileExtension())) {
            Clip clip = wavClips.get(currentMusic);
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
        }

        // Stop MP3 media player
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

    /**
     * Sets state of music (muted or not).
     * When true, any music that is playing is stopped.
     *
     * @param muted playback allowed when set to false.
     */
    public void setMuted(boolean muted) {
        this.isMuted = muted;
        if (muted) {
            stopMusic();
        }
    }

    /**
     * Check to see if music manager is muted.
     *
     * @return true if muted, false if not muted.
     */
    public boolean isMuted() {
        return isMuted;
    }
}
