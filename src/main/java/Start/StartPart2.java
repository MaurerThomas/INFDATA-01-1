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
    private StartPart2() {

    }

    public static void main(String[] args) {
        DataSetLoader dsl = new DataSetLoader();
        DataSetLoaderPart2 dataSetLoaderPart2 = new DataSetLoaderPart2();
        Map<Integer, Item> allItemsTreeMap = new TreeMap<>();
        Map<Integer, Item> allItemsTreeMap100k = new TreeMap<>();
        Map<Integer, User> allUsersTreeMap = new TreeMap<>();
        Map<Integer, User> allUsersTreeMap100k = new TreeMap<>();
        Logger logger = Logger.getLogger("myLogger");
        ItemRatingPredictor itemRatingPredictor = new ItemRatingPredictor();
        ItemDeviation itemDeviation = new ItemDeviation();

        try {
            allItemsTreeMap = dataSetLoaderPart2.loadDataSet("data/userItem.data", ",");
            allUsersTreeMap = dsl.loadDataSet("data/userItem.data", ",");
            allItemsTreeMap100k = dataSetLoaderPart2.loadDataSet("data/MovieLens100k/u.data", "\\t");
            allUsersTreeMap100k = dsl.loadDataSet("data/MovieLens100k/u.data", "\\t");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not init: ", e);
        }

        printQuestionNumber(6);
        itemDeviation.calculateItemDeviations(allItemsTreeMap);

        User targetUser = allUsersTreeMap.get(7);
        System.out.println("Predicted ratings for user 7");
        System.out.println("Predicted rating for item 101: " + itemRatingPredictor.predictRating(targetUser, 101, itemDeviation.getDeviationPerMovie()));
        System.out.println("Predicted rating for item 103: " + itemRatingPredictor.predictRating(targetUser, 103, itemDeviation.getDeviationPerMovie()));
        System.out.println("Predicted rating for item 106: " + itemRatingPredictor.predictRating(targetUser, 106, itemDeviation.getDeviationPerMovie()));

        targetUser = allUsersTreeMap.get(3);
        System.out.println("Predicted ratings for user 3");
        System.out.println("Predicted rating for item 103: " + itemRatingPredictor.predictRating(targetUser, 103, itemDeviation.getDeviationPerMovie()));
        System.out.println("Predicted rating for item 105: " + itemRatingPredictor.predictRating(targetUser, 105, itemDeviation.getDeviationPerMovie()));

        printQuestionNumber(8);
        System.out.println("User 3 rates item 105 with 4.0");

        Item item105 = allItemsTreeMap.get(105);
        itemDeviation.addRating(3, 4.0f, item105, allItemsTreeMap);
        targetUser = allUsersTreeMap.get(7);
        System.out.println("Updated predicted rating for item 101: " + itemRatingPredictor.predictRating(targetUser, 101, itemDeviation.getDeviationPerMovie()));
        System.out.println("Updated predicted rating for item 103: " + itemRatingPredictor.predictRating(targetUser, 103, itemDeviation.getDeviationPerMovie()));
        System.out.println("Updated predicted rating for item 106: " + itemRatingPredictor.predictRating(targetUser, 106, itemDeviation.getDeviationPerMovie()));

        printQuestionNumber(10);
        Map<Integer, Map<Integer, ItemDeviation>> deviationPerMovie100k = itemDeviation.calculateItemDeviations(allItemsTreeMap100k);
        targetUser = allUsersTreeMap100k.get(186);
        long time = System.nanoTime();

        Map<Integer, Double> topNRatings = itemRatingPredictor.getTopNRatings(targetUser, deviationPerMovie100k, allItemsTreeMap100k, 5);
        time = System.nanoTime() - time;
        System.out.println(topNRatings);
        System.out.println("Calculation time Top N: " + (time / 1000000) + "ms");
    }

    private static void printQuestionNumber(int questionNumber) {
        String seperator = "----------------------------";
        System.out.println(seperator);
        System.out.println("Part 2: question " + questionNumber);
        System.out.println(seperator);
    }
}


