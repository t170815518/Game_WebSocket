package com.GameServer.Method;

import com.GameServer.Client;
import com.GameServer.Concept.MultiPlayerRoom.MultiPlayerRoom;
import com.GameServer.Concept.MultiPlayerRoom.MultiPlayerRoomList;
import com.GameServer.Feedback.Feedback;
import com.GameServer.Feedback.UserLeaveMultiRoomFeedback;

import java.util.ArrayList;

public class LeaveMultiRoomMethod extends Method{
    private String roomKey;

    public LeaveMultiRoomMethod(Client from, String roomKey) {
        super(from);
        this.roomKey = roomKey;
    }

    @Override
    public Feedback execute() {
        MultiPlayerRoom room = MultiPlayerRoomList.getSingleton().getRoomByKey(roomKey);
        MultiPlayerRoomList.getSingleton().removeUser(from, room);

        isMultiListeners = true;
        feedbacksBuffer = new ArrayList<>();
        for (Client user: room.getUsers()) {
            Feedback feedback = new UserLeaveMultiRoomFeedback(user, from);
            feedbacksBuffer.add(feedback);
        }
        return null;
    }
}
