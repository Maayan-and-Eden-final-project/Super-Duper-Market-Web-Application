package systemEngine;

import exceptions.InvalideOrderHistoryLoadFileException;
import exceptions.ItemIsNotSoldException;
import exceptions.SingleSellingStoreException;
import exceptions.XmlSimilarItemsIdException;
import javafx.util.Pair;
import sdm.sdmElements.Order;
import sdm.sdmElements.Store;
import systemInfoContainers.*;
import systemInfoContainers.webContainers.AreaContainer;
import users.SingleUser;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class WebEngine  extends Connector{



    @Override
    public StoresContainer getStoresInformation() throws CloneNotSupportedException {
        return null;
    }

    @Override
    public ItemsContainer getItemsInformation() throws CloneNotSupportedException {
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

    public List<AreaContainer> getAreasContainer(Set<SingleUser> usersSet) {
        List<AreaContainer> areaContainersList = new ArrayList<>();

        for (SingleUser user : usersSet) {
            if (!user.getAreas().isEmpty()) {
                for (String area : user.getAreas()) {
                    AreaContainer areaContainer = new AreaContainer();
                    areaContainer.setAreaName(area);
                    areaContainer.setAreaOwner(user.getUserName());
                    areaContainer.setStoresInArea(user.getAreaNameToStores().get(area).size());
                    areaContainer.setItemsInArea(user.getAreaNameToItems().get(area).size());

                    for(Pair<Integer, Store> storeIdToStore: user.getAreaNameToStores().get(area)) {
                        Store store = storeIdToStore.getValue().clone();
                        areaContainer.setOrdersInArea(store.getOrders().size());

                        float ordersAvg = 0;
                        for(Order order: store.getOrders().values()) {
                            ordersAvg += order.getTotalItemsPrice();
                        }
                        ordersAvg /= store.getOrders().size();
                        areaContainer.setAvgOrdersCostsInArea(ordersAvg);
                    }
                    areaContainersList.add(areaContainer);
                }
            }
        }
        return areaContainersList;
    }
}
