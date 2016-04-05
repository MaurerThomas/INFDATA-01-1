package prediction;

import datastructure.Item;

import java.util.Map;
import java.util.TreeMap;

public class ItemDeviation {
    private int numberOfUsers;
    private double deviation;
    private Map<Integer, Map<Integer, ItemDeviation>> deviationPerMovie = new TreeMap<>();

    public double calculateItemDeviation(Item itemI, Item itemJ) {
        double currentDeviation = 0;
        Map<Integer, Float> itemJRatings = itemJ.getUserItemRatings();

        for (Map.Entry<Integer, Float> me : itemI.getUserItemRatings().entrySet()) {
            float itemIRating = me.getValue();
            int userId = me.getKey();
            if (userExistsInItemJ(itemI, itemJ, userId)) {
                float itemJRating = itemJRatings.get(userId);
                currentDeviation += (itemIRating - itemJRating);
                numberOfUsers++;
            }
        }

        if (!Double.isNaN(currentDeviation)){
            deviation = currentDeviation / numberOfUsers;

        }
        return deviation;

    }

    public Map<Integer, Map<Integer, ItemDeviation>> calculateItemDeviations(Map<Integer, Item> allItemsTreeMap) {

        for (Item itemI : allItemsTreeMap.values()) {
            Map<Integer, ItemDeviation> calculatedItemDeviations = new TreeMap<>();

            for (Item itemJ : allItemsTreeMap.values()) {
                ItemDeviation itemDeviation = new ItemDeviation();

                itemDeviation.calculateItemDeviation(itemI, itemJ);
                calculatedItemDeviations.put(itemJ.getItemId(), itemDeviation);
            }
            deviationPerMovie.put(itemI.getItemId(), calculatedItemDeviations);
        }
        return deviationPerMovie;
    }


    public void addRating(int userId, float newRating, Item targetItem, Map<Integer, Item> allItemsMap) {
        int itemId = targetItem.getItemId();
        double numerator;
        int denominator;
        targetItem.addUserRating(userId, newRating);
        for (Item item : allItemsMap.values()) {
            if (item != targetItem) {
                Float rating = item.getUserItemRatings().get(userId);
                if (rating != null) {
                    double deviationForItem = deviationPerMovie.get(itemId).get(item.getItemId()).getDeviation();
                    int users = deviationPerMovie.get(itemId).get(item.getItemId()).getNumberOfUsers();
                    numerator = (deviationForItem * users) + (newRating - rating);
                    denominator = users + 1;
                    deviationPerMovie.get(itemId).get(item.getItemId()).setNumberOfUsers(users + 1);
                    deviationPerMovie.get(itemId).get(item.getItemId()).setDeviation(numerator / denominator);
                    // Reverse
                    deviationPerMovie.get(item.getItemId()).get(itemId).setNumberOfUsers(users + 1);
                    deviationPerMovie.get(item.getItemId()).get(itemId).setDeviation(Math.abs(numerator / denominator));
                }
            }
        }
    }

    private boolean userExistsInItemJ(Item itemI, Item itemJ, int userId) {
        Map<Integer, Float> itemJRatings = itemJ.getUserItemRatings();
        return itemJRatings.containsKey(userId) && itemI.getItemId() != itemJ.getItemId();
    }

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    public double getDeviation() {
        return deviation;
    }

    public void setDeviation(double deviation) {
        this.deviation = deviation;
    }

    public Map<Integer, Map<Integer, ItemDeviation>> getDeviationPerMovie() {
        return deviationPerMovie;
    }
}
