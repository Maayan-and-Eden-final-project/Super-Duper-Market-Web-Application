package users;

import areas.Area;
import com.sun.xml.internal.bind.v2.model.core.EnumLeafInfo;
import exceptions.AreaAlreadyExistException;
import exceptions.StoreLocationAlreadyExistException;
import exceptions.XmlSimilarStoresIdException;
import sdm.enums.AccountAction;
import sdm.enums.UserType;
import sdm.sdmElements.Item;
import sdm.sdmElements.Offer;
import sdm.sdmElements.OrderedItem;
import sdm.sdmElements.Store;
import systemEngine.WebEngine;
import systemInfoContainers.ItemsContainer;
import systemInfoContainers.OrderSummeryContainer;
import systemInfoContainers.ProgressOrderItem;
import systemInfoContainers.SingleDiscountContainer;
import systemInfoContainers.webContainers.*;

import java.awt.*;
import java.util.*;
import java.util.List;

/*
Adding and retrieving users is synchronized and in that manner - these actions are thread safe
Note that asking if a user exists (isUserExists) does not participate in the synchronization and it is the responsibility
of the user of this class to handle the synchronization of isUserExists with other methods here on it's own
 */
public class UserManager {

    private Map<String, SingleUser> userNameToUser;
    private WebEngine engine;

    public UserManager() {
        userNameToUser = new HashMap<>();
        engine = new WebEngine();
    }

    public WebEngine getEngine() {
        return engine;
    }

    public synchronized void addUser(String username, UserType userType, Integer userId) {
        userNameToUser.put(username, new SingleUser(userType,username, userId));
    }

    /*public synchronized void removeUser(String username) {
        usersSet.forEach(user -> {
            if(user.getUserName().equals(username)) {
                usersSet.remove(user);
            }
        });
    }*/

    public synchronized Map<String,SingleUser> getUsers() {
        return Collections.unmodifiableMap(userNameToUser);
    }

    public boolean isUserExists(String username) {
        return userNameToUser.containsKey(username);
    }

    public void addAreaToUser(Area area, String userName) throws AreaAlreadyExistException {
        for(SingleUser user : userNameToUser.values()) {
            if (user.getAreaNameToAreas().keySet().contains(area.getAreaName())) {
                throw new AreaAlreadyExistException();
            }
        }

        if(userNameToUser.containsKey(userName)) {
            userNameToUser.get(userName).addNewArea(area);
        }
    }

    public UserType getUserType(String userName) {
        UserType userType = null;

        if(userNameToUser.containsKey(userName)) {
            userType = userNameToUser.get(userName).getUserType();
        }
         return userType;
    }

    public List<AreaContainer> getAreas() {
        return engine.getAreasContainer(userNameToUser);
    }

    public void addFundsToWallet(Float amount, String date, String userName) {
        userNameToUser.get(userName).handleAddFundsAction(date,amount);
    }

    public AccountActionsContainer getUserActions(String userName) {
        AccountActionsContainer actions = new AccountActionsContainer(userNameToUser.get(userName).getAccountActions(),userNameToUser.get(userName).getBalance());
        return actions;
    }

    public ItemsContainer getAreaItems(String areaName) throws CloneNotSupportedException {
        ItemsContainer itemsContainer = null;
        for(SingleUser user: userNameToUser.values()) {
           for(Area area : user.getAreaNameToAreas().values()) {
               if(area.getAreaName().equals(areaName)) {
                   itemsContainer = engine.systemItemsInformation(area.getItemIdToItem(), area.getStoreIdToStore());
               }
           }
        }

        return itemsContainer;
    }

    public List<SingleStoreContainer> getAreaStores(String areaName) {
            List<SingleStoreContainer> stores = null;
        for(SingleUser user: userNameToUser.values()) {
            for(Area area : user.getAreaNameToAreas().values()) {
                if(area.getAreaName().equals(areaName)) {
                    stores = engine.getAreaStores(area.getStoreIdToStore(),user.getUserName());
                }
            }
        }
        return stores;
    }

    public List<SingleStoreItemContainer> getAreaStoreItems(String areaName, Integer storeId, Integer xLocation, Integer yLocation) throws StoreLocationAlreadyExistException {
        Point location = new Point(xLocation,yLocation);

        for(SingleUser user : userNameToUser.values()) {
            for(Area area : user.getAreaNameToAreas().values()) {
                for (Store store : area.getStoreIdToStore().values()) {
                    if(store.getLocation().equals(location)) {
                        throw new StoreLocationAlreadyExistException();
                    }
                }
            }
        }

        for(SingleUser user : userNameToUser.values()) {
            if(user.getAreaNameToAreas().containsKey(areaName)) {
                Map<Item,Integer> itemToItemPrice = user.getAreaNameToAreas().get(areaName).getStoreIdToStore().get(storeId).getItemsAndPrices();
                return engine.getStoreItems(itemToItemPrice);
            }
        }
        return null;
    }

