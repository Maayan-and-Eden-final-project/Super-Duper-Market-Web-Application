package systemInfoContainers;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class OrderSummeryContainer implements Containable {
    private Map<Integer, SingleOrderStoreInfo> storeIdToStoreInfo;
    private Float totalOrderCostWithoutShipping;
    private Double totalShippingCost;
    private Double totalOrderCost;
    private Integer dynamicOrderId;
    private Point purchaserLocation;

    public OrderSummeryContainer() {
        this.storeIdToStoreInfo = new HashMap<>();
    }

    public void setDynamicOrderId(Integer dynamicOrderId) {
        this.dynamicOrderId = dynamicOrderId;
    }

    public Integer getDynamicOrderId() {
        return dynamicOrderId;
    }

    public Map<Integer, SingleOrderStoreInfo> getStoreIdToStoreInfo() {
        return storeIdToStoreInfo;
    }

    public void setStoreIdToStoreInfo(Map<Integer, SingleOrderStoreInfo> storeIdToStoreInfo) {
        this.storeIdToStoreInfo = storeIdToStoreInfo;
    }

    public Point getPurchaserLocation() {
        return purchaserLocation;
    }

    public void setPurchaserLocation(Point purchaserLocation) {
        this.purchaserLocation = purchaserLocation;
    }

    public Float getTotalOrderCostWithoutShipping() {
        return totalOrderCostWithoutShipping;
    }

    public void setTotalOrderCostWithoutShipping(Float totalOrderCostWithoutShipping) {
        this.totalOrderCostWithoutShipping = totalOrderCostWithoutShipping;
    }

    public Double getTotalShippingCost() {
        return totalShippingCost;
    }

    public void setTotalShippingCost(Double totalShippingCost) {
        this.totalShippingCost = totalShippingCost;
    }

    public double getTotalOrderCost() {
        return totalOrderCost;
    }

    public void setTotalOrderCost(Double totalOrderCost) {
        this.totalOrderCost = totalOrderCost;
    }
}
