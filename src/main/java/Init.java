import datastructure.DataSetLoader;
import datastructure.User;

import java.io.IOException;
import java.util.TreeMap;

/**
 * Created by armindo on 29-2-2016.
 */
public class Init {

    public static void main(String[] args) {
        DataSetLoader dsl = new DataSetLoader();
        TreeMap TM = new TreeMap<>();
        try {
           TM = dsl.LoadDataSet();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Get rating of two users on one movie

        User bla = (User) TM.get(1);
        float ratingkje = bla.getMovieRating(106);
    }
}
