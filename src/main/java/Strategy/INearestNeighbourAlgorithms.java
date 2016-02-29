package Strategy;

import datastructure.User;

/**
 * Created by armindo on 29-2-2016.
 */
public interface INearestNeighbourAlgorithms {
    float CalculatePearson(User userOne, User userTwo);
    float CalculateCosine(float ratingOne, float ratingTwo);
    float CalculateEuclidean(float ratingOne, float ratingTwo);
}
