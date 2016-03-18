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
        double totalSimularity = 0;
        double simularityTimesRating = 0;

        for (User neighbour : userSet) {
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

    public Map<Integer, Double> getTopNRatings(Map<User, Double> nearestNeighbours, int minimumNeighours, int maxRatings) {
        Map<Integer, Double> predictedRatingMap = new TreeMap<>();

        for (Map.Entry<User, Double> me : nearestNeighbours.entrySet()) {
            User user = me.getKey();
            Map<Integer, Float> userRatings = user.getTreemap();
            movieKeys.addAll(userRatings.keySet());
        }

        for (Integer movieId : movieKeys) {
            if (minimumNeighours < 1 || ratedByAtLeast(movieId, minimumNeighours, nearestNeighbours)) {
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
     * @return A sorted TreeMap
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

    private boolean ratedByAtLeast(int movieId, int minimumNeighours, Map<User, Double> nearestNeighbours) {
        int foundCount = 0;
        for (Map.Entry<User, Double> me : nearestNeighbours.entrySet()) {
            User neighbour = me.getKey();
            Map<Integer, Float> neighbourTreeMap = neighbour.getTreemap();

            if (neighbourTreeMap.get(movieId) != null) {
                foundCount++;

                if (foundCount >= minimumNeighours) {
                    return true;
                }
            }
        }
        return false;
    }
}


