package systemEngine;

import exceptions.InvalideOrderHistoryLoadFileException;
import exceptions.ItemIsNotSoldException;
import exceptions.SingleSellingStoreException;
import exceptions.XmlSimilarItemsIdException;

import javafx.util.Pair;
import sdm.sdmElements.Item;
import sdm.sdmElements.Store;
import systemInfoContainers.*;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public abstract class Connector implements Cloneable {
    protected Map<Integer, Store> stores;
    protected Map<Integer, Item> items;
    protected String xmlFileName;

    public abstract StoresContainer getStoresInformation() throws CloneNotSupportedException;
    public abstract ItemsContainer getItemsInformation() throws CloneNotSupportedException;
    public abstract ProgressStaticOrderContainer getProgressOrderInformation(Point userLocation, Integer storeId, Map<Integer,StoreItemInformation> storeItemInfo) throws CloneNotSupportedException;
    public abstract Map<Integer,StoreItemInformation> getStoreItemsInformation (Integer storeId) throws CloneNotSupportedException;
    public abstract void makeNewOrder(Integer storeId, Date orderDate, Orderable progressOrderInfo);
    public abstract Map<Pair<Integer,Integer>, OrdersContainer> getStoresOrders();
    public abstract void deleteStoreItem(Integer storeId,Integer itemId) throws ItemIsNotSoldException, SingleSellingStoreException;
    public abstract void rejectIfItemDefinedInStore(Integer storeId, Integer itemId) throws XmlSimilarItemsIdException;
    public abstract void addNewItemToStore(Integer storeId, Integer itemId,Integer itemPrice);
    public abstract void updateItemPrice(Integer storeId, Integer itemId,Integer newPrice) throws ItemIsNotSoldException;
    public abstract void rejectIfItemNotDefinedInStore(Integer storeId, Integer itemId) throws ItemIsNotSoldException;
    public abstract void saveOrdersToFile(String path) throws IOException;
    public abstract void loadOrdersFromFile(String usersPath) throws IOException, ClassNotFoundException, InvalideOrderHistoryLoadFileException;
    public abstract void saveMaxOrderIdToFile() throws IOException;
    public abstract void loadMaxOrderIdToFile() throws FileNotFoundException;
    public abstract ProgressDynamicOrderContainer getProgressDynamicOrder(Point userLocation,Map<Integer,Float> itemIdToAmount);

    public Connector() {
        this.stores = new HashMap<>();
        this.items = new HashMap<>();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getXmlFileName() {
        return xmlFileName;
    }

    public void setXmlFileName(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public Map<Integer, Store> getStores() {
        return stores;
    }

    public Map<Integer, Item> getItems() {
        return items;
    }

    public boolean isValidLocation(Point userLocation) {
        return stores.values().stream().filter(store -> store.getLocation().equals(userLocation)).count() == 0;
    }

    public boolean isIdStoreValid(Integer id) {
        return stores.containsKey(id);
    }

    public boolean isIdItemValid(Integer id) {
        return items.containsKey(id);
    }

    public boolean isItemInStore(Integer storeId, Integer itemId) {
        return stores.get(storeId).getItemsIdAndPrices().containsKey(itemId);
    }

}
