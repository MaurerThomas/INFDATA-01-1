package start;

import datastructure.DataSetLoader;
import datastructure.DataSetLoaderPart2;
import datastructure.Item;
import datastructure.User;
import prediction.ItemDeviation;
import prediction.ItemRatingPredictor;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartPart2 {
    private StartPart2(){

    }

    public static void main(String[] args) {
        DataSetLoader dsl = new DataSetLoader();
        DataSetLoaderPart2 dataSetLoaderPart2 = new DataSetLoaderPart2();
        Map<Integer, Item> allItemsTreeMap = new TreeMap<>();
        Map<Integer, Item> allItemsTreeMap100k = new TreeMap<>();
        Map<Integer, User> allUsersTreeMap = new TreeMap<>();
        Map<Integer, User> allUsersTreeMap100k = new TreeMap<>();
        Map<Integer, Map<Integer, ItemDeviation>> deviationPerMovie = new TreeMap<>();
        Logger logger = Logger.getLogger("myLogger");
        ItemRatingPredictor itemRatingPredictor = new ItemRatingPredictor();

        try {
            allItemsTreeMap = dataSetLoaderPart2.loadDataSet("data/userItem.data", ",");
            allUsersTreeMap = dsl.loadDataSet("data/userItem.data", ",");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not init: ", e);
        }

        for (Item itemI : allItemsTreeMap.values()) {
            Map<Integer, ItemDeviation> calculatedItemDeviations = new TreeMap<>();

            for (Item itemJ : allItemsTreeMap.values()) {
                ItemDeviation itemDeviation = new ItemDeviation();

                itemDeviation.calculateItemDeviation(itemI, itemJ);
                calculatedItemDeviations.put(itemJ.getItemId(), itemDeviation);
            }
            deviationPerMovie.put(itemI.getItemId(), calculatedItemDeviations);
        }

        User targetUser = allUsersTreeMap.get(3);
        double ratingkje = itemRatingPredictor.predictedRating(targetUser, 105, deviationPerMovie);
        System.out.println(ratingkje);
    }
}
