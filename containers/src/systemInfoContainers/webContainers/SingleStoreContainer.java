package systemInfoContainers.webContainers;

import sdm.sdmElements.Discount;
import sdm.sdmElements.Item;
import sdm.sdmElements.Order;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SingleStoreContainer {
    private String ownerName;
    private int storeId;
    private String storeName;
    private int deliveryPPK=0;
    private Point location;
    private float  purchasedItemsCost=0;
    private double totalDeliveryPayment=0;
    private int numOfOrders=0;
    List<SingleStoreItemContainer> storeItems;

    public SingleStoreContainer() {
        this.storeItems = new ArrayList<>();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getDeliveryPPK() {
        return deliveryPPK;
    }

    public void setDeliveryPPK(int deliveryPPK) {
        this.deliveryPPK = deliveryPPK;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public float getPurchasedItemsCost() {
        return purchasedItemsCost;
    }

    public void setPurchasedItemsCost(float purchasedItemsCost) {
        this.purchasedItemsCost = purchasedItemsCost;
    }

    public double getTotalDeliveryPayment() {
        return totalDeliveryPayment;
    }

    public void setTotalDeliveryPayment(double totalDeliveryPayment) {
        this.totalDeliveryPayment = totalDeliveryPayment;
    }

    public int getNumOfOrders() {
        return numOfOrders;
    }

    public void setNumOfOrders(int numOfOrders) {
        this.numOfOrders = numOfOrders;
    }

    public List<SingleStoreItemContainer> getStoreItems() {
        return storeItems;
    }

    public void setStoreItems(List<SingleStoreItemContainer> storeItems) {
        this.storeItems = storeItems;
    }
}
