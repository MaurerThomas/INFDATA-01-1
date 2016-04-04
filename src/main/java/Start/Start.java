package start;

import datastructure.DataSetLoader;
import datastructure.User;
import prediction.NeighbourPredictor;
import prediction.RatingPredictor;
import strategy.Cosine;
import strategy.Euclidean;
import strategy.INearestNeighbourAlgorithm;
import strategy.Pearson;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Start {
    private static final INearestNeighbourAlgorithm EUCLIDEAN = new Euclidean();
    private static final INearestNeighbourAlgorithm COSINE = new Cosine();
    private static final INearestNeighbourAlgorithm PEARSON = new Pearson();

    private Start() {
    }

    public static void main(String[] args) {
        DataSetLoader dsl = new DataSetLoader();
        Map<Integer, User> allUsersTreeMap = new TreeMap<>();
        Map<Integer, User> allUsersTreeMap100k = new TreeMap<>();
        Logger logger = Logger.getLogger("myLogger");
        RatingPredictor ratingPredictor = new RatingPredictor();
        NeighbourPredictor neighbourPredictor = new NeighbourPredictor();

        try {
            allUsersTreeMap = dsl.loadDataSet("data/userItem.data", ",");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not init: ", e);
        }

        printQuestionNumber(5);

        User targetUser = allUsersTreeMap.get(7);
        System.out.println("Target user 7, threshold 0.35");
        System.out.println("EUCLIDEAN");
        Map<User, Double> nearestNeighbours = neighbourPredictor.nearestNeighbourAlgorithm(targetUser, allUsersTreeMap, EUCLIDEAN, 3, true);
        printNearestNeighbourWithSimilarity(nearestNeighbours, 0);
        System.out.println("COSINE");
        nearestNeighbours = neighbourPredictor.nearestNeighbourAlgorithm(targetUser, allUsersTreeMap, COSINE, 3, false, 0.35);
        printNearestNeighbourWithSimilarity(nearestNeighbours, 0);
        System.out.println("PEARSON");
        nearestNeighbours = neighbourPredictor.nearestNeighbourAlgorithm(targetUser, allUsersTreeMap, PEARSON, 3, false);
        printNearestNeighbourWithSimilarity(nearestNeighbours, 0);

        printQuestionNumber(6);

        targetUser = allUsersTreeMap.get(3);
        nearestNeighbours = neighbourPredictor.nearestNeighbourAlgorithm(targetUser, allUsersTreeMap, PEARSON, 3, false);
        printNearestNeighbourWithSimilarity(nearestNeighbours, 4);

        printQuestionNumber(8);

        targetUser = allUsersTreeMap.get(7);
        nearestNeighbours = neighbourPredictor.nearestNeighbourAlgorithm(targetUser, allUsersTreeMap, PEARSON, 3, false);
        double predictedRating = ratingPredictor.getPredictedRating(101, nearestNeighbours);
        printPredictedRating(101, predictedRating);

        predictedRating = ratingPredictor.getPredictedRating(103, nearestNeighbours);
        printPredictedRating(103, predictedRating);

        predictedRating = ratingPredictor.getPredictedRating(106, nearestNeighbours);
        printPredictedRating(106, predictedRating);

        targetUser = allUsersTreeMap.get(4);
        nearestNeighbours = neighbourPredictor.nearestNeighbourAlgorithm(targetUser, allUsersTreeMap, PEARSON, 3, false);
        predictedRating = ratingPredictor.getPredictedRating(101, nearestNeighbours);
        printPredictedRating(101, predictedRating);

        printQuestionNumber(9);

        targetUser = allUsersTreeMap.get(7);
        targetUser.addMovieRating(106, 2.8f);
        System.out.println("Added rating 2.8 for user seven for movie id 106");
        nearestNeighbours = neighbourPredictor.nearestNeighbourAlgorithm(targetUser, allUsersTreeMap, PEARSON, 3, false);
        predictedRating = ratingPredictor.getPredictedRating(101, nearestNeighbours);
        printPredictedRating(101, predictedRating);

        predictedRating = ratingPredictor.getPredictedRating(103, nearestNeighbours);
        printPredictedRating(103, predictedRating);

        targetUser.addMovieRating(106, 5f);
        System.out.println("Changed rating to 5 for user seven for movie id 106");
        nearestNeighbours = neighbourPredictor.nearestNeighbourAlgorithm(targetUser, allUsersTreeMap, PEARSON, 3, false);
        predictedRating = ratingPredictor.getPredictedRating(101, nearestNeighbours);
        printPredictedRating(101, predictedRating);
        predictedRating = ratingPredictor.getPredictedRating(103, nearestNeighbours);
        printPredictedRating(103, predictedRating);

        printQuestionNumber(11);
        System.out.println("Using the 100k dataset now");

        try {
            allUsersTreeMap100k = dsl.loadDataSet("data/MovieLens100k/u.data", "\\t");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not init: ", e);
        }

        targetUser = allUsersTreeMap100k.get(186);
        nearestNeighbours = neighbourPredictor.nearestNeighbourAlgorithm(targetUser, allUsersTreeMap100k, PEARSON, 25, false, 0.35);
        Map<Integer, Double> predictedRatingTopN = ratingPredictor.getTopNRatings(nearestNeighbours, 0, 8);
        printMovieIdWithPredictedRating(predictedRatingTopN);

        printQuestionNumber(12);

        predictedRatingTopN = ratingPredictor.getTopNRatings(nearestNeighbours, 3, 8);
        printMovieIdWithPredictedRating(predictedRatingTopN);
    }

    private static void printNearestNeighbourWithSimilarity(Map<User, Double> nearestNeighboursRating, int userId) {
        Set<User> set = nearestNeighboursRating.keySet();
        for (Iterator<User> i = set.iterator(); i.hasNext(); ) {
            User neighbour = i.next();
            int neighbourId = neighbour.getUserId();
            if (neighbourId == userId || userId == 0) {
                double similarity = nearestNeighboursRating.get(neighbour);
                System.out.println("Nearest neighbour " + neighbourId + " with similarity: " + similarity);
            }
        }
    }

    private static void printMovieIdWithPredictedRating(Map<Integer, Double> predictedRatings) {
        int counter = 0;

        for (Map.Entry<Integer, Double> me : predictedRatings.entrySet()) {
            counter++;
            int movieId = me.getKey();
            double predictedRating = me.getValue();

            System.out.println("Recommendation " + counter + ": " + movieId + " with predicted rating: " + predictedRating);
        }
    }

    private static void printPredictedRating(int movieId, double rating) {
        System.out.println("Predicted rating for item " + movieId + " : " + rating);
    }

    private static void printQuestionNumber(int questionNumber) {
        String seperator = "----------------------------";

        System.out.println(seperator);
        System.out.println("Part 1: question " + questionNumber);
        System.out.println(seperator);
    }
}

