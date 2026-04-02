package typing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class is used to create an array of words from a text file.
 *
 * @author Yasmine Suojhayer
 */
public class createWordBank {

    /**
     * This method takes the difficulty of the night and create the needed array of words.
     *
     * @param nightDif The Night's difficulty setting.
     * @return The array of words.
     * @throws IOException If an error occurs when opening the file.
     */
    public static String[] create(int nightDif) throws IOException {
        String fileName;
        if (nightDif == 1){
            fileName = "EchoShift/easy.txt";
        } else if (nightDif == 2) {
            fileName = "EchoShift/medium.txt";
        } else {
            fileName = "EchoShift/hard.txt";
        }
        try{
            return Files.lines(Path.of(fileName)).toArray(String[]::new);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}