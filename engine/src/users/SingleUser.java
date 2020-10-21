package users;

import areas.Area;

import sdm.enums.AccountAction;
import sdm.enums.UserType;
import sdm.sdmElements.OrderedItem;
import sdm.sdmElements.Store;

import java.util.*;

public class SingleUser {

    private final String userName;
    private final UserType userType;
    private final Integer userId;
    private Map<String, Area> areaNameToAreas;
    private List<SingleAccountAction> accountActions;
    private float balance;
    private List<Store> myAddedStores;
    private List<String> userMessages;

    public SingleUser(UserType userType, String username, Integer userId) {
        this.userType = userType;
        this.userName = username;
        this.userId = userId;
        this.areaNameToAreas = new HashMap<>();
        this.accountActions = new ArrayList<>();
        this.balance = 0;
        this.myAddedStores = new ArrayList<>();
        this.userMessages = new ArrayList<>();
    }

    public List<Store> getMyAddedStores() {
        return myAddedStores;
    }

    public Map<String, Area> getAreaNameToAreas() {
        return Collections.unmodifiableMap(areaNameToAreas);
    }

    public Integer getUserId() {
        return userId;
    }

    public List<String> getUserMessages() {
        return Collections.unmodifiableList(userMessages);
    }

    public UserType getUserType() {
        return userType;
    }

    public String getUserName() {
        return userName;
    }

    public List<SingleAccountAction> getAccountActions() {
        return Collections.unmodifiableList(accountActions);
    }

    public float getBalance() {
        return balance;
    }

    public synchronized void addNewArea(Area newArea) {
        areaNameToAreas.put(newArea.getAreaName(),newArea);
    }

    public void handleAddFundsAction(String date, Float amount) {
        SingleAccountAction singleAccountAction =
                new SingleAccountAction(AccountAction.ADD_FUNDS,date,amount,balance+amount, balance);
        balance += amount;
        accountActions.add(singleAccountAction);
    }

    public void handleTransferAction(String date, Float amount) {
        SingleAccountAction singleAccountAction =
                new SingleAccountAction(AccountAction.PAYMENT_TRANSFERRED,date,amount,balance - amount, balance);
        balance -= amount;
        accountActions.add(singleAccountAction);
    }

    public void handlePaymentReceivedAction(String date, Float amount) {
        SingleAccountAction singleAccountAction =
                new SingleAccountAction(AccountAction.PAYMENT_RECEIVED,date,amount,balance + amount, balance);
        balance += amount;
        accountActions.add(singleAccountAction);
    }

    public void addNewStoreToMyStoresList(Store newStore) {
        myAddedStores.add(newStore);
    }

    public synchronized void addNewStoreToMyAreaStores(Store newStore) {
        areaNameToAreas.get(newStore.getAreaName()).addStoreToArea(newStore);
    }

    public synchronized void addNewMessage(String... messages) {
        userMessages.addAll(Arrays.asList(messages));
    }
}
