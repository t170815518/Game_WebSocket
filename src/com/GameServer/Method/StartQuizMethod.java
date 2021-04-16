package com.GameServer.Method;

import com.GameServer.Client;
import com.GameServer.Feedback.Feedback;
import com.GameServer.Feedback.UpdateQuestionFeedback;
import com.GameServer.Room.Room;
import com.GameServer.Room.RoomList;

import java.util.ArrayList;
import java.util.logging.Logger;

public class StartQuizMethod extends Method{
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private String roomId;

    public StartQuizMethod(Client from, String roomId) {
        super(from);
        this.roomId = roomId;
    }

    @Override
    public Feedback execute() {
        Room room = RoomList.getSingleton().findRoom(roomId);
        room.setStart(true);
        room.joinUser(from);
        logger.info(String.format("Room %s has started.", room.getId()));

        isMultiListeners = true;
        feedbacksBuffer = new ArrayList<>();
        for (Client user: room.getUsers()) {
            Feedback feedback = new UpdateQuestionFeedback(user, room);
            feedbacksBuffer.add(feedback);
        }
        return null;
    }
}
