package datastructure;

import strategy.INearestNeighbourAlgorithm;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Thomas on 9-2-2016.
 */
public class User {
    private int userId;
    private Map<Integer, Float> movieRatingsFromUser = new TreeMap<>();

    public User(int userId, Map<Integer, Float> movieRatingsFromUser) {
        this.movieRatingsFromUser = movieRatingsFromUser;
        this.userId = userId;
    }

    public void addMovieRatingsToUser(int movieId, float rating) {
        movieRatingsFromUser.put(movieId, rating);
    }

    @Override
    public String toString() {
        return movieRatingsFromUser.toString();
    }

    public float getMovieRating(int movieId) {
        return movieRatingsFromUser.get(movieId);
    }

    public Map getTreemap() {
        return movieRatingsFromUser;
    }

    public int getUserId() {
        return userId;
    }

    public Map<Integer, Double> calculateUsers(User targetUser, Map<Integer, User> users, INearestNeighbourAlgorithm calculationMethod) {
        return calculateUsers(targetUser, users, calculationMethod, null);
    }

    public Map<Integer, Double> calculateUsers(User targetUser, Map<Integer, User> users, INearestNeighbourAlgorithm calculationMethod, Double threshold) {
        int targetUserId = targetUser.getUserId();
        Map<Integer, Double> nearestNeighbours = new TreeMap<>();

        for(User currentUser : users.values()) {
            double rating;
            int currentUserId = currentUser.getUserId();

            //Check if user is not similar to targetUser, otherwise skip comparison
            if (targetUserId != currentUserId) {
                //use the calculationMethod's calculate function
                rating = calculationMethod.calculate(targetUser, currentUser);

                if(threshold == null) {
                    nearestNeighbours.put(currentUserId, rating);
                } else if (rating >= threshold) {
                    nearestNeighbours.put(currentUserId, rating);
                }
            }
        }
        //Total distance
        return nearestNeighbours;
    }
}
