package users;

import areas.Area;
import javafx.util.Pair;
import sdm.enums.UserType;
import sdm.sdmElements.OrderedItem;
import sdm.sdmElements.Store;

import java.util.*;

public class SingleUser {

    private final String userName;
    private final UserType userType;
    private final Integer userId;
    private Map<String, Area> areaNameToAreas;

    public SingleUser(UserType userType, String username, Integer userId) {
        this.userType = userType;
        this.userName = username;
        this.userId = userId;
        this.areaNameToAreas = new HashMap<>();
    }

    public Map<String, Area> getAreaNameToAreas() {
        return Collections.unmodifiableMap(areaNameToAreas);
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

    public synchronized void addNewArea(Area newArea) {
        areaNameToAreas.put(newArea.getAreaName(),newArea);
    }
}
