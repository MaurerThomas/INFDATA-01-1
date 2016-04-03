package prediction;

import datastructure.Item;
import datastructure.User;

import java.util.*;

public class ItemRatingPredictor {

    public double predictedRating(User user, int itemId, Map<Integer, Map<Integer, ItemDeviation>> deviationPerMovie) {
        double numerator = 0;
        double denominator = 0;
        Map<Integer, ItemDeviation> deviations = deviationPerMovie.get(itemId);

        for(Map.Entry<Integer, Float> userRatingEntry : user.getRatings().entrySet()) {
            int itemJId = userRatingEntry.getKey();
            float userRatingForJ = userRatingEntry.getValue();

            ItemDeviation deviationBetweenIAndJ = deviations.get(itemJId);
            double dev = deviationBetweenIAndJ.getDeviation();
            int numberOfUsers = deviationBetweenIAndJ.getNumberOfUsers();

            numerator += (userRatingForJ + dev) * numberOfUsers;
            denominator += numberOfUsers;
        }

        return numerator / denominator;
    }


    public Map<Integer, Double> getTopNRatings(User user, Map<Integer, Map<Integer, ItemDeviation>> deviationPerMovie, Map<Integer, Item>allItemsTreeMap , int maxRatings) {
        Map<Integer, Double> tempPredictedRatingMap = new TreeMap<>();
        Map<Integer, Double> predictedRatingMap = new TreeMap<>();
        List<Integer> listOfNotRatedItems = new ArrayList<>();

        //Get user's rated items
        Set<Integer> userItemSet =  user.getRatings().keySet();
        //Get all items that the user has NOT rated.
        Set<Integer> allItemSet = allItemsTreeMap.keySet();

        for(int itemId : allItemSet){
            if(!userItemSet.contains(itemId)){
                listOfNotRatedItems.add(itemId);
            }
        }

        for (int itemId: listOfNotRatedItems) {
            double predictedRating = predictedRating(user, itemId, deviationPerMovie);
            tempPredictedRatingMap.put(itemId, predictedRating);
        }

        tempPredictedRatingMap =  TreeMapSorter.sortByValue(tempPredictedRatingMap);

        int count = 0;
        for(Map.Entry<Integer, Double> tempPredictedRatingEntry : tempPredictedRatingMap.entrySet()) {
            if(count < maxRatings) {
                predictedRatingMap.put(tempPredictedRatingEntry.getKey(), tempPredictedRatingEntry.getValue());

                count++;
            }
        }

        return predictedRatingMap;
    }
}
