package Start;

import datastructure.DataSetLoader;
import datastructure.User;

import java.io.IOException;
import java.util.TreeMap;
import java.util.logging.*;

/**
 * Created by armindo on 29-2-2016.
 */
public class Init {

    private Init(){}

    public static void main(String[] args) {
        DataSetLoader dsl = new DataSetLoader();
        TreeMap TM = new TreeMap<>();
        Logger logger = Logger.getLogger("myLogger");
        try {
           TM = dsl.LoadDataSet();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not init: ", e);
        }

        //Get rating of two users on one movie

        User UserOne = (User) TM.get(1);
        float UserOneRating = UserOne.getMovieRating(106);
    }
}
