package prediction;

import datastructure.User;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class RatingPredictor {
    Set<Integer> movieKeys = new HashSet<>();

    /**
     * Get the predicted rating for a particular movie after already having calculated
     * the nearest neighbours with a given user.
     *
     * @param movieId           The movieId, which has not been rated by the target user yet.
     * @param nearestNeighbours A Map of nearest neighbours.
     * @return The predicted rating for an item.
     */
    public double getPredictedRating(int movieId, Map<User, Double> nearestNeighbours) {
        Set<User> userSet = new HashSet<>(nearestNeighbours.keySet());
        Map<Integer, Float> movieRatingsFromUser;
        double totalSimilarity = 0;
        double similarityTimesRating = 0;

        for (User neighbour : userSet) {
            movieRatingsFromUser = neighbour.getRatings();

            if (movieRatingsFromUser.get(movieId) != null) {
                float userRating = neighbour.getMovieRating(movieId);
                double similarity = nearestNeighbours.get(neighbour);

                similarityTimesRating += (similarity * userRating);
                totalSimilarity += similarity;
            }
        }
        return similarityTimesRating / totalSimilarity;
    }

    /**
     * Get the top n predicted ratings for a user
     *
     * @param nearestNeighbours A map of nearest neighbours
     * @param minimumNeighbours The number of minimum neighbours that have to had rated a movie.
     * @param maxRatings        the maximum amount of ratings that have to be returned, e.g. top 10.
     * @return A map of movieId's and predicted ratings, sorted from high to low.
     */
    public Map<Integer, Double> getTopNRatings(Map<User, Double> nearestNeighbours, int minimumNeighbours, int maxRatings) {
        Map<Integer, Double> predictedRatingMap = new TreeMap<>();

        for (Map.Entry<User, Double> me : nearestNeighbours.entrySet()) {
            User user = me.getKey();
            Map<Integer, Float> userRatings = user.getRatings();
            movieKeys.addAll(userRatings.keySet());
        }

        for (int movieId : movieKeys) {
            if (minimumNeighbours < 1 || ratedByAtLeast(movieId, minimumNeighbours, nearestNeighbours)) {
                double predictedRating = getPredictedRating(movieId, nearestNeighbours);

                if (predictedRatingMap.size() < maxRatings) {
                    predictedRatingMap.put(movieId, predictedRating);
                } else {
                    break;
                }
            }
        }
        return TreeMapSorter.sortByValue(predictedRatingMap);
    }


    /**
     * Check if the given movie is rated by a minimum number of neighbours.
     *
     * @param movieId           The given movie id.
     * @param minimumNeighbours The number of minimum neighbours that has to be checked.
     * @param nearestNeighbours A Map of earlier computed neighbours
     * @return If the movie is rated by more than minimumNeighbours
     */
    private boolean ratedByAtLeast(int movieId, int minimumNeighbours, Map<User, Double> nearestNeighbours) {
        int foundCount = 0;
        for (Map.Entry<User, Double> me : nearestNeighbours.entrySet()) {
            User neighbour = me.getKey();
            Map<Integer, Float> neighbourTreeMap = neighbour.getRatings();

            if (neighbourTreeMap.get(movieId) != null) {
                foundCount++;

                if (foundCount >= minimumNeighbours) {
                    return true;
                }
            }
        }
        return false;
    }
}


