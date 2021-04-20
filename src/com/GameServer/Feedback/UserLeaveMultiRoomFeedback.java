package com.GameServer.Feedback;

import com.GameServer.Client;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class UserLeaveMultiRoomFeedback extends Feedback{
    private Client from;

    public UserLeaveMultiRoomFeedback(Client to, Client from) {
        super(to);
        this.from = from;
    }

    @Override
    public byte[] toBytes() {
        JSONObject jsonObject = new JSONObject().put("method", "leaveMultiRoom").put("username", from.getUsername());
        return jsonObject.toString().getBytes(StandardCharsets.UTF_8);
    }
}
