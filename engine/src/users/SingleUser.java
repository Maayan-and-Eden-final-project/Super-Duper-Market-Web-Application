package users;

import sdm.enums.UserType;

public class SingleUser {

    private final UserType userType;
    private final String username;
    private final Integer userId;

    public SingleUser(UserType userType, String username, Integer userId) {
        this.userType = userType;
        this.username = username;
        this.userId = userId;
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
}
