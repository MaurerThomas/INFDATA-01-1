package Start;

import Strategy.Euclidean;
import datastructure.DataSetLoader;
import datastructure.User;

import java.io.IOException;
import java.util.TreeMap;
import java.util.logging.*;

/**
 * Created by armindo on 29-2-2016.
 */
public class Start {

    private Start(){}

    public static void main(String[] args) {
        DataSetLoader dsl = new DataSetLoader();
        TreeMap TM = new TreeMap<>();
        Logger logger = Logger.getLogger("myLogger");
        Euclidean ec = new Euclidean();

        try {
           TM = dsl.LoadDataSet();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not init: ", e);
        }

        //TODO: Forloop maken om de Treemap vna users door te lopen en dan distances uit te lezen.
        //Get rating of two users on one movie
        User userOne = (User) TM.get(1);
        User userTwo = (User) TM.get(2);
        User userThree = (User) TM.get(3);

        double ecRatingOneTwo = ec.Calculate(userOne,userTwo);
        double ecRatingOneThree = ec.Calculate(userOne,userThree);

        System.out.println("ecRatingOneTwo:" + ecRatingOneTwo + " ecRatingOneThree: " + ecRatingOneThree);

    }
}
