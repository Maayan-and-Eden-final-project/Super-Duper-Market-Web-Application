package utils;

import java.io.*;
import java.util.*;

public class Utils {

    public static void saveLastStoresOrderId(String xmlFileName, Map<Integer,Integer> storeIdToMaxOrderId) throws IOException {

        try {
            FileWriter writer = new FileWriter(xmlFileName + "LastStoresOrderId.txt");

            for(Integer storeId : storeIdToMaxOrderId.keySet()) {
                writer.write(String.format(storeId + " " + storeIdToMaxOrderId.get(storeId) + "%n"));
            }
            writer.close();
        }catch (Exception e) {
            throw e;
        }
    }

    public static Map<Integer,Integer> loadLastStoresOrderId(String xmlFileName) throws FileNotFoundException {
        Map<Integer,Integer> storeIdToMaxOrderId = new HashMap<>();
        File file = new File(xmlFileName + "LastStoresOrderId.txt");

        Scanner reader = new Scanner(file);
        Integer storeId;
        Integer maxOrderId;

        while (reader.hasNextInt()) {
            storeId = reader.nextInt();
            maxOrderId = reader.nextInt();
            storeIdToMaxOrderId.put(storeId,maxOrderId);
        }
        return storeIdToMaxOrderId;
    }

}
