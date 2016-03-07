package strategy;

import datastructure.User;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Cosine implements INearestNeighbourAlgorithms {

    @Override
    public double Calculate(User userOne, User userTwo) {
        float x = 0;
        float y = 0;
        double x2 = 0;
        double y2 = 0;
        float dotProductXY = 0;
        double denominator;

        TreeMap userOneTreeMap = userOne.getTreemap();
        TreeMap userTwoTreeMap = userTwo.getTreemap();
        Set set = userOneTreeMap.entrySet();
        Iterator it = set.iterator();

        while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();

            if (userTwoTreeMap.get(me.getKey()) != null) {
                float userOneRating = Float.parseFloat(me.getValue().toString());
                float userTwoRating = Float.parseFloat(userTwoTreeMap.get(me.getKey()).toString());

                x += userOneRating * userOneRating;
                y += userTwoRating * userTwoRating;

                x2 = Math.sqrt(x);
                y2 = Math.sqrt(y);

                dotProductXY += (userOneRating * userTwoRating);
            } else {
                return 0;
            }
        }
        denominator = x2 * y2;
        return dotProductXY / denominator;
    }
}
