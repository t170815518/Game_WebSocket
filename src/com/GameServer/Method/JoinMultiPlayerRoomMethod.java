package com.GameServer.Method;

import com.GameServer.Client;
import com.GameServer.Concept.MultiPlayerRoom.MultiPlayerRoom;
import com.GameServer.Concept.MultiPlayerRoom.MultiPlayerRoomList;
import com.GameServer.Feedback.Feedback;
import com.GameServer.Feedback.UserJoinMultiRoomFeedback;

import java.util.ArrayList;


public class JoinMultiPlayerRoomMethod extends Method{
    private String roomKey;
    private int avatarId;

    public JoinMultiPlayerRoomMethod(Client from, String roomKey, int avatarId) {
        super(from);
        this.roomKey = roomKey;
        this.avatarId = avatarId;
    }

    @Override
    public Feedback execute() {
        MultiPlayerRoom room = MultiPlayerRoomList.getSingleton().getRoomByKey(roomKey);
        MultiPlayerRoomList.getSingleton().joinUser(from, room);

        isMultiListeners = true;
        feedbacksBuffer = new ArrayList<>();
        for (Client user: room.getUsers()) {
            Feedback feedback = new UserJoinMultiRoomFeedback(user, from, avatarId);
            feedbacksBuffer.add(feedback);
        }

        return null;
    }
}
