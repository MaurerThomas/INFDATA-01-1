package strategy;

import datastructure.User;

import java.util.*;

public class Euclidean implements INearestNeighbourAlgorithm {
    /**
     * Calculate the similarity between two users.
     * @param userOne The first user
     * @param userTwo The second user
     * @return Euclidean distance
     */
    @Override
    public double calculate(User userOne, User userTwo) {
        List<Double> calculatedValues = new ArrayList<>();
        Map<Integer, Float> userOneTreeMap = userOne.getTreemap();
        Map<Integer, Float> userTwoTreeMap = userTwo.getTreemap();
        double calculatedValuesSum = 0;

        for(Map.Entry<Integer, Float> me : userOneTreeMap.entrySet()) {
            double calculated;

            if (userTwoTreeMap.containsKey(me.getKey())) {
                float userOneRating = me.getValue();
                float userTwoRating = userTwoTreeMap.get(me.getKey());

                calculated = Math.pow(userOneRating - userTwoRating, 2);
                calculatedValues.add(calculated);
            }
        }

        for (int i = 0; i < calculatedValues.size(); i++) {
            calculatedValuesSum += calculatedValues.get(i);
        }
        return Math.sqrt(calculatedValuesSum);
    }
}
