package com.GameServer.Feedback;

import com.GameServer.Client;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;


public class UserJoinMultiRoomFeedback extends Feedback{
    private Client newUser;
    private int avartarId;

    public UserJoinMultiRoomFeedback(Client to, Client newUser, int avartarId) {
        super(to);
        this.newUser = newUser;
        this.avartarId = avartarId;
    }

    @Override
    public byte[] toBytes() {
        JSONObject jsonObject = new JSONObject();
        jsonObject = jsonObject.put("method", "joinMultiRoom").put("username", newUser.getUsername()).put("avatarId", avartarId);
        return jsonObject.toString().getBytes(StandardCharsets.UTF_8);
    }
}
