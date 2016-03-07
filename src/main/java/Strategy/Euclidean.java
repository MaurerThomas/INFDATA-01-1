package strategy;

import datastructure.User;

import java.util.*;

public class Euclidean implements INearestNeighbourAlgorithms {

    @Override
    public double Calculate(User userOne, User userTwo) {
        List<Float> calculatedValues = new ArrayList<>();
        TreeMap userOneTreeMap = userOne.getTreemap();
        TreeMap userTwoTreeMap = userTwo.getTreemap();
        Set set = userOneTreeMap.entrySet();
        Iterator it = set.iterator();
        double calculatedValuesSum = 0;

        while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            float calculated;

            if (userTwoTreeMap.get(me.getKey()) != null) {
                float userOneRating = Float.parseFloat(me.getValue().toString());
                float userTwoRating = Float.parseFloat(userTwoTreeMap.get(me.getKey()).toString());
                calculated = (userOneRating - userTwoRating) * (userOneRating - userTwoRating);
                calculatedValues.add(calculated);
            } else {
                calculated = 0;
                calculatedValues.add(calculated);
            }
        }

        for (int i = 0; i < calculatedValues.size(); i++) {
            calculatedValuesSum += calculatedValues.get(i);
        }
        return Math.sqrt((1 + calculatedValuesSum));
    }
}
