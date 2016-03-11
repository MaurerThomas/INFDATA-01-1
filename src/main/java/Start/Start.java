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
    public static final INearestNeighbourAlgorithm EUCLIDEAN = new Euclidean();
    public static final INearestNeighbourAlgorithm COSINE = new Cosine();
    public static final INearestNeighbourAlgorithm PEARSON = new Pearson();

    private Start() {
    }

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
        //nearestNeighboursRatingEuclidean = targetUser.calculateUsers(targetUser, allUsersTreeMap, EUCLIDEAN);
        //nearestNeighboursRatingCosine = targetUser.calculateUsers(targetUser, allUsersTreeMap, COSINE, 0.75);
        nearestNeighboursRatingPearson = targetUser.calculateUsers(targetUser, allUsersTreeMap, PEARSON);


        ratingPredictor.getPredictedRating(targetUser, 101, nearestNeighboursRatingPearson);
        ratingPredictor.getPredictedRating(targetUser, 103, nearestNeighboursRatingPearson);
        ratingPredictor.getPredictedRating(targetUser, 106, nearestNeighboursRatingPearson);

    }
}
