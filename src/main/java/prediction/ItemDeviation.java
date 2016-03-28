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

            if(userExistsInItemJ(itemI, itemJ, userId)){
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

        if(Double.doubleToRawLongBits(currentDeviation) == 0){
            return 0;
        }else{
            deviation = currentDeviation / numberOfUsers;
            return deviation;
        }

    }

    private boolean userExistsInItemJ(Item itemI, Item itemJ, int userId) {
        Map<Integer, Float> itemJRatings = itemJ.getUserItemRatings();
        return itemJRatings.containsKey(userId) && itemI.getItemId() != itemJ.getItemId();
    }

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public double getDeviation() {
        return deviation;
    }
}
