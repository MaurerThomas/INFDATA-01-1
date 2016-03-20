package prediction;

import datastructure.User;

import java.util.*;

public class RatingPredictor {
    Set<Integer> movieKeys = new HashSet<>();

    /**
     * Get the predicted rating for a targetUser and movie.
     *
     * @param movieId The movieId, which has not been rated by the target user yet.
     * @param nearestNeighbours A Map of nearest neighbours.
     * @return The predicted rating to an item.
     */
    public double getPredictedRating(int movieId, Map<User, Double> nearestNeighbours) {
        Set<User> userSet = new HashSet<>(nearestNeighbours.keySet());
        Map<Integer, Float> movieRatingsFromUser;
        double totalSimilarity = 0;
        double similarityTimesRating = 0;

        for (User neighbour : userSet) {
            movieRatingsFromUser = neighbour.getTreemap();

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
     * @param nearestNeighbours A map of nearest neighbours
     * @param minimumNeighbours The number of minimum neighbours that have to had rated a movie.
     * @param maxRatings the maximum amount of ratings that have to be returned, e.g. top 10.
     * @return A map of movieId's and predicted ratings, sorted from high to low.
     */
    public Map<Integer, Double> getTopNRatings(Map<User, Double> nearestNeighbours, int minimumNeighbours, int maxRatings) {
        Map<Integer, Double> predictedRatingMap = new TreeMap<>();

        for (Map.Entry<User, Double> me : nearestNeighbours.entrySet()) {
            User user = me.getKey();
            Map<Integer, Float> userRatings = user.getTreemap();
            movieKeys.addAll(userRatings.keySet());
        }

        for (Integer movieId : movieKeys) {
            if (minimumNeighbours < 1 || ratedByAtLeast(movieId, minimumNeighbours, nearestNeighbours)) {
                double predictedRating = getPredictedRating(movieId, nearestNeighbours);
                if (predictedRatingMap.size() < maxRatings) {
                    predictedRatingMap.put(movieId, predictedRating);
                } else {
                    break;
                }
            }
        }
        return sortByValue(predictedRatingMap);
    }

    /**
     * Returns a sorted TreeMap from highest to lowest.
     * Source: http://stackoverflow.com/a/2581754
     *
     * @param map The TreeMap.
     * @param <K> TreeMap Key.
     * @param <V> TreeMap Value.
     * @return A sorted TreeMap.
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list =
                new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * Check if the given movie is rated by a minimum number of neighbours.
     * @param movieId The given movie id.
     * @param minimumNeighbours The number of minimum neighbours that has to be checked.
     * @param nearestNeighbours A Map of earlier computed neighbours
     * @return If the movie is rated by more than minimumNeighbours
     */
    private boolean ratedByAtLeast(int movieId, int minimumNeighbours, Map<User, Double> nearestNeighbours) {
        int foundCount = 0;
        for (Map.Entry<User, Double> me : nearestNeighbours.entrySet()) {
            User neighbour = me.getKey();
            Map<Integer, Float> neighbourTreeMap = neighbour.getTreemap();

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


