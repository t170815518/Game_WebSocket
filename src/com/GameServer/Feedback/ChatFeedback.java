package com.GameServer.Feedback;

import com.GameServer.Client;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class ChatFeedback extends Feedback{
    private String message;
    private Client from;

    public ChatFeedback(Client to, String message, Client from) {
        super(to);
        this.message = message;
        this.from = from;
    }

    @Override
    public byte[] toBytes() {
        JSONObject jsonObject = new JSONObject().put("method", "chat").put("from", from.getUsername()).put("content", message);
        jsonObject.toString().getBytes(StandardCharsets.UTF_8);
    }
}
