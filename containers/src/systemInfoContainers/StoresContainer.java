package systemInfoContainers;

import sdm.sdmElements.Store;
import java.util.HashMap;
import java.util.Map;

public class StoresContainer implements Containable{
    private Map<Integer, Store> stores;


    public StoresContainer() {
        this.stores = new HashMap<>();
    }

    public Map<Integer, Store> getStores() {
        return stores;
    }

    public void setStores(Map<Integer, Store> stores) {
        this.stores = stores;
    }

    public String storeSimpleString() {
        StringBuilder storesInfo = new StringBuilder();
        stores.values().stream().forEach(store -> storesInfo.append("%nStore id: " +store.getId() + "%nStore name: " + store.getName()  + "%nDelivery PPK: " + store.getDeliveryPPK()+"%n"));
        return storesInfo.toString();
    }

    public String getStoresItemSimpleString(Integer storeId) {
        StringBuilder itemsInStore = new StringBuilder();

        if (stores.containsKey(storeId)) {
            stores.get(storeId).getItemsAndPrices().keySet().forEach(item -> itemsInStore.append(item.toString()));
        }
        return itemsInStore.toString();
    }

    @Override
    public String toString() {
        StringBuilder informationString = new StringBuilder();

        for (Store store : stores.values()) {
            informationString.append(store.toString());
        }
        return informationString.toString();
    }

}
