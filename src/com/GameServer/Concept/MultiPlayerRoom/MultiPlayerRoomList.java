package com.GameServer.Concept.MultiPlayerRoom;

import com.GameServer.Client;

import java.util.ArrayList;
import java.util.HashMap;


public class MultiPlayerRoomList {
    private HashMap<String, MultiPlayerRoom> rooms;  // Key=>String: room name
    private static MultiPlayerRoomList singleton = null;

    private MultiPlayerRoomList() {
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
