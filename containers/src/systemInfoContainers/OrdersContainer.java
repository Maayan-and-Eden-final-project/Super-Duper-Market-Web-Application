package systemInfoContainers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrdersContainer implements Containable {
    private Integer orderId;
    private Date orderDate;
    private Integer storeId;
    private String storeName;
    private Integer numberOfDifferentItems;
    private Integer numberOfItemsInOrder;
    private Float totalItemsCost;
    private Double shippingCost;
    private Float totalOrderCost;


    @Override
    public String toString() {
        StringBuilder systemOrdersInfo = new StringBuilder();
        SimpleDateFormat dateFormat =  new SimpleDateFormat("dd/MM-hh:mm");

        systemOrdersInfo.append("%nOrder Id: " + orderId + "%n");
        systemOrdersInfo.append("Order Date: " + dateFormat.format(orderDate) + "%n");
        systemOrdersInfo.append("Store Id: " + storeId + "%n");
        systemOrdersInfo.append("Store Name: " + storeName + "%n");
        systemOrdersInfo.append("Number Of Different Items: " + numberOfDifferentItems + "%n");
        systemOrdersInfo.append("Total Number Of Items: " + numberOfItemsInOrder + "%n");
        systemOrdersInfo.append("Total Items Cost: " + String.format("%.2f",totalItemsCost) + "%n");
        systemOrdersInfo.append("Delivery Cost: " + String.format("%.2f",shippingCost) + "%n");
        systemOrdersInfo.append("Total Order Price: " + String.format("%.2f",totalOrderCost) + "%n");

        return  systemOrdersInfo.toString();
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getNumberOfDifferentItems() {
        return numberOfDifferentItems;
    }

    public void setNumberOfDifferentItems(Integer numberOfDifferentItems) {
        this.numberOfDifferentItems = numberOfDifferentItems;
    }

    public Integer getNumberOfItemsInOrder() {
        return numberOfItemsInOrder;
    }

    public void setNumberOfItemsInOrder(Integer numberOfItemsInOrder) {
        this.numberOfItemsInOrder = numberOfItemsInOrder;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getStoresId() {
        return storeId;
    }

    public void setStoresId(Integer storesId) {
        this.storeId = storesId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Float getTotalItemsCost() {
        return totalItemsCost;
    }

    public void setTotalItemsCost(Float totalItemsCost) {
        this.totalItemsCost = totalItemsCost;
    }

    public Double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public Float getTotalOrderCost() {
        return totalOrderCost;
    }

    public void setTotalOrderCost(Float totalOrderCost) {
        this.totalOrderCost = totalOrderCost;
    }

}
