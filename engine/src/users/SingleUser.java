package users;

import sdm.enums.UserType;

public class SingleUser {

    private final UserType userType;
    private final String username;

    public SingleUser(UserType userType, String username) {
        this.userType = userType;
        this.username = username;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getUsername() {
        return username;
    }
}
