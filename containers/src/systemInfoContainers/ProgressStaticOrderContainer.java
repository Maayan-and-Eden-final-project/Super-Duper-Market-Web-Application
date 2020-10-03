package systemInfoContainers;

import sdm.sdmElements.Store;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ProgressStaticOrderContainer implements Containable,Orderable {
    private Double totalShippingCost;
    private Map<Integer, StoreItemInformation> itemIdToOrderInfo;
    private Integer storePPK;
    private double distance;


    public ProgressStaticOrderContainer() {
        this.itemIdToOrderInfo = new HashMap<>();
    }

    public Map<Integer, StoreItemInformation> getItemIdToOrderInfo() {
        return itemIdToOrderInfo;
    }

    public void setItemIdToOrderInfo(Map<Integer, StoreItemInformation> itemIdToOrderInfo) {
        this.itemIdToOrderInfo = itemIdToOrderInfo;
    }

    public Double getTotalShippingCost() {
        return totalShippingCost;
    }

    public void calcDistPpkAndShippingCost(Point userLocation, Store store) {
        this.distance = store.getLocation().distance(userLocation.x,userLocation.y);
        this.storePPK = store.getDeliveryPPK();
        this.totalShippingCost = distance * storePPK;
    }

    public void calcDistPpkAndShippingCost(Double totalShippingCost) {
        this.totalShippingCost = totalShippingCost;
    }

    public Integer getStorePPK() {
        return storePPK;
    }

    public void setStorePPK(Integer storePPK) {
        this.storePPK = storePPK;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isEmptyOrder() {
        int counter = 0;
        for (StoreItemInformation item : itemIdToOrderInfo.values()) {
            if(item.getAmount() == null) {
                counter++;
            }
        }
        return counter == itemIdToOrderInfo.size();
    }

    @Override
    public String toString() {
        StringBuilder orderSummeryString = new StringBuilder();
        orderSummeryString.append(String.format("~~~~~~~ORDER SUMMERY~~~~~~~%n%n"));

        for(StoreItemInformation orderInformation : itemIdToOrderInfo.values()) {
            if (orderInformation.getAmount() != null) {
                orderSummeryString.append(String.format(orderInformation.getItem().toString()));
                orderSummeryString.append(String.format("Price: " + orderInformation.getItemPrice() + "%n"));
                orderSummeryString.append(String.format("Amount ordered: %.2f%n",orderInformation.getAmount()));
                orderSummeryString.append(String.format("Total price for this item: %.2f%n%n" , orderInformation.getTotalItemPrice()));
            }
        }
        orderSummeryString.append(String.format("User distance from store: " + String.format("%.2f",distance) + "%n"));
        orderSummeryString.append(String.format("Store's PPK: " + storePPK + "%n"));
        orderSummeryString.append(String.format("Price of shipping: " + String.format("%.2f" ,totalShippingCost) + "%n"));

        return orderSummeryString.toString();
    }
}
