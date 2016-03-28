package prediction;

import datastructure.User;

import java.util.Map;

public class ItemRatingPredictor {

    public double predictedRating(User user, int itemId, Map<Integer, Map<Integer, ItemDeviation>> deviationPerMovie) {
        double numerator = 0;
        double denominator = 0;
        Map<Integer, ItemDeviation> deviations = deviationPerMovie.get(itemId);

        for(Map.Entry<Integer, Float> userRatingEntry : user.getRatings().entrySet()) {
            int itemIId = userRatingEntry.getKey();
            float userRatingForJ = userRatingEntry.getValue();
            ItemDeviation deviationBetweenIAndJ = deviations.get(itemIId);
            double dev = deviationBetweenIAndJ.getDeviation();
            int numberOfUsers = deviationBetweenIAndJ.getNumberOfUsers();

            numerator += (userRatingForJ + dev) * numberOfUsers;
            denominator += numberOfUsers;
        }
        return numerator / denominator;
    }
}
