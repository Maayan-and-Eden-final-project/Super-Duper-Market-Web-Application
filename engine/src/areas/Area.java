package areas;

import sdm.sdmElements.Item;
import sdm.sdmElements.OrderedItem;
import sdm.sdmElements.Store;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Area {
    private String areaName;
    private Map<Integer, Store> storeIdToStore;
    private Map<Integer, Item> itemIdToItem;
    private int dynamicIdCounter = 0;

    public Area( String areaName) {
        this.areaName = areaName;
        this.storeIdToStore = new HashMap<>();
        this.itemIdToItem = new HashMap<>();
    }

    public String getAreaName() {
        return areaName;
    }

    public Map<Integer, Store> getStoreIdToStore() {
        return Collections.unmodifiableMap(storeIdToStore);
    }

    public Map<Integer, Item> getItemIdToItem() {
        return Collections.unmodifiableMap(itemIdToItem);
    }

    public void addItem(Item item) {
        itemIdToItem.put(item.getId(),item);
    }

    public void addStore(Store store) {
        storeIdToStore.put(store.getId(),store);
    }

    public void addItemToArea(Item item) {
        itemIdToItem.put(item.getId(),item);
    }

    public void addStoreToArea(Store store) {
        storeIdToStore.put(store.getId(),store);
    }

    public synchronized int getNextDynamicOrderId() {
        return ++dynamicIdCounter;
    }

}
