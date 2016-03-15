package datastructure;

import strategy.INearestNeighbourAlgorithm;
import java.util.*;

public class User implements Comparable<User> {
    private int userId;
    private Map<Integer, Float> movieRatingsFromUser = new TreeMap<>();
    
    public User(int userId, Map<Integer, Float> movieRatingsFromUser) {
        this.movieRatingsFromUser = movieRatingsFromUser;
        this.userId = userId;
    }

    /**
     * Search for the nearest neighbours.
     *
     * @param targetUser The initial user to find the neighbours for.
     * @param users All users in our data set.
     * @param calculationMethod Which algorithm to use.
     * @param threshold The minimum similarity threshold. Can be null if a threshold is not necessary.
     * @param maxNeighbours The maximum neighbours we want to find.
     * @return Returns a map of nearest neighbours.
     */
    public Map<User, Double> nearestNeighbourAlgorithm(User targetUser, Map<Integer, User> users, INearestNeighbourAlgorithm calculationMethod, Double threshold, int maxNeighbours) {
        int targetUserId = targetUser.getUserId();
        Map<User, Double> nearestNeighbours = new TreeMap<>();

        for(User currentUser : users.values()) {
            // use the calculationMethod's calculate function
            double similarity = calculationMethod.calculate(targetUser, currentUser);
            int currentUserId = currentUser.getUserId();

            //Check if user is not similar to targetUser, otherwise skip comparison
            if (targetUserId != currentUserId) {
                Map.Entry<User, Double> minimumEntry = null;
                //Fill 3 slots
                if(nearestNeighbours.size() < maxNeighbours){
                    nearestNeighbours.put(currentUser, similarity);
                }else if(nearestNeighbours.size() >= maxNeighbours){
                    nearestNeighbours = checkToInsertOrRemoveNeighbour(minimumEntry, nearestNeighbours, similarity, currentUser, threshold);
                }
            }
        }
        //Return highest similar neighbours
        return nearestNeighbours;
    }

    public Map<User, Double> nearestNeighbourAlgorithm(User targetUser, Map<Integer, User> users, INearestNeighbourAlgorithm calculationMethod, int maxNeighbours) {
        return nearestNeighbourAlgorithm(targetUser, users, calculationMethod, null, maxNeighbours);
    }

    /**
     * Check whether to insert or remove a neighbour based on the minimum value and similarity.
     *
     * @param minimumEntry The current minimum entry of the nearest neighbours map.
     * @param nearestNeighbours A map filled with the nearest neighbours.
     * @param similarity The similarity of the user.
     * @param currentUser The current user to check.
     * @param threshold The minimum similarity threshold.
     * @return Returns the nearest neighbours map.
     */
    private Map<User, Double> checkToInsertOrRemoveNeighbour( Map.Entry<User, Double> minimumEntry, Map<User, Double> nearestNeighbours, double similarity, User currentUser, Double threshold  ) {
        double minimumValue = 0;
        //Loop through nearestNeighbours TreeMap and get lowest similarity
        for (Map.Entry<User, Double> entry : nearestNeighbours.entrySet()) {
            if (minimumEntry == null || minimumEntry.getValue() > entry.getValue()) {
                minimumEntry = entry;
                minimumValue = minimumEntry.getValue();
            }
        }
        //Check user rating versus lowest similarity.
        if(minimumValue <= similarity){
            //Remove lowest similarity.
            nearestNeighbours.remove(minimumEntry.getKey());
            //Check threshold and add user to nearestNeighbours TreeMap
            if(threshold == null) {
                nearestNeighbours.put(currentUser, similarity);
            } else if (similarity >= threshold) {
                nearestNeighbours.put(currentUser, similarity);
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
