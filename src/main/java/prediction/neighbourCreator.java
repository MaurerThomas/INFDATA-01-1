package prediction;

import datastructure.User;
import strategy.INearestNeighbourAlgorithm;

import java.util.Map;

public class NeighbourCreator {
    private User targetUser;
    private Map<Integer, User> users;
    private INearestNeighbourAlgorithm calculationMethod;
    private int maxNeighbours;
    private boolean reversed;
    private Double threshold;
    private int targetUserId;
    private Map<User, Double> nearestNeighbours;
    private double value = 0;

    public NeighbourCreator(User targetUser, Map<Integer, User> users, INearestNeighbourAlgorithm calculationMethod, int maxNeighbours, boolean reversed, Double threshold, int targetUserId, Map<User, Double> nearestNeighbours) {
        this.targetUser = targetUser;
        this.users = users;
        this.calculationMethod = calculationMethod;
        this.maxNeighbours = maxNeighbours;
        this.reversed = reversed;
        this.threshold = threshold;
        this.targetUserId = targetUserId;
        this.nearestNeighbours = nearestNeighbours;
    }

    public Map<User, Double> invoke() {
        for (User currentUser : users.values()) {
            // use the calculationMethod's calculate function
            double similarity = calculationMethod.calculate(targetUser, currentUser);
            int currentUserId = currentUser.getUserId();

            //Check if user is not similar to targetUser, otherwise skip comparison
            if (targetUserId != currentUserId) {
                Map.Entry<User, Double> minimumEntry = null;
                //Fill 3 slots
                if (nearestNeighbours.size() < maxNeighbours) {
                    nearestNeighbours.put(currentUser, similarity);
                } else if (nearestNeighbours.size() >= maxNeighbours) {
                    nearestNeighbours = checkToInsertOrRemoveNeighbour(minimumEntry, currentUser, similarity);
                }
            }
        }
        return nearestNeighbours;
    }


    /**
     * Check whether to insert or remove a neighbour based on the minimum value and similarity.
     *
     * @param mapEntry    The current minimum entry of the nearest neighbours map.
     * @param similarity  The similarity of the user.
     * @param currentUser The current user to check.
     * @return Returns the nearest neighbours map.
     */
    private Map<User, Double> checkToInsertOrRemoveNeighbour(Map.Entry<User, Double> mapEntry, User currentUser, double similarity) {
        if (reversed) {
            nearestNeighbours = reversedCheckToInsertOrRemoveNeighbour(mapEntry, currentUser, similarity);
        } else {
            LowestSimilarity LowestSimilarity = new LowestSimilarity(mapEntry, nearestNeighbours, value).invoke();
            value = LowestSimilarity.getValue();
            //Check user rating versus lowest similarity.
            if (value <= similarity) {
                //Remove lowest similarity.
                nearestNeighbours.remove(LowestSimilarity.getMapEntry().getKey());
                //Check threshold and add user to nearestNeighbours TreeMap
                if (threshold == null) {
                    nearestNeighbours.put(currentUser, similarity);
                } else if (similarity >= threshold) {
                    nearestNeighbours.put(currentUser, similarity);
                }
            }
        }
        return nearestNeighbours;
    }

    private Map<User, Double> reversedCheckToInsertOrRemoveNeighbour(Map.Entry<User, Double> mapEntry, User currentUser, double similarity) {
        //Loop through nearestNeighbours TreeMap and get lowest similarity
        for (Map.Entry<User, Double> entry : nearestNeighbours.entrySet()) {
            if (mapEntry == null || mapEntry.getValue() < entry.getValue()) {
                mapEntry = entry;
                value = mapEntry.getValue();
            }
        }
        //Check user rating versus lowest similarity.
        if (value >= similarity) {
            //Remove lowest similarity.
            nearestNeighbours.remove(mapEntry.getKey());
            //Check threshold and add user to nearestNeighbours TreeMap
            if (threshold == null) {
                nearestNeighbours.put(currentUser, similarity);
            }
        }
        return nearestNeighbours;
    }
}
