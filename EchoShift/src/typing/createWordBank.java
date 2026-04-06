package typing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    public static String[] create(int nightDif) {
        String fileName;

        if (nightDif == 1){
            fileName = "/echoshift/text/easy.txt";
        } else if (nightDif == 2) {
            fileName = "/echoshift/text/medium.txt";
        } else {
            fileName = "/echoshift/text/hard.txt";
        }

        try (InputStream input = createWordBank.class.getResourceAsStream(fileName)) {

            if (input == null) {
                throw new RuntimeException("File not found: " + fileName);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            return reader.lines().toArray(String[]::new);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}