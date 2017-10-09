package server;

import message.*;
import java.util.*;

public class RoomList {
    private HashMap<RoomName, Room> roomsList;
    private int roomCounter;
    private int maxRooms;

    RoomList () {
        roomCounter = 0;
        maxRooms = 500;
        roomsList = new HashMap<>();
    }

    void removeAll () {
        for(Map.Entry<RoomName, Room> entry : roomsList.entrySet()) {
            remove(entry.getKey());
        }
    }

    int getRoomCounter() {
        return roomCounter;
    }

    int getAndIncreaseUserCounter() {
        return ++roomCounter;
    }

    int getCurrentUser() {
        return roomsList.size();
    }

    int getMaxRooms() {
        return maxRooms;
    }

    synchronized void add(RoomName roomName, Room room) {
        roomsList.put(roomName, room);
    }

    synchronized void remove (RoomName roomName) {
        roomsList.remove(roomName);
    }
} 
