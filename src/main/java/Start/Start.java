package start;

import datastructure.DataSetLoader;
import datastructure.User;

import java.io.IOException;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Start {
    public static final char EUCLIDEAN = 'e';
    public static final char COSINE = 'c';
    public static final char PEARSON = 'p';

    private Start() {
    }

    public static void main(String[] args) {
        DataSetLoader dsl = new DataSetLoader();
        TreeMap allUsersTreeMap = new TreeMap<>();
        TreeMap nearestNeighboursRating;
        Logger logger = Logger.getLogger("myLogger");
        try {
            allUsersTreeMap = dsl.LoadDataSet();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not init: ", e);
        }
        User userSeven = (User) allUsersTreeMap.get(6);
        nearestNeighboursRating = userSeven.calculateUsers(userSeven, allUsersTreeMap, EUCLIDEAN, 0.35);
        System.out.println(nearestNeighboursRating.toString());
    }
}
