package systemInfoContainers;


import java.util.ArrayList;
import java.util.List;

public class SingleOrderStoreInfo implements Containable{
    private Integer storeId;
    private String storeName;
    private Integer ppk;
    private Double distanceFromCustomer;
    private Double customerShippingCost;
    private List<OrderStoreItemInfo> progressItems;


    public SingleOrderStoreInfo() {
        this.progressItems = new ArrayList<>();
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

    public List<OrderStoreItemInfo> getProgressItems() {
        return progressItems;
    }

    public void setProgressItems(List<OrderStoreItemInfo> progressItems) {
        this.progressItems = progressItems;
    }
}
