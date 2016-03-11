package datastructure;

import strategy.INearestNeighbourAlgorithm;

import java.util.*;

/**
 * Created by Thomas on 9-2-2016.
 */
public class User implements Comparable<User> {
    private int userId;
    private Map<Integer, Float> movieRatingsFromUser = new TreeMap<>();
    public static final int MAXNEIGHBOURS = 3;

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

    public Map<Integer, Float> getTreemap() {
        return movieRatingsFromUser;
    }

    public int getUserId() {
        return userId;
    }

    public Map<User, Double> calculateUsers(User targetUser, Map<Integer, User> users, INearestNeighbourAlgorithm calculationMethod) {
        return calculateUsers(targetUser, users, calculationMethod, null);
    }

    public Map<User, Double> calculateUsers(User targetUser, Map<Integer, User> users, INearestNeighbourAlgorithm calculationMethod, Double threshold) {
        int targetUserId = targetUser.getUserId();
        Map<User, Double> nearestNeighbours = new TreeMap<>();

        for(User currentUser : users.values()) {
            // use the calculationMethod's calculate function
            double simularity = calculationMethod.calculate(targetUser, currentUser);
            int currentUserId = currentUser.getUserId();

            //Check if user is not similar to targetUser, otherwise skip comparison
            if (targetUserId != currentUserId) {
                Map.Entry<User, Double> min = null;
                double minVal = 0;

                //Fill 3 slots
                if(nearestNeighbours.size() < MAXNEIGHBOURS){
                    nearestNeighbours.put(currentUser, simularity);
                }else if(nearestNeighbours.size() >= MAXNEIGHBOURS){
                    //Loop through nearestNeighbours TreeMap and get lowest simularity
                    for (Map.Entry<User, Double> entry : nearestNeighbours.entrySet()) {
                        if (min == null || min.getValue() > entry.getValue()) {
                            min = entry;
                            minVal = min.getValue();
                        }
                    }
                    //Check user rating versus lowest simularity.
                    if(minVal <= simularity){
                        //Remove lowest simularity.
                        nearestNeighbours.remove(min.getKey());

                        //Check threshold and add user to nearestNeighbours TreeMap
                        if(threshold == null) {
                            nearestNeighbours.put(currentUser, simularity);
                        } else if (simularity >= threshold) {
                            nearestNeighbours.put(currentUser, simularity);
                        }
                    }

                }
            }
        }

        //Return highest simular neighbours
        return nearestNeighbours;
    }


    @Override
    public int compareTo(User o) {
        return userId - o.userId;
    }
}
