package prediction;

import datastructure.User;

import java.util.Map;

public class LowestSimilarity {
    private Map.Entry<User, Double> mapEntry;
    private Map<User, Double> nearestNeighbours;
    private double value;

    public LowestSimilarity(Map.Entry<User, Double> mapEntry, Map<User, Double> nearestNeighbours, double value) {
        this.mapEntry = mapEntry;
        this.nearestNeighbours = nearestNeighbours;
        this.value = value;
    }

    public Map.Entry<User, Double> getMapEntry() {
        return mapEntry;
    }

    public double getValue() {
        return value;
    }

    public LowestSimilarity invoke() {
        //Loop through nearestNeighbours TreeMap and get lowest similarity
        for (Map.Entry<User, Double> entry : nearestNeighbours.entrySet()) {
            if (mapEntry == null || mapEntry.getValue() > entry.getValue()) {
                mapEntry = entry;
                value = mapEntry.getValue();
            }
        }
        return this;
    }
}
