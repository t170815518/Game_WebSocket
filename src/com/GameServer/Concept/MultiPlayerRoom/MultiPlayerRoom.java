package com.GameServer.Concept.MultiPlayerRoom;

import com.GameServer.Client;
import java.util.ArrayList;


public class MultiPlayerRoom {
    private ArrayList<Client> users;

    public MultiPlayerRoom() {
    }

    public ArrayList<Client> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Client> users) {
        this.users = users;
    }

    public void addUser(Client user) {
        users.add(user);
    }

    public void removeUser(Client user) {
        users.remove(user);
    }
}
