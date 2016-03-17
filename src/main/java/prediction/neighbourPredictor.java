package prediction;

import datastructure.User;
import strategy.INearestNeighbourAlgorithm;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Thomas on 16-3-2016.
 */
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


    //Method for sorting the TreeMap based on values
    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator = new Comparator<K>() {
            public int compare(K k1, K k2) {
                int compare = map.get(k1).compareTo(map.get(k2));
                if (compare == 0)
                    return 1;
                else
                    return compare;
            }
        };

        Map<K, V> sortedByValues = new TreeMap<>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }


    }

