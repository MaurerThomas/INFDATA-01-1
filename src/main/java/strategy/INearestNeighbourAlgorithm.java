package strategy;

import datastructure.User;

/**
 * Created by armindo on 29-2-2016.
 */
public interface INearestNeighbourAlgorithm {
    double calculate(User userOne, User userTwo);
}
