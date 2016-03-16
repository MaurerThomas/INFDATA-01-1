package start;

import datastructure.DataSetLoader;
import datastructure.User;
import prediction.RatingPredictor;
import prediction.NeighbourPredictor;
import strategy.Cosine;
import strategy.Euclidean;
import strategy.INearestNeighbourAlgorithm;
import strategy.Pearson;

import java.io.IOException;
import java.util.*;
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
        NeighbourPredictor neighbourPredictor = new NeighbourPredictor();

        try {
            allUsersTreeMap = dsl.loadDataSet();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not init: ", e);
        }

        User targetUser = allUsersTreeMap.get(7);
        nearestNeighboursRatingEuclidean = neighbourPredictor.nearestNeighbourAlgorithm(targetUser, allUsersTreeMap, EUCLIDEAN, 3, true);
        nearestNeighboursRatingCosine = neighbourPredictor.nearestNeighbourAlgorithm(targetUser, allUsersTreeMap, COSINE, 3, false, 0.35);
        nearestNeighboursRatingPearson = neighbourPredictor.nearestNeighbourAlgorithm(targetUser, allUsersTreeMap, PEARSON, 3, false);


        //ratingPredictor.getPredictedRating(targetUser, 101, nearestNeighboursRatingPearson);
        //ratingPredictor.getPredictedRating(targetUser, 103, nearestNeighboursRatingPearson);
        //ratingPredictor.getPredictedRating(targetUser, 106, nearestNeighboursRatingPearson);


        String seperator = "----------------------------";
        System.out.println(seperator);
        System.out.println("Part 1: question 5");
        System.out.println(seperator);
        System.out.println("Target user 7, threshold 0.35");
        System.out.println("PEARSON");

        Set<User> pearsonSet = nearestNeighboursRatingPearson.keySet();
        for(Iterator<User> i = pearsonSet.iterator(); i.hasNext();){
            User neighbour = i.next();
            int userId = neighbour.getUserId();
            double similarity = nearestNeighboursRatingPearson.get(neighbour);

            System.out.println("Nearest neighbour " + userId + ", with similarity: " + similarity);
        }

        System.out.println("COSINE");
        Set<User> cosineSet = nearestNeighboursRatingCosine.keySet();
        for(Iterator<User> i = cosineSet.iterator(); i.hasNext();){
            User neighbour = i.next();
            int userId = neighbour.getUserId();
            double similarity = nearestNeighboursRatingCosine.get(neighbour);

            System.out.println("Nearest neighbour " + userId + ", with similarity: " + similarity);
        }

        System.out.println("EUCLIDEAN");
        Set<User> euclideanSet = nearestNeighboursRatingEuclidean.keySet();
        for(Iterator<User> i = euclideanSet.iterator(); i.hasNext();) {
            User neighbour = i.next();
            int userId = neighbour.getUserId();
            double similarity = nearestNeighboursRatingEuclidean.get(neighbour);

            System.out.println("Nearest neighbour " + userId + ", with similarity: " + similarity);
        }

        System.out.println(seperator);
        System.out.println("Part 1: question 6");
        System.out.println(seperator);

        User userThree = allUsersTreeMap.get(3);
        Map<User, Double> nearestNeighboursRatingPearsonThreeFour = neighbourPredictor.nearestNeighbourAlgorithm(userThree, allUsersTreeMap, PEARSON, 3, false);
        Set<User> pearsonSetThreeFour = nearestNeighboursRatingPearsonThreeFour.keySet();
        for(Iterator<User> i = pearsonSetThreeFour.iterator(); i.hasNext();){
            User neighbour = i.next();
            int userId = neighbour.getUserId();
            if(userId == 4){
                double similarity = nearestNeighboursRatingPearsonThreeFour.get(neighbour);
                System.out.println("User 3 and 4 have a Pearson coefficient similarity of " + similarity);
            }
        }

    }
}
