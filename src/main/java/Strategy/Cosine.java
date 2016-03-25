package strategy;

import datastructure.User;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Cosine implements INearestNeighbourAlgorithm {
    /**
     * Calculate the similarity between two users.
     *
     * @param userOne The first user
     * @param userTwo The second user
     * @return Cosine similarity
     */
    @Override
    public double calculate(User userOne, User userTwo) {
        float x = 0;
        float y = 0;
        double x2 = 0;
        double y2 = 0;
        float dotProductXY = 0;
        double denominator;

        Map<Integer, Float> userOneTreeMap = userOne.getTreemap();
        Map<Integer, Float> userTwoTreeMap = userTwo.getTreemap();

        Set<Integer> keys = new HashSet<>(userOneTreeMap.keySet());
        keys.addAll(userTwoTreeMap.keySet());

        for (int movieId : keys) {
            float userOneRating;
            float userTwoRating;

            if (userOneTreeMap.containsKey(movieId)) {
                userOneRating = userOneTreeMap.get(movieId);
            } else {
                userOneRating = 0;
            }

            if (userTwoTreeMap.containsKey(movieId)) {
                userTwoRating = userTwoTreeMap.get(movieId);
            } else {
                userTwoRating = 0;
            }

            x += userOneRating * userOneRating;
            y += userTwoRating * userTwoRating;

            x2 = Math.sqrt(x);
            y2 = Math.sqrt(y);

            dotProductXY += (userOneRating * userTwoRating);
        }

        denominator = x2 * y2;

        return dotProductXY / denominator;
    }
}
