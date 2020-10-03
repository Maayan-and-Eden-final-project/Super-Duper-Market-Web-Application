package systemInfoContainers;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class SingleOrderStoreInfo implements Containable{
    private Integer storeId;
    private String storeName;
    private Integer ppk;
    private Double distanceFromCustomer;
    private Double customerShippingCost;
    private Map<Pair<Integer,Boolean>, OrderStoreItemInfo> itemIdMapToProgressItem;


    public SingleOrderStoreInfo() {
        this.itemIdMapToProgressItem = new HashMap<>();
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getPpk() {
        return ppk;
    }

    public void setPpk(Integer ppk) {
        this.ppk = ppk;
    }

    public Double getDistanceFromCustomer() {
        return distanceFromCustomer;
    }

    public void setDistanceFromCustomer(Double distanceFromCustomer) {
        this.distanceFromCustomer = distanceFromCustomer;
    }

    public Double getCustomerShippingCost() {
        return customerShippingCost;
    }

    public void setCustomerShippingCost(Double customerShippingCost) {
        this.customerShippingCost = customerShippingCost;
    }

    public Map<Pair<Integer, Boolean>, OrderStoreItemInfo> getItemIdMapToProgressItem() {
        return itemIdMapToProgressItem;
    }

    public void setItemIdMapToProgressItem(Map<Pair<Integer, Boolean>, OrderStoreItemInfo> itemIdMapToProgressItem) {
        this.itemIdMapToProgressItem = itemIdMapToProgressItem;
    }
}
