package start;

import datastructure.DataSetLoader;
import datastructure.User;
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
        Map<Integer, Double> nearestNeighboursRatingEuclidean;
        Map<Integer, Double> nearestNeighboursRatingCosine;
        Map<Integer, Double> nearestNeighboursRatingPearson;
        Logger logger = Logger.getLogger("myLogger");

        try {
            allUsersTreeMap = dsl.loadDataSet();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not init: ", e);
        }

        User userSeven = allUsersTreeMap.get(7);
        nearestNeighboursRatingEuclidean = userSeven.calculateUsers(userSeven, allUsersTreeMap, EUCLIDEAN);
        System.out.println("Euclidean: " + nearestNeighboursRatingEuclidean.toString());

        nearestNeighboursRatingCosine = userSeven.calculateUsers(userSeven, allUsersTreeMap, COSINE, 0.75);
        System.out.println("Cosine: " + nearestNeighboursRatingCosine);

        nearestNeighboursRatingPearson = userSeven.calculateUsers(userSeven, allUsersTreeMap, PEARSON);
        System.out.println("Pearson: " + nearestNeighboursRatingPearson);
    }
}
