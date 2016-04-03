package prediction;

import java.util.*;

public class TreeMapSorter {

    /**
     * Returns a sorted TreeMap from highest to lowest.
     * Source: http://stackoverflow.com/a/2581754
     *
     * @param map The TreeMap.
     * @param <K> TreeMap Key.
     * @param <V> TreeMap Value.
     * @return A sorted TreeMap.
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list =
                new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
