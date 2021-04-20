package com.GameServer.Method;

import com.GameServer.Client;
import com.GameServer.Concept.MultiPlayerRoom.MultiPlayerRoom;
import com.GameServer.Concept.MultiPlayerRoom.MultiPlayerRoomList;
import com.GameServer.Feedback.Feedback;
import com.GameServer.Feedback.MoveFeedback;

import java.util.ArrayList;


public class MoveMethod extends Method{
    private int distanceUp;
    private int distanceRight;
    private String roomKey;

    public MoveMethod(Client from, int distanceUp, int distanceRight, String roomKey) {
        super(from);
        this.distanceUp = distanceUp;
        this.distanceRight = distanceRight;
        this.roomKey = roomKey;
    }

    @Override
    public Feedback execute() {
        MultiPlayerRoom room = MultiPlayerRoomList.getSingleton().getRoomByKey(roomKey);

        isMultiListeners = true;
        feedbacksBuffer = new ArrayList<>();

        for (Client user: room.getUsers()) {
            Feedback feedback = new MoveFeedback(user, distanceUp, distanceRight, from);
            feedbacksBuffer.add(feedback);
        }
        return null;
    }
}
