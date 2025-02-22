package sdm.sdmElements;


import javafx.util.Pair;

import java.awt.*;
import java.io.Serializable;
import java.util.*;

public class Order implements Serializable, Cloneable {
    private String orderDate;
    private String orderPurchaser;
    private int orderId;
    private String storeName;
    private Integer storeId;
    private Double distance;
    private Point purchaserLocation;
    private float totalItemsPrice;
    private double deliveryCost;
    private double totalOrderPrice;
    private int totalNumberOfItems;
    private Map<Pair<Integer,Boolean>,OrderedItem> itemIdPairToItems;
    private int dynamicOrderId = -1;

    public Order (){
        this.itemIdPairToItems = new HashMap<>();
    }

    public Order(String orderDate, int orderId, Map<Pair<Integer,Boolean>,OrderedItem> itemIdMapToItems, float totalItemsPrice, float deliveryCost, float totalOrderPrice, Double distance) {
        this.orderDate = orderDate;
        this.orderId = orderId;
        this.distance = distance;
        this.itemIdPairToItems = itemIdMapToItems;
        this.totalItemsPrice = totalItemsPrice;
        this.deliveryCost = deliveryCost;
        this.totalOrderPrice = totalOrderPrice;
    }

    public Point getPurchaserLocation() {
        return purchaserLocation;
    }

    public void setPurchaserLocation(Point purchaserLocation) {
        this.purchaserLocation = purchaserLocation;
    }

    public String getOrderPurchaser() {
        return orderPurchaser;
    }

    public void setOrderPurchaser(String orderPurchaser) {
        this.orderPurchaser = orderPurchaser;
    }

    public int getDynamicOrderId() {
        return dynamicOrderId;
    }

    public void setDynamicOrderId(int dynamicOrderId) {
        this.dynamicOrderId = dynamicOrderId;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public Order clone() throws CloneNotSupportedException {
        Order order = (Order) super.clone();
        Map<Pair<Integer,Boolean>,OrderedItem> itemIdPairToItemsCopy = new HashMap<>();
        order.setOrderDate(orderDate);

        for(Pair<Integer,Boolean> itemIdPair : itemIdPairToItems.keySet()) {
            Pair<Integer,Boolean> itemIdPairCopy = new Pair<>(itemIdPair.getKey(),itemIdPair.getValue());
            
            itemIdPairToItemsCopy.put(itemIdPairCopy, itemIdPairToItems.get(itemIdPair).clone());
        }
        order.setItemIdPairToItems(itemIdPairToItemsCopy);

        return order;
    }


    public Map<Pair<Integer,Boolean>, OrderedItem> getItemIdPairToItems() {
        return itemIdPairToItems;
    }

    public void setItemIdPairToItems(Map<Pair<Integer,Boolean>, OrderedItem> itemIdPairToItems) {
        this.itemIdPairToItems = itemIdPairToItems;
    }

    public int getTotalNumberOfItems() {
        return totalNumberOfItems;
    }

    public void setTotalNumberOfItems(int totalNumberOfItems) {
        this.totalNumberOfItems = totalNumberOfItems;
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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public float getTotalItemsPrice() {
        return totalItemsPrice;
    }

    public void setTotalItemsPrice(float totalItemsPrice) {
        this.totalItemsPrice = totalItemsPrice;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public double getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(double totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId;
    }

   /* public int calcNumberOfTotalItems() {
        int numberOfTotalItems = 0;
        for(Item item : itemAndQuantity.keySet()) {
            if(item.getPurchaseCategory().compareTo(PurchaseCategory.WEIGHT) == 0) {
                numberOfTotalItems++;
            } else {
                numberOfTotalItems += itemAndQuantity.get(item);
            }
        }
        return numberOfTotalItems;
    }*/

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return "sdm.sdmElements.Order{" +
                "orderDate=" + orderDate +
                ", orderId=" + orderId +
                ", totalPrice=" + totalItemsPrice +
                ", deliveryCost=" + deliveryCost +
                ", totalOrderPrice=" + totalOrderPrice +
                '}';
    }
}
