package strategy;

import datastructure.User;

public interface INearestNeighbourAlgorithm {
    double calculate(User userOne, User userTwo);
}
