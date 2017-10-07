package server;

import message.*;
import java.util.HashMap;
import java.util.Map;

public class UserList {
    private HashMap<UserName, User> usersList;
    private int userCounter;
    private int maxUsers;

    UserList () {
        userCounter = 0;
        maxUsers = 1000;
        usersList = new HashMap<>();
    }

    void closeAll() {
        for(Map.Entry<UserName, User> entry : usersList.entrySet()) {
            remove(entry.getKey());
            entry.getValue().closeSocket();
        }
    }

    int getUserCounter() {
        return userCounter;
    }

    int getAndIncreaseUserCounter() {
        return ++userCounter;
    }

    int getCurrentUser() {
        return usersList.size();
    }

    int getMaxUsers() {
        return maxUsers;
    }

    synchronized void add(UserName userName, User user) {
        usersList.put(userName, user);
    }

    synchronized void remove (UserName userName) {
        usersList.remove(userName);
    }
}