package datastructure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataSetLoader {
    /**
     * Load the dataset from a datafile.
     *
     * @return A map with the userId and User object.
     * @throws IOException
     */
    public Map<Integer, User> loadDataSet(String dataset, String seperator) throws IOException {
        // Make our logger to log stuff.
        Logger logger = Logger.getLogger("myLogger");
        // Make our TreeMap with a list as values.
        Map<Integer, User> userMap = new TreeMap<>();
        // Create a new file.
        System.getProperty("user.dir");
        File file = new File(dataset);
        // Go through each line and create new users with preferences.
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] details = line.split(seperator);
                int userId = Integer.parseInt(details[0]);
                int movieId = Integer.parseInt(details[1]);
                float rating = Float.parseFloat(details[2]);
                // If user does not exist then add preferences.
                if (!userMap.containsKey(userId)) {
                    Map<Integer, Float> userRatings = new TreeMap<>();
                    userRatings.put(movieId, rating);
                    User newUser = new User(userId, userRatings);
                    userMap.put(userId, newUser);
                } else {
                    User existingUser = userMap.get(userId);
                    existingUser.addMovieRating(movieId, rating);
                }
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "File not found", e);
        }
        return userMap;
    }
}