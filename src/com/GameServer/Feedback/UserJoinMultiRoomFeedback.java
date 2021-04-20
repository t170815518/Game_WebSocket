package com.GameServer.Feedback;

import com.GameServer.Client;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;


public class UserJoinMultiRoomFeedback extends Feedback{
    private Client newUser;

    public UserJoinMultiRoomFeedback(Client to, Client newUser) {
        super(to);
        this.newUser = newUser;
    }

    @Override
    public byte[] toBytes() {
        JSONObject jsonObject = new JSONObject();
        jsonObject = jsonObject.put("method", "joinMultiRoom").put("username", newUser.getUsername()).put("avatarId", newUser.getAvatarId());
        return jsonObject.toString().getBytes(StandardCharsets.UTF_8);
    }
}
