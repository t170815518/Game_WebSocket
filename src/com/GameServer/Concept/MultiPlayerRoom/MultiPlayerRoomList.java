package com.GameServer.Concept.MultiPlayerRoom;

import com.GameServer.Client;

import java.util.ArrayList;
import java.util.HashMap;


public class MultiPlayerRoomList {
    private HashMap<String, MultiPlayerRoom> rooms = new HashMap<>();  // Key=>String: room name
    private static MultiPlayerRoomList singleton = null;

    /**
     * Constructor of multi-player rooms. 9 Rooms by default.
     */
    private MultiPlayerRoomList() {
        for (int i = 0; i < 10; i++) {
            rooms.put(String.valueOf(i), new MultiPlayerRoom());
        }
    }

    static public MultiPlayerRoomList getSingleton() {
        if (singleton == null) {
            singleton = new MultiPlayerRoomList();
        }
        return singleton;
    }

    public void joinUser(Client client, MultiPlayerRoom room) {
        room.addUser(client);
    }

    public void removeUser(Client client, MultiPlayerRoom room) {
        room.removeUser(client);
    }

    public MultiPlayerRoom getRoomByKey(String key) {
        MultiPlayerRoom room = rooms.get(key);
        return room;
    }
}
