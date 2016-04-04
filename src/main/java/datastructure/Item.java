package datastructure;

import java.util.Map;

public class Item {

    private int itemId;
    private Map<Integer, Float> userItemRatings;

    public Item(int itemId, Map<Integer, Float> userItemRatings) {
        this.itemId = itemId;
        this.userItemRatings = userItemRatings;
    }

    public int getItemId() {
        return itemId;
    }

    public Map<Integer, Float> getUserItemRatings() {
        return userItemRatings;
    }

    public void addUserRating(int userId, float rating) {
        userItemRatings.put(userId, rating);
    }
}
