package echoshift.services;

import com.google.gson.*;
import echoshift.models.UserStatistics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//reusable function with custom parameter UserID
//go through the player data
//identify the class
//return the class

public class UserDataRetrievalService {
    private static final String IncompletePath = "data/playerData";

    private String pathConstructor(String id) {
        return IncompletePath + "/" + id + ".json";
    }

    public UserStatistics retrieveStatistics(String id) {
        String statisticsPath = pathConstructor(id);
        return loadStatistics(statisticsPath);
    }

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

