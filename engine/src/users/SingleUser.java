package users;

import javafx.util.Pair;
import sdm.enums.UserType;
import sdm.sdmElements.OrderedItem;
import sdm.sdmElements.Store;

import java.util.*;

public class SingleUser {

    private final String userName;
    private final UserType userType;
    private final Integer userId;
    private List<String> areas;
    private Map<String, List<Pair<Integer,Store>>> areaNameToStores;
    private Map<String, List<Pair<Integer, OrderedItem>>> areaNameToItems;


    public SingleUser(UserType userType, String username, Integer userId) {
        this.userType = userType;
        this.userName = username;
        this.userId = userId;
        this.areas = new ArrayList<>();
        this.areaNameToStores = new HashMap<>();
        this.areaNameToItems = new HashMap<>();
    }


    public Map<String, List<Pair<Integer, OrderedItem>>> getAreaNameToItems() {
        return Collections.unmodifiableMap(areaNameToItems);
    }

    public Map<String, List<Pair<Integer, Store>>> getAreaNameToStores() {
        return Collections.unmodifiableMap(areaNameToStores);
    }

    public List<String> getAreas() {
        return Collections.unmodifiableList(areas);
    }

    public Integer getUserId() {
        return userId;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getUserName() {
        return userName;
    }

    public synchronized void addNewArea(String newArea) {
        areas.add(newArea);
    }
}
