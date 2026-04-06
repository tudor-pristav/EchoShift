package echoshift;

/**
 * Contains the required music files (in MP3 or WAV)
 * as enumerations and is utilized by MusicManager class.
 *
 * @author Matthew Taylor
 */
public enum MusicEffectType {

    // Main menu music track
    MAIN_MENU("Anxious-Sextile.mp3");

    /** Filename of music track */
    private final String filename;

    /**
     * MusicEffectType constructor. Initializes filename.
     *
     * @param filename the name of the music track.
     */
    MusicEffectType(String filename) {
        this.filename = filename;
    }

    /**
     * Retrieves the filename.
     *
     * @return filename as a String.
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Gets the extension of file (either MP3 or WAV) and
     * returns it. Returns "" for a failed get mission.
     *
     * @return extension of file.
     */
    public String getFileExtension() {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }
}
