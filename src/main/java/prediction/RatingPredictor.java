package prediction;

import datastructure.User;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RatingPredictor {

    /**
     * Get the predicted rating for a targetUser and movie.
     * @param targetUser The target User.
     * @param movieId The movieId, which has not been rated by the target user yet.
     * @param nearestNeighbours A Map of nearest neighbours.
     * @return The predicted rating to an item.
     */
    public double getPredictedRating(User targetUser, int movieId, Map<User, Double> nearestNeighbours) {
        Set<User> keys = new HashSet<>(nearestNeighbours.keySet());
        Map<Integer, Float> movieRatingsFromUser;
        double totalSimularity = 0;
        double simularityTimesRating = 0;

        for (User neighbour : keys) {
            movieRatingsFromUser = neighbour.getTreemap();

            if (movieRatingsFromUser.get(movieId) != null) {
                float userRating = neighbour.getMovieRating(movieId);
                double simularity = nearestNeighbours.get(neighbour);

                simularityTimesRating += (simularity * userRating);
                totalSimularity += simularity;
            }
        }
        return simularityTimesRating / totalSimularity;
    }
}
