package prediction;

import datastructure.Item;
import java.util.Map;

public class ItemDeviation {
    private int numberOfUsers;
    private double deviation;

    public double calculateItemDeviation(Item itemI, Item itemJ, boolean reversed) {
        double currentDeviation = 0;
        Map<Integer, Float> itemJRatings = itemJ.getUserItemRatings();

        for (Map.Entry<Integer, Float> me : itemI.getUserItemRatings().entrySet()) {
            float itemIRating = me.getValue();
            int userId = me.getKey();

            if(userExistsInItemJ(itemI, itemJ, itemJRatings, userId)){
                float itemJRating = itemJRatings.get(userId);
                if (reversed) {
                    currentDeviation += (itemJRating - itemIRating);
                } else {
                    currentDeviation += (itemIRating - itemJRating);
                }
                numberOfUsers++;
            } else {
                break;
            }
        }
        deviation = currentDeviation / numberOfUsers;

        return checkIfDeviationIsNotANumber();
    }

    private double checkIfDeviationIsNotANumber() {
        if (!Double.isNaN(deviation)) {
            return deviation;
        } else {
            return 0;
        }
    }

    private boolean userExistsInItemJ(Item itemI, Item itemJ, Map<Integer, Float> itemJRatings, int userId) {
        return itemJRatings.containsKey(userId) && itemI.getItemId() != itemJ.getItemId();
    }

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public double getDeviation() {
        return deviation;
    }
}
