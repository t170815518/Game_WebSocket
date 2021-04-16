package com.GameServer.Feedback;

import com.GameServer.Client;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class UserReadyFeedback extends Feedback{
    private Client from;

    public UserReadyFeedback(Client to, Client from) {
        super(to);
        this.from = from;
    }

    @Override
    public byte[] toBytes() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("method", "userReady");
        jsonObject.put("username", from.getUsername());
        return jsonObject.toString().getBytes(StandardCharsets.UTF_8);
    }
}
