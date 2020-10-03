package systemInfoContainers;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ProgressDynamicOrderContainer implements Containable,Orderable {
   private Map<Integer, ProgressOrderItem> itemIdToOrderItem;
   private Point userLocation;


    public Point getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(Point userLocation) {
        this.userLocation = userLocation;
    }

    public ProgressDynamicOrderContainer() {
        this.itemIdToOrderItem = new HashMap<>();
    }

    public Map<Integer, ProgressOrderItem> getItemIdToOrderItem() {
        return itemIdToOrderItem;
    }

    public void setItemIdToOrderItem(Map<Integer, ProgressOrderItem> itemIdToOrderItem) {
        this.itemIdToOrderItem = itemIdToOrderItem;
    }

    public boolean isEmptyOrder() {
        return itemIdToOrderItem.isEmpty();
    }

    @Override
    public String toString() {
       StringBuilder dynamicOrder = new StringBuilder();
       itemIdToOrderItem.values().forEach(item -> dynamicOrder.append(String.format("Item id: " + item.getItemId() + "%nItem name: "
               + item.getItemName() + "%nPurchase category: " + item.getPurchaseCategory() + "%nAmount: %.2f%n", item.getAmount())));
       return dynamicOrder.toString();
    }
}
