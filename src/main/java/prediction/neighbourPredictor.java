package prediction;

import datastructure.User;
import strategy.INearestNeighbourAlgorithm;

import java.util.Map;
import java.util.TreeMap;

public class NeighbourPredictor {

    /**
     * Search for the nearest neighbours.
     *
     * @param targetUser        The initial user to find the neighbours for.
     * @param users             All users in our data set.
     * @param calculationMethod Which algorithm to use.
     * @param threshold         The minimum similarity threshold. Can be null if a threshold is not necessary.
     * @param maxNeighbours     The maximum neighbours we want to find.
     * @return Returns a map of nearest neighbours.
     */
    public Map<User, Double> nearestNeighbourAlgorithm(User targetUser, Map<Integer, User> users, INearestNeighbourAlgorithm calculationMethod, int maxNeighbours, boolean reversed, Double threshold) {
        int targetUserId = targetUser.getUserId();
        Map<User, Double> nearestNeighbours = new TreeMap<>();
        nearestNeighbours = new NeighbourCreator(targetUser, users, calculationMethod, maxNeighbours, reversed, threshold, targetUserId, nearestNeighbours).invoke();
        //Return highest similar neighbours
        return nearestNeighbours;
    }

    public Map<User, Double> nearestNeighbourAlgorithm(User targetUser, Map<Integer, User> users, INearestNeighbourAlgorithm calculationMethod, int maxNeighbours, boolean reversed) {
        return nearestNeighbourAlgorithm(targetUser, users, calculationMethod, maxNeighbours, reversed, null);
    }

}

