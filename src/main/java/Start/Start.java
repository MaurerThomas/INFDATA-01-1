package start;

import datastructure.DataSetLoader;
import datastructure.User;
import prediction.RatingPredictor;
import strategy.Cosine;
import strategy.Euclidean;
import strategy.INearestNeighbourAlgorithm;
import strategy.Pearson;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Start {
    private static final INearestNeighbourAlgorithm EUCLIDEAN = new Euclidean();
    private static final INearestNeighbourAlgorithm COSINE = new Cosine();
    private static final INearestNeighbourAlgorithm PEARSON = new Pearson();

    public static void main(String[] args) {
        DataSetLoader dsl = new DataSetLoader();
        Map<Integer, User> allUsersTreeMap = new TreeMap<>();
        Map<User, Double> nearestNeighboursRatingEuclidean;
        Map<User, Double> nearestNeighboursRatingCosine;
        Map<User, Double> nearestNeighboursRatingPearson;
        Logger logger = Logger.getLogger("myLogger");
        RatingPredictor ratingPredictor = new RatingPredictor();

        try {
            allUsersTreeMap = dsl.loadDataSet();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not init: ", e);
        }

        User targetUser = allUsersTreeMap.get(7);
        nearestNeighboursRatingEuclidean = targetUser.nearestNeighbourAlgorithm(targetUser, allUsersTreeMap, EUCLIDEAN, 3, true);

        nearestNeighboursRatingCosine = targetUser.nearestNeighbourAlgorithm(targetUser, allUsersTreeMap, COSINE, 3, false, 0.35);
        nearestNeighboursRatingPearson = targetUser.nearestNeighbourAlgorithm(targetUser, allUsersTreeMap, PEARSON, 3, false);


        //ratingPredictor.getPredictedRating(targetUser, 101, nearestNeighboursRatingPearson);
        //ratingPredictor.getPredictedRating(targetUser, 103, nearestNeighboursRatingPearson);
        //ratingPredictor.getPredictedRating(targetUser, 106, nearestNeighboursRatingPearson);


        String seperator = "----------------------------";
        System.out.println(seperator);
        System.out.println("Part 1: E");
        System.out.println(seperator);
        System.out.println("Target user 7, threshold 0.35");
        System.out.println("PEARSON");
        int pearsonCoefficientCounter = 1;
        for(double pearsonCoefficient : nearestNeighboursRatingPearson.values()){
            System.out.println("Nearest neighbour " + pearsonCoefficientCounter + ", similarity: " + pearsonCoefficient);
            pearsonCoefficientCounter++;
        }

        System.out.println("COSINE");
        int cosineCoefficientCounter = 1;
        for(double cosineCoefficient : nearestNeighboursRatingCosine.values()){
            System.out.println("Nearest neighbour " + cosineCoefficientCounter + ", similarity: " + cosineCoefficient);
            cosineCoefficientCounter++;
        }

        System.out.println("EUCLIDEAN");
        int euclideanCoefficientCounter = 1;
        for(double euclideanCoefficient : nearestNeighboursRatingEuclidean.values()) {
            System.out.println("Nearest neighbour " + euclideanCoefficientCounter + ", similarity: " + euclideanCoefficient);
            euclideanCoefficientCounter++;
        }

        System.out.println(seperator);
        System.out.println("Part 1: G");
        System.out.println(seperator);
    }
}
