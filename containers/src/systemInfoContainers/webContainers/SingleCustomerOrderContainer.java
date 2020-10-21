package systemInfoContainers.webContainers;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SingleCustomerOrderContainer {
    private Integer orderId;
    private String orderDate;
    private Point customerLocation;
    private Integer numOfOrderedStores;
    private Integer numberOfDifferentItems;
    private Float totalItemsCost;
    private Double shippingCost;
    private Float totalOrderCost;
    private List<SingleOrderItemContainer> items;

    public SingleCustomerOrderContainer() {
        this.items = new ArrayList<>();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Point getCustomerLocation() {
        return customerLocation;
    }

    public void setCustomerLocation(Point customerLocation) {
        this.customerLocation = customerLocation;
    }

    public Integer getNumOfOrderedStores() {
        return numOfOrderedStores;
    }

    public void setNumOfOrderedStores(Integer numOfOrderedStores) {
        this.numOfOrderedStores = numOfOrderedStores;
    }

    public Integer getNumberOfDifferentItems() {
        return numberOfDifferentItems;
    }

    public void setNumberOfDifferentItems(Integer numberOfDifferentItems) {
        this.numberOfDifferentItems = numberOfDifferentItems;
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

    public List<SingleOrderItemContainer> getItems() {
        return items;
    }

    public void setItems(List<SingleOrderItemContainer> items) {
        this.items = items;
    }
}
