package datastructure;

import java.util.TreeMap;

/**
 * Created by Thomas on 9-2-2016.
 */
public class User {

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
}
