package prediction;

import datastructure.Item;
import datastructure.User;

import java.util.Map;

public class ItemRatingPredictor {


    public double predictedRating(Item itemI, Item itemJ) {
        double numerator = 0;
        double denominator = 0;

        // foreach item j that user u rated
        for (Map.Entry<Integer, Float> me : itemI.getUserItemRatings().entrySet()) {
            int userId = me.getKey();

            if (userExistsInItemJ(itemI, itemJ, userId)){

                //get deviation between i and j
                //numerator += (rating of u for j + dev between i and j) * howManyUsers
                //denominator += howManyUsers

            }


        }

        return numerator / denominator;
    }

    private boolean userExistsInItemJ(Item itemI, Item itemJ, int userId) {
        Map<Integer, Float> itemJRatings = itemJ.getUserItemRatings();
        return itemJRatings.containsKey(userId) && itemI.getItemId() != itemJ.getItemId();
    }



}
