package systemInfoContainers.webContainers;

import sdm.enums.UserType;

public class SingleUserContainer {
    private final String userName;
    private final UserType userType;
    private boolean isCurrentUser = false;

    public SingleUserContainer(String userName, UserType userType) {
        this.userName = userName;
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public UserType getUserType() {
        return userType;
    }

    public boolean isCurrentUser() {
        return isCurrentUser;
    }

    public void setCurrentUser(boolean currentUser) {
        isCurrentUser = currentUser;
    }
}
