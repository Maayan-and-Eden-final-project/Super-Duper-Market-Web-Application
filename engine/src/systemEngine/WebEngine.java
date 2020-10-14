package systemEngine;

import areas.Area;
import exceptions.InvalideOrderHistoryLoadFileException;
import exceptions.ItemIsNotSoldException;
import exceptions.SingleSellingStoreException;
import exceptions.XmlSimilarItemsIdException;
import javafx.util.Pair;
import sdm.sdmElements.Item;
import sdm.sdmElements.Order;
import sdm.sdmElements.Store;
import systemInfoContainers.*;
import systemInfoContainers.webContainers.*;
import users.SingleUser;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    public Map<Pair<Integer, Integer>, OrdersContainer> getStoresOrders() {
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
}
