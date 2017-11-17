package server;

import message.*;
import java.util.*;

public class UserList {
    private HashMap<UserName, User> usersList;
    private int userCounter;
    private int maxUsers;

    UserList () {
        userCounter = 0;
        maxUsers = 1000;
        usersList = new HashMap<>();
    }

    void removeAll() {
        for(Map.Entry<UserName, User> entry : usersList.entrySet()) {
            entry.getValue().disconnect();
            remove(entry.getKey());
        }
    }

    User getUser (UserName userName) {
        return usersList.get(userName);
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

    public synchronized void add(UserName userName, User user) {
        usersList.put(userName, user);
    }

    public synchronized void remove (UserName userName) {
        usersList.remove(userName);
    }

    public void print(){
        for(Map.Entry<UserName, User> entry : usersList.entrySet()) {
            System.out.println(entry.getKey().getUserName());
        }
    }
}