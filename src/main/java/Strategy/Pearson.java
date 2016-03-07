package strategy;

import datastructure.User;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Pearson implements INearestNeighbourAlgorithms {

    @Override
    public double Calculate(User userOne, User userTwo) {
        float sum_xy = 0;
        float sum_x = 0;
        float sum_y = 0;
        float sum_x2 = 0;
        float sum_y2 = 0;
        int n = 0;
        double denominator = 0;


        TreeMap userOneTreeMap = userOne.getTreemap();
        TreeMap userTwoTreeMap = userTwo.getTreemap();
        Set set = userOneTreeMap.entrySet();
        Iterator it = set.iterator();

        while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            // Check if both users rated the same movie entry.
            if (userTwoTreeMap.get(me.getKey()) != null) {
                float userOneRating = Float.parseFloat(me.getValue().toString());
                float userTwoRating = Float.parseFloat(userTwoTreeMap.get(me.getKey()).toString());
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
            } else {
                return 0;
            }
        }
        denominator = Math.sqrt(sum_x2 - ((sum_x * sum_x) / n)) *
                Math.sqrt(sum_y2 - ((sum_y * sum_y) / n));
        // For comparisons of zero. Using bit comparison ensures we compare zero values.
        if (Double.doubleToRawLongBits(denominator) == 0) {
            return 0;
        } else {
            return (sum_xy - (sum_x * sum_y) / n) / denominator;
        }
    }
}
