package sdm.sdmElements;


import utils.IntegerToBooleanPair;

import java.io.Serializable;
import java.util.*;

public class Order implements Serializable, Cloneable {
    private Date orderDate;
    private int orderId;
    private String storeName;
    private Integer storeId;
    private Double distance;
    private float totalItemsPrice;
    private double deliveryCost;
    private double totalOrderPrice;
    private int totalNumberOfItems;
    private Map<IntegerToBooleanPair,OrderedItem> itemIdPairToItems;
    private int dynamicOrderId = -1;

    public Order (){
        this.itemIdPairToItems = new HashMap<>();
    }

    public Order(Date orderDate, int orderId, Map<IntegerToBooleanPair,OrderedItem> itemIdMapToItems, float totalItemsPrice, float deliveryCost, float totalOrderPrice, Double distance) {
        this.orderDate = orderDate;
        this.orderId = orderId;
        this.distance = distance;
        this.itemIdPairToItems = itemIdMapToItems;
        this.totalItemsPrice = totalItemsPrice;
        this.deliveryCost = deliveryCost;
        this.totalOrderPrice = totalOrderPrice;
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
        Map<IntegerToBooleanPair,OrderedItem> itemIdPairToItemsCopy = new HashMap<>();
        Date orderDate = (Date)this.orderDate.clone();
        order.setOrderDate(orderDate);

        for(IntegerToBooleanPair itemIdPair : itemIdPairToItems.keySet()) {
            IntegerToBooleanPair itemIdPairCopy = new IntegerToBooleanPair(itemIdPair.getKey(),itemIdPair.getValue());
            
            itemIdPairToItemsCopy.put(itemIdPairCopy, itemIdPairToItems.get(itemIdPair).clone());
        }
        order.setItemIdPairToItems(itemIdPairToItemsCopy);

        return order;
    }


    public Map<IntegerToBooleanPair, OrderedItem> getItemIdPairToItems() {
        return itemIdPairToItems;
    }

    public void setItemIdPairToItems(Map<IntegerToBooleanPair, OrderedItem> itemIdPairToItems) {
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

    /*public Map<Item, Float> getItemAndQuantity() {
        return itemAndQuantity;
    }

    public void setItemAndQuantity(Map<Item, Float> itemAndQuantity) {
        this.itemAndQuantity = itemAndQuantity;
    }*/

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
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
