package com.GameServer.Feedback;

import com.GameServer.Client;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class MoveFeedback extends Feedback{
    private int distanceUp;
    private int distanceRight;
    private Client userToMove;

    public MoveFeedback(Client to, int distanceUp, int distanceRight, Client userToMove) {
        super(to);
        this.distanceUp = distanceUp;
        this.distanceRight = distanceRight;
        this.userToMove = userToMove;
    }

    @Override
    public byte[] toBytes() {
        JSONObject jsonObject = new JSONObject().put("method", "move").put("username", userToMove.getUsername()).put("up", distanceUp).
                put("right", distanceRight);
        return jsonObject.toString().getBytes(StandardCharsets.UTF_8);
    }
}
