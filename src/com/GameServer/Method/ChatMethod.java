package com.GameServer.Method;

import com.GameServer.Client;
import com.GameServer.Concept.MultiPlayerRoom.MultiPlayerRoom;
import com.GameServer.Concept.MultiPlayerRoom.MultiPlayerRoomList;
import com.GameServer.Feedback.ChatFeedback;
import com.GameServer.Feedback.Feedback;
import com.GameServer.Feedback.UserJoinMultiRoomFeedback;

import java.util.ArrayList;

public class ChatMethod extends Method{
    private String roomKey;
    private String message;

    public ChatMethod(Client from, String roomKey, String message) {
        super(from);
        this.roomKey = roomKey;
        this.message = message;
    }

    @Override
    public Feedback execute() {
        MultiPlayerRoom room = MultiPlayerRoomList.getSingleton().getRoomByKey(roomKey);

        isMultiListeners = true;
        feedbacksBuffer = new ArrayList<>();
        for (Client user: room.getUsers()) {
            Feedback feedback = new ChatFeedback(user, message, from);
            feedbacksBuffer.add(feedback);
        }

        return null;
    }
}