    public void addNewStore(String areaName,Integer storeId, String storeName,Integer xLocation, Integer yLocation, Integer ppk, Map<Integer,Integer> itemIdToItemPrice, String usernameFromSession) throws XmlSimilarStoresIdException, StoreLocationAlreadyExistException, CloneNotSupportedException {
        Point newStoreLocation = new Point(xLocation,yLocation);

        for(SingleUser user : userNameToUser.values()) {
            for(Area area : user.getAreaNameToAreas().values()) {
                if(area.getStoreIdToStore().containsKey(storeId)) {
                   throw new XmlSimilarStoresIdException();
                }
                for (Store store : area.getStoreIdToStore().values()) {
                    if(store.getLocation().equals(newStoreLocation)) {
                        throw new StoreLocationAlreadyExistException();
                    }
                }
            }
        }

        SingleUser shopOwner = userNameToUser.get(usernameFromSession);
        SingleUser areaOwner = null;
        for(SingleUser user : userNameToUser.values()) {
            if(user.getAreaNameToAreas().containsKey(areaName)) {
                areaOwner = user;
            }
        }

        engine.addNewStore(areaName,storeId,storeName,newStoreLocation,ppk,itemIdToItemPrice,shopOwner,areaOwner);
        addMessageToUser(shopOwner.getUserName() + " opened a new store- " + storeName +  " in your area- " + areaName, areaOwner);
    }

    private void addMessageToUser(String message, SingleUser areaOwner) {
        areaOwner.addNewMessage(message);
    }

    public List<String> getUserMessages(String userName, Integer version) {
        if (version < 0 || version > userNameToUser.get(userName).getUserMessages().size()) {
            version = 0;
        }
        if(version != 0){
            return userNameToUser.get(userName).getUserMessages().subList(version -1, userNameToUser.get(userName).getUserMessages().size());
        }
        return userNameToUser.get(userName).getUserMessages();
    }

    public int getUserMessageVersion(String userName) {
        return userNameToUser.get(userName).getUserMessages().size();
    }

    public SingleAreaOptionContainer getNewOrderOptions(String areaName) {
        Map<Integer,Store> stores = null;

        for(SingleUser user : userNameToUser.values()) {
            if(user.getAreaNameToAreas().containsKey(areaName)) {
                stores = user.getAreaNameToAreas().get(areaName).getStoreIdToStore();
            }
        }

        return engine.getNewOrderOptions(areaName,stores);
    }

    public List<SingleDiscountContainer> getDiscounts(String areaName, Integer storeId,String method, Map<Integer,Float> itemIdToItemAmount, Map<Integer, List<ProgressOrderItem>> minimalCart) throws CloneNotSupportedException {
        Store store = null;
        Map<Integer, List<ProgressOrderItem>>  storeIdToItemsList = null;
        Area area = null;
        if(method.equals("Static Order")) {
            for (SingleUser user : userNameToUser.values()) {
                if (user.getAreaNameToAreas().containsKey(areaName)) {
                    area = user.getAreaNameToAreas().get(areaName);
                    store = area.getStoreIdToStore().get(storeId);
                    storeIdToItemsList = engine.makeOrderStoreIdToItemsList(store.getItemsAndPrices(),store.getId(), itemIdToItemAmount);
                }
            }

        } else if (method.equals("Dynamic Order")) {
            for (SingleUser user : userNameToUser.values()) {
                if (user.getAreaNameToAreas().containsKey(areaName)) {
                    area = user.getAreaNameToAreas().get(areaName);
                    storeIdToItemsList = minimalCart;
                }
            }
        }

        return engine.findRelevantDiscounts(area, storeIdToItemsList);
    }

    public MinimalCartContainer getMinimalCart(String areaName, Map<Integer,Float> itemIdToAmount, Integer xLocation, Integer yLocation) {
        Point location = new Point(xLocation,yLocation);
        Area area = null;

        for (SingleUser user : userNameToUser.values()) {
            if(user.getAreaNameToAreas().containsKey(areaName)) {
                area = user.getAreaNameToAreas().get(areaName);
            }
        }
        return engine.getDynamicOrderCalcSummery(area,itemIdToAmount,location);
    }

    public OrderSummeryContainer getOrderSummery(String areaName, Map<Integer,Float> itemIdToAmount, Map<String,List<Offer>> discountNameToOffersList, Integer xLocation, Integer yLocation,String method, Integer storeId, Map<Integer, List<ProgressOrderItem>> minimalCart) {
        OrderSummeryContainer orderSummery = null;
        Store store = null;
        Map<Integer, List<ProgressOrderItem>> storeIdToItemsList = null;
        Area area = null;
        if (method.equals("Static Order")) {
            for (SingleUser user : userNameToUser.values()) {
                if (user.getAreaNameToAreas().containsKey(areaName)) {
                    area = user.getAreaNameToAreas().get(areaName);
                    store = area.getStoreIdToStore().get(storeId);
                    storeIdToItemsList = engine.makeOrderStoreIdToItemsList(store.getItemsAndPrices(), store.getId(), itemIdToAmount);
                }
            }
        } else if (method.equals("Dynamic Order")) {
            for (SingleUser user : userNameToUser.values()) {
                if (user.getAreaNameToAreas().containsKey(areaName)) {
                    area = user.getAreaNameToAreas().get(areaName);
                    storeIdToItemsList = minimalCart;
                }
            }
        }


        orderSummery = engine.getOrderSummery(storeIdToItemsList,discountNameToOffersList,area,new Point(xLocation,yLocation));
        return orderSummery;
    }

}
