package users;

import sdm.enums.UserType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SingleUser {

    private final UserType userType;
    private final String username;
    private final Integer userId;
    private List<String> areas;

    public SingleUser(UserType userType, String username, Integer userId) {
        this.userType = userType;
        this.username = username;
        this.userId = userId;
        this.areas = new ArrayList<>();
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

    public String getUsername() {
        return username;
    }

    public synchronized void addNewArea(String newArea) {
        areas.add(newArea);
    }
}
