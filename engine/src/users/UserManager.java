package users;

import exceptions.AreaAlreadyExistException;
import sdm.enums.UserType;
import systemEngine.WebEngine;

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
    private WebEngine engine;

    public UserManager() {
        usersSet = new HashSet<>();
        engine = new WebEngine();
    }

    public WebEngine getEngine() {
        return engine;
    }

    public synchronized void addUser(String username, UserType userType, Integer userId) {
        usersSet.add(new SingleUser(userType,username, userId));
    }

    public synchronized void removeUser(String username) {
        usersSet.forEach(user -> {
            if(user.getUserName().equals(username)) {
                usersSet.remove(user);
            }
        });
    }

    public synchronized Set<SingleUser> getUsers() {
        return Collections.unmodifiableSet(usersSet);
    }

    public boolean isUserExists(String username) {
        return usersSet.stream().filter(user -> user.getUserName().equals(username)).count() > 0;
    }


    public void addAreaToUser(String area, String userName) throws AreaAlreadyExistException {
        for(SingleUser user : usersSet) {
            if (user.getAreas().contains(area)) {
                throw new AreaAlreadyExistException();
            }
        }

        usersSet.forEach(user -> {
            if(user.getUserName().equals(userName)) {
                user.addNewArea(area);
            }
        });
    }

    public UserType getUserType(String userName) {
        UserType userType = null;
         for(SingleUser user : usersSet) {
             if (user.getUserName().equals(userName)) {
                 userType =  user.getUserType();
             }
         }
         return userType;
    }
}
