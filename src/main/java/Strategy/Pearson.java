package strategy;

import datastructure.User;

import java.util.Map;

public class Pearson implements INearestNeighbourAlgorithm {

    /**
     * Calculate the similarity between two users.
     *
     * @param userOne The first user
     * @param userTwo The second user
     * @return Pearson coefficient
     */
    @Override
    public double calculate(User userOne, User userTwo) {
        double sumXy = 0;
        double sumX = 0;
        double sumY = 0;
        double sumX2 = 0;
        double sumY2 = 0;
        int n = 0;
        double denominator;

        Map<Integer, Float> userOneTreeMap = userOne.getRatings();
        Map<Integer, Float> userTwoTreeMap = userTwo.getRatings();

        for (Map.Entry<Integer, Float> me : userOneTreeMap.entrySet()) {
            if (userTwoTreeMap.containsKey(me.getKey())) {
                float userOneRating = me.getValue();
                float userTwoRating = userTwoTreeMap.get(me.getKey());
                // Total movies.
                n++;
                // Square of rating 1 and rating 2.
                sumXy += (userOneRating * userTwoRating);
                // Sum of all user 1 ratings.
                sumX += userOneRating;
                // Sum of all user 2 ratings.
                sumY += userTwoRating;
                // Square of rating 1.
                sumX2 += (userOneRating * userOneRating);
                // Square of rating 2.
                sumY2 += (userTwoRating * userTwoRating);
                // Return nothing if no match.
            }
        }

        denominator = Math.sqrt((sumX2 - ((sumX * sumX) / n)) * (sumY2 - ((sumY * sumY) / n)));

        //comparison ensures we compare zero values.
        if (Double.doubleToRawLongBits(denominator) == 0) {
            return 0;
        } else {
            return (sumXy - (sumX * sumY) / n) / denominator;
        }
    }
}
