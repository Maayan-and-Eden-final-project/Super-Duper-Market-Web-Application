package systemInfoContainers;

import sdm.sdmElements.Item;
import java.util.HashMap;
import java.util.Map;

public class ItemsContainer implements Containable {
    private Map<Integer,ItemInfo> itemIdToItemInfo;

    public ItemsContainer() {
        this.itemIdToItemInfo = new HashMap<>();
    }


    public Map<Integer, ItemInfo> getItemIdToItemInfo() {
        return itemIdToItemInfo;
    }

    public void setItemIdToItemInfo(Map<Integer, ItemInfo> itemIdToItemInfo) {
        this.itemIdToItemInfo = itemIdToItemInfo;
    }

   /* @Override
    public String toString() {
        StringBuilder systemItemsInfo = new StringBuilder();
        Integer sellingStores;
        Float averagePrice;
        Float totalPurchases;
        systemItemsInfo.append(String.format("~~~~~~~ITEMS IN SYSTEM~~~~~~~%n%n"));

        for (ItemInfo item : itemIdToItemInfo.values()) {
            systemItemsInfo.append(item.toString());
            sellingStores = itemIdToNumOfSellingStores.get(item.getId());
            averagePrice = itemIdToAvgPrice.get(item.getId());
            totalPurchases = itemIdToTotalPurchases.get(item.getId());

            systemItemsInfo.append(String.format("Number of stores that sell this item: " + sellingStores + "%n"));
            systemItemsInfo.append(String.format("Average price of item: " + String.format("%.2f", averagePrice) + "%n"));
            systemItemsInfo.append(String.format("Quantity of items sold: %.2f%n%n", totalPurchases));
        }

        return systemItemsInfo.toString();
    }*/
}




