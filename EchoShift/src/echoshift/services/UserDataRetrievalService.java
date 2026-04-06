package echoshift.services;

import com.google.gson.*;
import echoshift.models.UserStatistics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Service for retrieving user statistics from JSON files.
 *
 * @author Tudor Mihai Pristav
 */
public class UserDataRetrievalService {
    private static final String IncompletePath = "data/playerData";

    /**
     * Builds the file path for a user's statistics.
     *
     * @param id user ID
     * @return file path
     */
    private String pathConstructor(String id) {
        return IncompletePath + "/" + id + ".json";
    }

    /**
     * Retrieves statistics for a given user ID.
     *
     * @param id user ID
     * @return user statistics
     */
    public UserStatistics retrieveStatistics(String id) {
        String statisticsPath = pathConstructor(id);
        return loadStatistics(statisticsPath);
    }

    /**
     * Loads statistics from a JSON file.
     *
     * @param path file path
     * @return user statistics
     */
    public UserStatistics loadStatistics(String path) {
        try {
            String json = Files.readString(Paths.get(path));

            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
            JsonObject statObj = obj.getAsJsonObject("statistics");

            return new Gson().fromJson(statObj, UserStatistics.class);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load statistics from file", e);
        }
    }
}