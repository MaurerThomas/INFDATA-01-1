package datastructure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DataSetLoaderPart2 {
    /**
     * @param dataset   location and name of the dataset
     * @param separator separator character that is used in the dataset
     * @return A map with the itemId and Item object.
     * @throws IOException
     */
    public Map<Integer, Item> loadDataSet(String dataset, String separator) throws IOException {
        Logger logger = Logger.getLogger("myLogger");
        Map<Integer, Item> itemMap = new TreeMap<>();
        // Create a new file.
        System.getProperty("user.dir");
        File file = new File(dataset);

        // Go through each line and create new items with users and ratings.
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] details = line.split(separator);
                int userId = Integer.parseInt(details[0]);
                int itemId = Integer.parseInt(details[1]);
                float rating = Float.parseFloat(details[2]);

                // If user does not exist then add rating.
                if (!itemMap.containsKey(itemId)) {
                    Map<Integer, Float> itemUserRatings = new TreeMap<>();
                    itemUserRatings.put(userId, rating);

                    Item item = new Item(itemId, itemUserRatings);
                    itemMap.put(itemId, item);
                } else {
                    Item existingItem = itemMap.get(itemId);
                    existingItem.addUserRating(userId, rating);
                }
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "File not found", e);
        }
        return itemMap;
    }
}
