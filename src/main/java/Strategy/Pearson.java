package strategy;

import datastructure.User;

import java.util.Map;
import java.util.TreeMap;

public class Pearson implements INearestNeighbourAlgorithm {

    @Override
    public double calculate(User userOne, User userTwo) {
        double sum_xy = 0;
        double sum_x = 0;
        double sum_y = 0;
        double sum_x2 = 0;
        double sum_y2 = 0;
        int n = 0;
        double denominator;

        Map<Integer, Float> userOneTreeMap = userOne.getTreemap();
        Map<Integer, Float> userTwoTreeMap = userTwo.getTreemap();

        for(Map.Entry<Integer, Float> me : userOneTreeMap.entrySet()){
            if (userTwoTreeMap.containsKey(me.getKey())) {
                float userOneRating = me.getValue();
                float userTwoRating = userTwoTreeMap.get(me.getKey());
                // Total movies.
                n++;
                // Square of rating 1 and rating 2.
                sum_xy += (userOneRating * userTwoRating);
                // Sum of all user 1 ratings.
                sum_x += userOneRating;
                // Sum of all user 2 ratings.
                sum_y += userTwoRating;
                // Square of rating 1.
                sum_x2 += (userOneRating * userOneRating);
                // Square of rating 2.
                sum_y2 += (userTwoRating * userTwoRating);
                // Return nothing if no match.
            }
        }

        denominator = Math.sqrt((sum_x2 - ((sum_x * sum_x) / n)) * (sum_y2 - ((sum_y * sum_y) / n)));

        //comparison ensures we compare zero values.
        if (Double.doubleToRawLongBits(denominator) == 0) {
            return 0;
        } else {
            return (sum_xy - (sum_x * sum_y) / n) / denominator;
        }
    }
}
