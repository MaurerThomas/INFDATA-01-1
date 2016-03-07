package datastructure;

import strategy.Cosine;
import strategy.Euclidean;
import strategy.Pearson;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Thomas on 9-2-2016.
 */
public class User {

    Euclidean ec = new Euclidean();
    Pearson pearson = new Pearson();
    Cosine cosine = new Cosine();


    private int userId;
    private TreeMap<Integer, Float> movieRatingsFromUser = new TreeMap<>();

    public User(int userId, TreeMap<Integer, Float> movieRatingsFromUser) {
        this.movieRatingsFromUser = movieRatingsFromUser;
        this.userId = userId;
    }

    public void addMovieRatingsToUser(int movieId, float rating) {
        movieRatingsFromUser.put(movieId, rating);
    }

    public String toString() {
        return movieRatingsFromUser.toString();
    }

    public float getMovieRating(int movieId) {
        return movieRatingsFromUser.get(movieId);
    }

    public TreeMap getTreemap() {
        return movieRatingsFromUser;
    }

    public int getUserId() {
        return userId;
    }

    public TreeMap calculateUsers(User targetUser, TreeMap users, char calculationMethod, double threshold) {
        Set set = users.entrySet();
        Iterator it = set.iterator();
        int targetUserId = targetUser.getUserId();
        TreeMap nearestNeighbours = new TreeMap<>();

        while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            double rating = 0;
            User currentUser = (User) me.getValue();
            int currentUserId = currentUser.getUserId();
            //Check of user gelijk is aan targetUser
            if (targetUserId != currentUserId) {
                //e = ec, p = pearson, c = cosine
                if (calculationMethod == 'c') {
                    rating = cosine.Calculate(targetUser, currentUser);
                } else if (calculationMethod == 'e') {
                    rating = ec.Calculate(targetUser, currentUser);
                } else if (calculationMethod == 'p') {
                    rating = pearson.Calculate(targetUser, currentUser);
                }

                if (rating >= threshold) {
                    nearestNeighbours.put(currentUserId, rating);
                }
            }
        }
        //Total distance
        return nearestNeighbours;
    }
}
