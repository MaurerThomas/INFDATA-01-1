package datastructure;

import strategy.INearestNeighbourAlgorithm;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class User implements Comparable<User> {
    private int userId;
    private Map<Integer, Float> movieRatingsFromUser = new TreeMap<>();

    public User(int userId, Map<Integer, Float> movieRatingsFromUser) {
        this.movieRatingsFromUser = movieRatingsFromUser;
        this.userId = userId;
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
                    nearestNeighbours = checkToInsertOrRemoveNeighbour(minimumEntry, nearestNeighbours, similarity, currentUser, reversed, threshold);
                }
            }
        }
        //Return highest similar neighbours
        return nearestNeighbours;
    }

    public Map<User, Double> nearestNeighbourAlgorithm(User targetUser, Map<Integer, User> users, INearestNeighbourAlgorithm calculationMethod, int maxNeighbours, boolean reversed) {
        return nearestNeighbourAlgorithm(targetUser, users, calculationMethod, maxNeighbours, reversed, null);
    }

    /**
     * Check whether to insert or remove a neighbour based on the minimum value and similarity.
     *
     * @param mapEntry          The current minimum entry of the nearest neighbours map.
     * @param nearestNeighbours A map filled with the nearest neighbours.
     * @param similarity        The similarity of the user.
     * @param currentUser       The current user to check.
     * @param threshold         The minimum similarity threshold.
     * @return Returns the nearest neighbours map.
     */
    private Map<User, Double> checkToInsertOrRemoveNeighbour(Map.Entry<User, Double> mapEntry, Map<User, Double> nearestNeighbours, double similarity, User currentUser, boolean reversed, Double threshold) {
        double value = 0;

        if (reversed) {
            //Loop through nearestNeighbours TreeMap and get lowest similarity
            for (Map.Entry<User, Double> entry : nearestNeighbours.entrySet()) {
                if (mapEntry == null || mapEntry.getValue() < entry.getValue()) {
                    mapEntry = entry;
                    value = mapEntry.getValue();
                }
            }
        } else {
            //Loop through nearestNeighbours TreeMap and get lowest similarity
            for (Map.Entry<User, Double> entry : nearestNeighbours.entrySet()) {
                if (mapEntry == null || mapEntry.getValue() > entry.getValue()) {
                    mapEntry = entry;
                    value = mapEntry.getValue();
                }
            }
        }

        if(reversed){
            //Check user rating versus lowest similarity.
            if(value >= similarity){
                //Remove lowest similarity.
                nearestNeighbours.remove(mapEntry.getKey());
                //Check threshold and add user to nearestNeighbours TreeMap
                if(threshold == null) {
                    nearestNeighbours.put(currentUser, similarity);
                }
            }
        }else {
            //Check user rating versus lowest similarity.
            if(value <= similarity){
                //Remove lowest similarity.
                nearestNeighbours.remove(mapEntry.getKey());
                //Check threshold and add user to nearestNeighbours TreeMap
                if(threshold == null) {
                    nearestNeighbours.put(currentUser, similarity);
                } else if (similarity >= threshold) {
                    nearestNeighbours.put(currentUser, similarity);
                }
            }
        }

        return nearestNeighbours;
    }


    @Override
    public String toString() {
        return movieRatingsFromUser.toString();
    }

    @Override
    public int compareTo(User o) {
        return userId - o.userId;
    }

    public void addMovieRatingsToUser(int movieId, float rating) {
        movieRatingsFromUser.put(movieId, rating);
    }

    public float getMovieRating(int movieId) {
        return movieRatingsFromUser.get(movieId);
    }

    public Map<Integer, Float> getTreemap() {
        return movieRatingsFromUser;
    }

    public int getUserId() {
        return userId;
    }
}
