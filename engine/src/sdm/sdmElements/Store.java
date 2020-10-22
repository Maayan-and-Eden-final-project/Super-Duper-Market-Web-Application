package sdm.sdmElements;


import javafx.util.Pair;
import systemInfoContainers.OrderStoreItemInfo;
import systemInfoContainers.OrderSummeryContainer;
import systemInfoContainers.SingleOrderStoreInfo;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Store implements Cloneable{
    private int id;
    private String name;
    private int deliveryPPK;
    private Point location;
    private Map<Integer,Integer> itemsIdAndPrices;
    private Map<Integer,Float> purchasedItems;
    private double totalDeliveryPayment;
    private List<Order> orders;
    private Map<Item,Integer> itemsAndPrices;
    private Integer orderCounter = 0;
    private List<Discount> discountList;
    private String areaName;
    private List<Feedback> feedbackList;

    @Override
    public Store clone() {
        try {
            Store newStore = (Store) super.clone();
            newStore.setLocation(new Point(this.location.x, this.location.y));
            Map<Integer, Integer> itemsIdAndPricesCopy = new HashMap<>();
            Map<Integer, Float> purchasedItemsCopy = new HashMap<>();
            List<Order> ordersCopy = new ArrayList<>();
            Map<Item, Integer> itemsAndPricesCopy = new HashMap<>();
            List<Discount> discountListCopy = new ArrayList<>();

            for (Item item : itemsAndPrices.keySet()) {
                itemsAndPricesCopy.put(item.clone(), itemsAndPrices.get(item));
            }

            for (Order order : orders) {
                ordersCopy.add(order.clone());
            }

            for (Discount discount : discountList) {
                discountListCopy.add(discount.clone());
            }

            itemsIdAndPricesCopy.putAll(this.itemsIdAndPrices);
            purchasedItemsCopy.putAll(this.purchasedItems);

            newStore.setItemsAndPrices(itemsAndPricesCopy);
            newStore.setOrders(ordersCopy);
            newStore.setItemsIdAndPrices(itemsIdAndPricesCopy);
            newStore.setPurchasedItems(purchasedItemsCopy);
            newStore.setDiscountList(discountListCopy);
            return newStore;
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }

    public Store(){}

    public Store(int id, String name, int deliveryPPK, Point location, Map<Integer, Integer> itemsAndPrices) {
        this.id = id;
        this.name = name;
        this.deliveryPPK = deliveryPPK;
        this.location = location;
        this.itemsIdAndPrices = itemsAndPrices;
        this.purchasedItems = new HashMap<>();
        this.totalDeliveryPayment = 0;
        this.orders = new ArrayList<>();
        this.itemsAndPrices = new HashMap<>();
        this.discountList = new ArrayList<>();
        this.feedbackList = new ArrayList<>();
    }

    public Store(int id, String name, int deliveryPPK, Point location, Map<Integer, Integer> itemIdAndPrices, String areaName) {
        this.id = id;
        this.name = name;
        this.deliveryPPK = deliveryPPK;
        this.location = location;
        this.itemsIdAndPrices = itemIdAndPrices;
        this.purchasedItems = new HashMap<>();
        this.totalDeliveryPayment = 0;
        this.orders = new ArrayList<>();
        this.itemsAndPrices = new HashMap<>();
        this.discountList = new ArrayList<>();
        this.feedbackList = new ArrayList<>();
        this.areaName = areaName;
    }

    public Store(int id, String name, int deliveryPPK, Point location, Map<Integer, Integer> itemIdAndPrices,Map<Item,Integer> itemToItemPrice, String areaName) {
        this.id = id;
        this.name = name;
        this.deliveryPPK = deliveryPPK;
        this.location = location;
        this.itemsIdAndPrices = itemIdAndPrices;
        this.purchasedItems = new HashMap<>();
        this.totalDeliveryPayment = 0;
        this.orders = new ArrayList<>();
        this.itemsAndPrices = itemToItemPrice;
        this.discountList = new ArrayList<>();
        this.areaName = areaName;
        this.feedbackList = new ArrayList<>();
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public String getAreaName() {
        return areaName;
    }

    public List<Discount> getDiscountList() {
        return discountList;
    }

    public void setDiscountList(List<Discount> discountList) {
        this.discountList = discountList;
    }

    public Integer getOrderCounter() {
        return orderCounter;
    }

    public void setOrderCounter(Integer orderCounter) {
        this.orderCounter = orderCounter;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Map<Item, Integer> getItemsAndPrices() {
        return itemsAndPrices;
    }

    public void setItemsAndPrices(Map<Item, Integer> itemsAndPrices) {
        this.itemsAndPrices = itemsAndPrices;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Map<Integer, Integer> getItemsIdAndPrices() {
        return itemsIdAndPrices;
    }

    public void setItemsIdAndPrices(Map<Integer, Integer> itemsIdAndPrices) {
        this.itemsIdAndPrices = itemsIdAndPrices;
    }

    public Map<Integer, Float> getPurchasedItems() {
        return purchasedItems;
    }

    public void setPurchasedItems(Map<Integer, Float> purchasedItems) {
        this.purchasedItems = purchasedItems;
    }

    public double getTotalDeliveryPayment() {
        return totalDeliveryPayment;
    }

    public void setTotalDeliveryPayment(double totalDeliveryPayment) {
        this.totalDeliveryPayment += totalDeliveryPayment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return id == store.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return  String.format(itemsListToString());
    }

    private String itemsListToString() {
        StringBuilder informationString = new StringBuilder();
        informationString.append("~~~~~~~" + name.toUpperCase() + "~~~~~~~%n%n");
        informationString.append("Store id: " + id);
        informationString.append("%nName: " + name);
        informationString.append("%n%n-----Items in store-----");

        for(Item item : itemsAndPrices.keySet()) {
            informationString.append("%n%nItem id: " + item.getId());
            informationString.append("%nName: " + item.getName());
            informationString.append("%nPurchase Category: " + item.getPurchaseCategory().toString().toLowerCase());
            informationString.append("%nPrice: " + itemsIdAndPrices.get(item.getId()));
            informationString.append("%nNumber of Purchases: " + (purchasedItems.get(item.getId()) == null ? "0" : purchasedItems.get(item.getId())));
        }
        informationString.append("%n%n-----Orders in store-----%n");

        if(!orders.isEmpty()) {
            for (Order order : orders) {
                informationString.append("%nOrder date: " + order.getOrderDate());
                informationString.append("%nTotal items: " + order.getTotalNumberOfItems());
                informationString.append("%nTotal items price: " + order.getTotalItemsPrice());
                informationString.append("%nDelivery cost: " + String.format("%.2f",order.getDeliveryCost()));
                informationString.append("%nTotal order price: " + String.format("%.2f", order.getTotalOrderPrice()));
                informationString.append("%n********%n");

            }
        } else{
           informationString.append("%nThere are no orders from this store%n");
        }

        informationString.append("%nStore PPK: " + deliveryPPK);
        informationString.append("%nTotal delivery payment: " + String.format("%.2f",totalDeliveryPayment) + "%n%n");
        return informationString.toString();
    }

    public Integer getNewOrderId() {
        return ++orderCounter;
    }

    public void updateItemSoldMap(Map<Integer,Float> newPurchasedItems) {
        for (Integer itemId : newPurchasedItems.keySet()) {
           if(itemsIdAndPrices.containsKey(itemId)){
               if(purchasedItems.containsKey(itemId)) {
                  purchasedItems.put(itemId,purchasedItems.get(itemId) + newPurchasedItems.get(itemId));
               } else {
                   purchasedItems.put(itemId,newPurchasedItems.get(itemId));
               }
           }
        }
    }

    public Map<Pair<Integer,Integer>,OrderSummeryContainer> getStoresOrders() {
        Map<Pair<Integer,Integer>,OrderSummeryContainer> storeOrders = new HashMap<>();

        for (Order order : orders) {
            OrderSummeryContainer orderContainer = new OrderSummeryContainer();
            orderContainer.setTotalOrderCostWithoutShipping(order.getTotalItemsPrice());
            orderContainer.setTotalOrderCost(order.getTotalOrderPrice());
            orderContainer.setTotalShippingCost(order.getDeliveryCost());

            SingleOrderStoreInfo storeInfo = new SingleOrderStoreInfo();
            storeInfo.setStoreId(id);
            storeInfo.setStoreName(name);
            storeInfo.setPpk(deliveryPPK);
            storeInfo.setCustomerShippingCost(order.getDeliveryCost());
            storeInfo.setDistanceFromCustomer(order.getDistance());

            for(OrderedItem item : order.getItemIdPairToItems().values()) {
                OrderStoreItemInfo itemContainer = new OrderStoreItemInfo();
                itemContainer.setItemId(item.getItemId());
                itemContainer.setItemName(item.getItemName());
                itemContainer.setTotalPrice(item.getTotalPrice());
                itemContainer.setFromDiscount(item.isFromDiscount());
                itemContainer.setPricePerPiece(item.getPricePerPiece());
                itemContainer.setPurchaseCategory(item.getPurchaseCategory());
                itemContainer.setAmount(item.getAmount());

                storeInfo.getProgressItems().add(itemContainer);
            }
            orderContainer.getStoreIdToStoreInfo().put(id,storeInfo);
            storeOrders.put(new Pair<>(id,order.getOrderId()),orderContainer);
        }
        return storeOrders;
    }

    public boolean isItemSoldInStore(Integer itemId) {
        return getItemsIdAndPrices().containsKey(itemId);
    }

    public void addItem(Item itemToAdd,Integer itemPrice) {
        itemsAndPrices.put(itemToAdd,itemPrice);
        itemsIdAndPrices.put(itemToAdd.getId(),itemPrice);
    }
}
