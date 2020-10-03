package users;

import sdm.enums.UserType;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/*
Adding and retrieving users is synchronized and in that manner - these actions are thread safe
Note that asking if a user exists (isUserExists) does not participate in the synchronization and it is the responsibility
of the user of this class to handle the synchronization of isUserExists with other methods here on it's own
 */
public class UserManager {

    private final Set<SingleUser> usersSet;

    public UserManager() {
        usersSet = new HashSet<>();
    }

    public synchronized void addUser(String username, UserType userType) {
        usersSet.add(new SingleUser(userType,username));
    }

    public synchronized void removeUser(String username) {
        usersSet.forEach(user -> {
            if(user.getUsername().equals(username)) {
                usersSet.remove(user);
            }
        });
    }

    public synchronized Set<SingleUser> getUsers() {
        return Collections.unmodifiableSet(usersSet);
    }

    public boolean isUserExists(String username) {
        return usersSet.stream().filter(user -> user.getUsername().equals(username)).count() > 0;
    }
}
