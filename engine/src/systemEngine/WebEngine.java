package systemEngine;

import areas.Area;
import exceptions.InvalideOrderHistoryLoadFileException;
import exceptions.ItemIsNotSoldException;
import exceptions.SingleSellingStoreException;
import exceptions.XmlSimilarItemsIdException;

import sdm.sdmElements.*;
import systemInfoContainers.*;
import systemInfoContainers.webContainers.*;
import users.SingleUser;
import utils.IntegerToBooleanPair;
import utils.IntegerToIntegerPair;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class WebEngine  extends Connector{

    @Override
    public ItemsContainer getItemsInformation() throws CloneNotSupportedException {
        return null;
    }

    @Override
    public StoresContainer getStoresInformation() throws CloneNotSupportedException {
        return null;
    }

    @Override
    public ProgressStaticOrderContainer getProgressOrderInformation(Point userLocation, Integer storeId, Map<Integer, StoreItemInformation> storeItemInfo) throws CloneNotSupportedException {
        return null;
    }

    @Override
    public Map<Integer, StoreItemInformation> getStoreItemsInformation(Integer storeId) throws CloneNotSupportedException {
        return null;
    }

    @Override
    public void makeNewOrder(Integer storeId, Date orderDate, Orderable progressOrderInfo) {

    }

    @Override
    public Map<IntegerToIntegerPair, OrdersContainer> getStoresOrders() {
        return null;
    }

    @Override
    public void deleteStoreItem(Integer storeId, Integer itemId) throws ItemIsNotSoldException, SingleSellingStoreException {

    }

    @Override
    public void rejectIfItemDefinedInStore(Integer storeId, Integer itemId) throws XmlSimilarItemsIdException {

    }

    @Override
    public void addNewItemToStore(Integer storeId, Integer itemId, Integer itemPrice) {

    }

    @Override
    public void updateItemPrice(Integer storeId, Integer itemId, Integer newPrice) throws ItemIsNotSoldException {

    }

    @Override
    public void rejectIfItemNotDefinedInStore(Integer storeId, Integer itemId) throws ItemIsNotSoldException {

    }

    @Override
    public void saveOrdersToFile(String path) throws IOException {

    }

    @Override
    public void loadOrdersFromFile(String usersPath) throws IOException, ClassNotFoundException, InvalideOrderHistoryLoadFileException {

    }

    @Override
    public void saveMaxOrderIdToFile() throws IOException {

    }

    @Override
    public void loadMaxOrderIdToFile() throws FileNotFoundException {

    }

    @Override
    public ProgressDynamicOrderContainer getProgressDynamicOrder(Point userLocation, Map<Integer, Float> itemIdToAmount) {
        return null;
    }

    public ItemsContainer systemItemsInformation(Map<Integer, Item> itemIdToItem, Map<Integer, Store> storeIdToStore) throws CloneNotSupportedException {
        ItemsContainer systemItemsInfo = new ItemsContainer();
        List<Store> storesList;
        int sellingStores;
        int sum;
        float average;
        float numberOfSoldItems = 0;
        ItemInfo itemInfo;

        for(Item item : itemIdToItem.values()) {
            itemInfo = new ItemInfo();

            storesList = storeIdToStore.values().stream().filter(store -> store.getItemsIdAndPrices()
                    .containsKey(item.getId())).collect(Collectors.toList());
            sellingStores = storesList.size();

            sum = storesList.stream().mapToInt(store -> store.getItemsIdAndPrices().get(item.getId())).sum();
            average = sum / sellingStores;

            numberOfSoldItems = 0;

            for (Store store : storesList) {
                if(store.getPurchasedItems().containsKey(item.getId()))
                    numberOfSoldItems += store.getPurchasedItems().get(item.getId());
            }
            itemInfo.setItemId(item.getId());
            itemInfo.setItemName(item.getName());
            itemInfo.setAvgPrice(average);
            itemInfo.setNumOfSellingStores(sellingStores);
            itemInfo.setPurchaseCategory(item.getPurchaseCategory());
            itemInfo.setTotalPurchases(numberOfSoldItems);
            systemItemsInfo.getItemIdToItemInfo().put(item.getId(),itemInfo);
        }

        return systemItemsInfo;
    }


    public List<AreaContainer> getAreasContainer(Map<String,SingleUser> userMap) {
        List<AreaContainer> areaContainersList = new ArrayList<>();

        for (SingleUser user : userMap.values()) {
            if (!user.getAreaNameToAreas().isEmpty()) {
                for (Area area : user.getAreaNameToAreas().values()) {
                    AreaContainer areaContainer = new AreaContainer();
                    areaContainer.setAreaName(area.getAreaName());
                    areaContainer.setAreaOwner(user.getUserName());
                    areaContainer.setStoresInArea(area.getStoreIdToStore().size());
                    areaContainer.setItemsInArea(area.getItemIdToItem().size());

                    for(Store store: area.getStoreIdToStore().values()) {
                        areaContainer.setOrdersInArea(store.getOrders().size());

                        float ordersAvg = 0;
                        for(Order order: store.getOrders().values()) {
                            ordersAvg += order.getTotalItemsPrice();
                        }
                        if(store.getOrders().size() == 0) {
                            ordersAvg = 0;
                        } else {
                            ordersAvg /= store.getOrders().size();
                        }
                        areaContainer.setAvgOrdersCostsInArea(ordersAvg);
                    }
                    areaContainersList.add(areaContainer);
                }
            }
        }
        return areaContainersList;
    }

    public List<SingleStoreContainer> getAreaStores(Map<Integer, Store> storeIdToStore, String areaOwnerName) {
        List<SingleStoreContainer> stores = new ArrayList<>();

        for(Store store : storeIdToStore.values()) {
            SingleStoreContainer singleStore = new SingleStoreContainer();
            singleStore.setStoreId(store.getId());
            singleStore.setStoreName(store.getName());
            singleStore.setOwnerName(areaOwnerName);
            singleStore.setLocation(store.getLocation());
            singleStore.setNumOfOrders(store.getStoresOrders().size());
            singleStore.setDeliveryPPK(store.getDeliveryPPK());
            singleStore.setTotalDeliveryPayment(store.getTotalDeliveryPayment());

            float totalItemsCost = 0;
            for(Order order: store.getOrders().values()) {
                totalItemsCost += order.getTotalItemsPrice();
            }
            singleStore.setPurchasedItemsCost(totalItemsCost);

            for(Item item: store.getItemsAndPrices().keySet()) {
                SingleStoreItemContainer singleItem = new SingleStoreItemContainer();
                singleItem.setItemId(item.getId());
                singleItem.setItemName(item.getName());
                singleItem.setPurchaseCategory(item.getPurchaseCategory());
                singleItem.setItemPrice(store.getItemsAndPrices().get(item));
                if(store.getPurchasedItems().containsKey(item.getId())) {
                    singleItem.setAmountSold(store.getPurchasedItems().get(item.getId()));
                } else {
                    singleItem.setAmountSold(0);
                }
                singleStore.getStoreItems().add(singleItem);
            }
            stores.add(singleStore);
        }
        return stores;
    }

    public void addNewStore(String area,Integer storeId, String storeName,Point newStoreLocation, Integer ppk, Map<Integer,Integer> itemIdToItemPrice, SingleUser newShopOwner, SingleUser areaOwner ) throws CloneNotSupportedException {
        Map<Item,Integer> itemToItemPrice =  makeItemsAndPricesMap(itemIdToItemPrice, areaOwner.getAreaNameToAreas().get(area).getItemIdToItem());
        Store store = new Store(storeId,storeName,ppk,newStoreLocation,itemIdToItemPrice,itemToItemPrice ,area);
        newShopOwner.addNewStoreToMyStoresList(store);
        areaOwner.addNewStoreToMyAreaStores(store);
    }

    private Map<Item,Integer> makeItemsAndPricesMap(Map<Integer,Integer> itemIdToItemPrice, Map<Integer,Item> areaItems) throws CloneNotSupportedException {
        Map<Item,Integer> itemToItemPrice = new HashMap<>();
        for(Integer itemId : itemIdToItemPrice.keySet()) {
            Item item = areaItems.get(itemId).clone();
            itemToItemPrice.put(item, itemIdToItemPrice.get(itemId));
        }
        return itemToItemPrice;
    }

    public SingleAreaOptionContainer getNewOrderOptions(String areaName,  Map<Integer,Store> stores) {
        SingleAreaOptionContainer orderOptions = new SingleAreaOptionContainer();
        orderOptions.setAreaName(areaName);

        for(Store store : stores.values()) {
            SingleOrderStoreContainer singleStore = new SingleOrderStoreContainer(store.getId(),store.getName());
            orderOptions.getStores().add(singleStore);
        }

        return orderOptions;
    }

    public List<SingleStoreItemContainer> getStoreItems(Map<Item,Integer> itemToItemPrice) {
        List<SingleStoreItemContainer> items = new ArrayList<>();

        for(Item item : itemToItemPrice.keySet()) {
            SingleStoreItemContainer singleItem = new SingleStoreItemContainer(item.getId(),item.getName(),item.getPurchaseCategory(),itemToItemPrice.get(item));
            items.add(singleItem);
        }

        return items;
    }

    public OrderSummeryContainer getOrderSummery(Map<Integer, List<ProgressOrderItem>> storeIdToItemsList ,Map<String,List<Offer>> discountNameToOffersList, Area area, Point userLocation) {
        OrderSummeryContainer orderSummery = new OrderSummeryContainer();
        Store currentStore = null;
        float totalItemsCost = 0;
        double totalShippingCost = 0;
        for (Integer storeId : storeIdToItemsList.keySet()) {

            Map<IntegerToBooleanPair, OrderStoreItemInfo> itemIdMapToProgressItem = new HashMap<>();
            SingleOrderStoreInfo store = new SingleOrderStoreInfo();
            currentStore = area.getStoreIdToStore().get(storeId);
            store.setStoreId(storeId);
            store.setStoreName(currentStore.getName());
            store.setPpk(currentStore.getDeliveryPPK());
            store.setDistanceFromCustomer(currentStore.getLocation().distance(userLocation.x,userLocation.y));
            store.setCustomerShippingCost(store.getDistanceFromCustomer() * store.getPpk());
            totalShippingCost += store.getCustomerShippingCost();
            for (ProgressOrderItem item : storeIdToItemsList.get(storeId)) {
                OrderStoreItemInfo storeItem = new OrderStoreItemInfo();
                storeItem.setItemId(item.getItemId());
                storeItem.setItemName(item.getItemName());
                storeItem.setPurchaseCategory(item.getPurchaseCategory());
                storeItem.setAmount(item.getAmount());
                storeItem.setPricePerPiece((float)(currentStore.getItemsIdAndPrices().get(item.getItemId())));
                storeItem.setTotalPrice(storeItem.getPricePerPiece() * storeItem.getAmount());
                storeItem.setFromDiscount(false);
                totalItemsCost += storeItem.getTotalPrice();
                itemIdMapToProgressItem.put(new IntegerToBooleanPair(item.getItemId(),false),storeItem);
            }
            for(String discountName : discountNameToOffersList.keySet()) {
                for(Discount discount : currentStore.getDiscountList()) {
                    if(discount.getName().contains(discountName)) {
                        for(Offer offer : discountNameToOffersList.get(discountName)) {
                            IntegerToBooleanPair discountItemPair = new IntegerToBooleanPair(offer.getItemId(),true);
                            OrderStoreItemInfo offerItem = null;
                            if(itemIdMapToProgressItem.containsKey(discountItemPair)) {
                                offerItem = itemIdMapToProgressItem.get(discountItemPair);
                                offerItem.setAmount(offerItem.getAmount() + (float)offer.getQuantity());
                                offerItem.setTotalPrice(offerItem.getTotalPrice() + offer.getForAdditional()*offerItem.getAmount());
                                itemIdMapToProgressItem.put(discountItemPair,offerItem);
                            } else {
                                offerItem = new OrderStoreItemInfo();
                                Item areaItem = area.getItemIdToItem().get(offer.getItemId());
                                offerItem.setItemId(offer.getItemId());
                                offerItem.setItemName(areaItem.getName());
                                offerItem.setPurchaseCategory(areaItem.getPurchaseCategory());
                                offerItem.setAmount((float)offer.getQuantity());
                                offerItem.setFromDiscount(true);
                                offerItem.setPricePerPiece((float)(offer.getForAdditional()));
                                offerItem.setTotalPrice(offerItem.getPricePerPiece() * offerItem.getAmount());
                                itemIdMapToProgressItem.put(new IntegerToBooleanPair(offerItem.getItemId(),true),offerItem);
                            }
                            totalItemsCost += offerItem.getTotalPrice();
                        }
                    }
                }
            }

            store.setItemIdMapToProgressItem(itemIdMapToProgressItem);
            orderSummery.getStoreIdToStoreInfo().put(storeId,store);
        }
        orderSummery.setTotalOrderCostWithoutShipping(totalItemsCost);
        orderSummery.setTotalShippingCost(totalShippingCost);
        orderSummery.setTotalOrderCost(orderSummery.getTotalShippingCost() + orderSummery.getTotalOrderCostWithoutShipping());
        return orderSummery;
    }

    public  Map<Integer, List<ProgressOrderItem>> makeOrderStoreIdToItemsList(Map<Item,Integer> itemToItemId, Integer storeId, Map<Integer,Float> itemIdToAmount ) {
        Map<Integer, List<ProgressOrderItem>> storeIdToItemsList = new HashMap<>();
        List<ProgressOrderItem> itemsList = new ArrayList<>();
        for(Integer itemId : itemIdToAmount.keySet()) {
            ProgressOrderItem singleItem = new ProgressOrderItem();
            singleItem.setItemId(itemId);
            singleItem.setAmount(itemIdToAmount.get(itemId));

            Item tempItem = null;
            for(Item item : itemToItemId.keySet()) {
                if(item.getId().equals(itemId)) {
                    tempItem = item;
                }
            }
            singleItem.setItemName(tempItem.getName());
            singleItem.setPurchaseCategory(tempItem.getPurchaseCategory());
            itemsList.add(singleItem);
        }
        storeIdToItemsList.put(storeId,itemsList);
        return storeIdToItemsList;
    }

    public MinimalCartContainer getDynamicOrderCalcSummery(Area area, Map<Integer,Float> itemIdToAmount, Point location) {
        List<SingleDynamicStoreContainer> dynamicStores = new ArrayList<>();
        Map<Integer, List<ProgressOrderItem>> storeIdToItemsList = calcMinimalCart(area, itemIdToAmount);

        for (Integer storeId : storeIdToItemsList.keySet()) {
            SingleDynamicStoreContainer singleStore = new SingleDynamicStoreContainer();
            singleStore.setStoreId(storeId);
            Store store = area.getStoreIdToStore().get(storeId);
            singleStore.setStoreName(store.getName());
            singleStore.setPpk(store.getDeliveryPPK());
            singleStore.setLocation(store.getLocation());
            double distance = area.getStoreIdToStore().get(storeId).getLocation().distance(location.x, location.y);
            singleStore.setDistanceFromCustomer(distance);
            singleStore.setCustomerShippingCost(distance * store.getDeliveryPPK());
            singleStore.setNumOfDifferentItem(storeIdToItemsList.get(storeId).size());

            float totalItemCost = 0;
            for (ProgressOrderItem item : storeIdToItemsList.get(storeId)) {
                totalItemCost += item.getAmount() *
                        store.getItemsIdAndPrices().get(item.getItemId());
            }
            singleStore.setTotalItemsCost(totalItemCost);
            dynamicStores.add(singleStore);
        }
        MinimalCartContainer minimalCartContainer = new MinimalCartContainer(dynamicStores, storeIdToItemsList);
        return minimalCartContainer;
    }

    public Map<Integer, List<ProgressOrderItem>> calcMinimalCart(Area area, Map<Integer,Float> itemIdToAmount) {
        Store orderStore;

        Map<Integer, List<ProgressOrderItem>> storeIdToItemsList = new HashMap<>();

        for (Integer itemId : itemIdToAmount.keySet()) {

            Map<Integer, Integer> itemPriceToStoreId = area.getStoreIdToStore().entrySet().stream().filter(store -> store.getValue().getItemsIdAndPrices().containsKey(itemId) == true)
                    .collect(Collectors.toMap(e -> e.getValue().getItemsIdAndPrices().get(itemId), e -> e.getKey()));
            Integer minItemPrice = Collections.min(itemPriceToStoreId.keySet());

            orderStore = area.getStoreIdToStore().get(itemPriceToStoreId.get(minItemPrice));

            ProgressOrderItem item = new ProgressOrderItem();
            item.setItemId(itemId);
            item.setAmount(itemIdToAmount.get(itemId));

            for(Item storeItem : orderStore.getItemsAndPrices().keySet()) {
                if(storeItem.getId().equals(itemId)) {
                    item.setItemName(storeItem.getName());
                    item.setPurchaseCategory(storeItem.getPurchaseCategory());
                }
            }

            if (!storeIdToItemsList.containsKey(orderStore.getId())) {
                List<ProgressOrderItem> itemsList = new ArrayList<>();

                itemsList.add(item);
                storeIdToItemsList.put(orderStore.getId(), itemsList);
            } else {
                storeIdToItemsList.get(orderStore.getId()).add(item);
                storeIdToItemsList.put(orderStore.getId(), storeIdToItemsList.get(orderStore.getId()));
            }
        }
        return storeIdToItemsList;
    }

    public  List<SingleDiscountContainer> findRelevantDiscounts(Area area, Map<Integer, List<ProgressOrderItem>> storeIdToItemsList) throws CloneNotSupportedException {
        Integer purchasedItemId;
        float amountPurchased = 0;
        double discountAmount = 0;
        int numberOfCurrentDiscount;
        List<SingleDiscountContainer> discountsContainerList = new ArrayList<>();

        for (Integer storeId : storeIdToItemsList.keySet()) {
            for (Discount discount : area.getStoreIdToStore().get(storeId).getDiscountList()) {
                purchasedItemId = discount.getIfYouBuy().getItemId();
                discountAmount = discount.getIfYouBuy().getQuantity();

                for (ProgressOrderItem item : storeIdToItemsList.get(storeId)) {
                    if (item.getItemId().equals(purchasedItemId)) {
                        amountPurchased = item.getAmount();

                        if ((numberOfCurrentDiscount = (int) (amountPurchased / discountAmount)) >= 1) {

                            for (int i = 0; i < numberOfCurrentDiscount; i++) {
                                SingleDiscountContainer singleDiscountContainer = new SingleDiscountContainer();
                                singleDiscountContainer.setIfYouBuy(discount.getIfYouBuy().clone());
                                singleDiscountContainer.setThenYouGet(discount.getThenYouGet().clone());
                                singleDiscountContainer.setName(discount.getName());
                                discountsContainerList.add(singleDiscountContainer);
                            }
                        }
                    }
                }
            }
        }
        return discountsContainerList;
    }

    public Map<Integer,Float> addNewOrder(OrderSummeryContainer orderSummeryContainer, Area area, String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        float totalItemsCost = 0;
        Map<Integer,Float> storeIdToTotalCost = new HashMap<>();
        for (SingleOrderStoreInfo store : orderSummeryContainer.getStoreIdToStoreInfo().values()) {
            Store areaStore = area.getStoreIdToStore().get(store.getStoreId());
            Order newOrder = new Order();
            newOrder.setStoreId(store.getStoreId());
            newOrder.setStoreName(store.getStoreName());
            newOrder.setDeliveryCost(store.getCustomerShippingCost());
            newOrder.setDistance(store.getDistanceFromCustomer());
            newOrder.setOrderDate(dateFormat.parse(date));

            for (OrderStoreItemInfo itemInfo : store.getItemIdMapToProgressItem().values()) {
                OrderedItem orderedItem = new OrderedItem();
                orderedItem.setItemId(itemInfo.getItemId());
                orderedItem.setItemName(itemInfo.getItemName());
                orderedItem.setAmount(itemInfo.getAmount());
                orderedItem.setPurchaseCategory(itemInfo.getPurchaseCategory());
                orderedItem.setPricePerPiece(itemInfo.getPricePerPiece());
                orderedItem.setFromDiscount(itemInfo.isFromDiscount());
                orderedItem.setTotalPrice(itemInfo.getTotalPrice());
                newOrder.getItemIdPairToItems().put(new IntegerToBooleanPair(orderedItem.getItemId(), orderedItem.isFromDiscount()), orderedItem);
                totalItemsCost += itemInfo.getTotalPrice();
                areaStore.getPurchasedItems().put(itemInfo.getItemId(), itemInfo.getAmount());
            }

            List<Integer> distinctItems = new ArrayList<>();
            for (OrderStoreItemInfo item : store.getItemIdMapToProgressItem().values()) {
                if (!distinctItems.contains(item.getItemId())) {
                    distinctItems.add(item.getItemId());
                }
            }

            newOrder.setTotalNumberOfItems(distinctItems.size());
            newOrder.setTotalItemsPrice(totalItemsCost);
            newOrder.setTotalOrderPrice(store.getCustomerShippingCost() + totalItemsCost);

            double totalDeliveryPayments = areaStore.getTotalDeliveryPayment();
            areaStore.setTotalDeliveryPayment(totalDeliveryPayments + newOrder.getDeliveryCost());
            areaStore.getOrders().put(newOrder.getOrderId(), newOrder);
            storeIdToTotalCost.put(store.getStoreId(),(float)newOrder.getTotalOrderPrice());
        }
        return storeIdToTotalCost;
    }
}
