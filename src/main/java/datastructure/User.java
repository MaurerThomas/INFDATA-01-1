package datastructure;

import java.util.Map;
import java.util.TreeMap;

public class User implements Comparable<User> {
    private int userId;
    private Map<Integer, Float> movieRatingsFromUser = new TreeMap<>();

    public User(int userId, Map<Integer, Float> movieRatingsFromUser) {
        this.movieRatingsFromUser = movieRatingsFromUser;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return movieRatingsFromUser.toString();
    }

    @Override
    public int compareTo(User o) {
        return userId - o.userId;
    }

    public void addMovieRating(int movieId, float rating) {
        movieRatingsFromUser.put(movieId, rating);
    }

    public float getMovieRating(int movieId) {
        return movieRatingsFromUser.get(movieId);
    }

    public Map<Integer, Float> getRatings() {
        return movieRatingsFromUser;
    }

    public int getUserId() {
        return userId;
    }
}
