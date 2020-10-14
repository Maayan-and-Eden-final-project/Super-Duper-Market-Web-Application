package systemInfoContainers.webContainers;

import java.awt.*;

public class SingleDynamicStoreContainer {
    private Integer storeId;
    private String storeName;
    private Integer ppk;
    private Point location;
    private Double distanceFromCustomer;
    private Double customerShippingCost;
    private Integer numOfDifferentItem;
    private Float totalItemsCost;

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

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
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

    public Integer getNumOfDifferentItem() {
        return numOfDifferentItem;
    }

    public void setNumOfDifferentItem(Integer numOfDifferentItem) {
        this.numOfDifferentItem = numOfDifferentItem;
    }

    public Float getTotalItemsCost() {
        return totalItemsCost;
    }

    public void setTotalItemsCost(Float totalItemsCost) {
        this.totalItemsCost = totalItemsCost;
    }
}
