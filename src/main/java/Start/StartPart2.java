package start;

import datastructure.DataSetLoaderPart2;
import datastructure.Item;
import prediction.ItemDeviation;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartPart2 {
    private StartPart2(){

    }

    public static void main(String[] args) {
        DataSetLoaderPart2 dataSetLoaderPart2 = new DataSetLoaderPart2();
        Map<Integer, Item> allItemsTreeMap = new TreeMap<>();
        Map<Integer, Item> allItemsTreeMap100k = new TreeMap<>();
        Map<Integer, Map<Integer, ItemDeviation>> itemWithItemDeviations = new TreeMap<>();
        Map<Integer, ItemDeviation> calculatedItemDeviations = new TreeMap<>();
        Logger logger = Logger.getLogger("myLogger");

        try {
            allItemsTreeMap = dataSetLoaderPart2.loadDataSet("data/userItem.data", ",");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not init: ", e);
        }

        Item itemI = allItemsTreeMap.get(101);
        for (Map.Entry<Integer, Item> me : allItemsTreeMap.entrySet()) {
            ItemDeviation itemDeviation = new ItemDeviation();

            Item itemJ = me.getValue();
            itemDeviation.calculateItemDeviation(itemI, itemJ, false);

            calculatedItemDeviations.put(itemJ.getItemId(), itemDeviation);
            itemWithItemDeviations.put(itemI.getItemId(), calculatedItemDeviations);
        }

        System.out.println(itemWithItemDeviations.toString());
    }

}
