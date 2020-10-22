package systemInfoContainers.webContainers;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SingleShopOwnerOrderContainer {
    private Integer orderId;
    private String orderDate;
    private String purchaserName;
    private Point customerLocation;
    private Integer numberOfDifferentItems;
    private Float totalItemsCost;
    private Double shippingCost;
    private List<SingleOrderItemContainer> items;

    public SingleShopOwnerOrderContainer() {
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

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public Point getCustomerLocation() {
        return customerLocation;
    }

    public void setCustomerLocation(Point customerLocation) {
        this.customerLocation = customerLocation;
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

    public List<SingleOrderItemContainer> getItems() {
        return items;
    }

    public void setItems(List<SingleOrderItemContainer> items) {
        this.items = items;
    }
}
