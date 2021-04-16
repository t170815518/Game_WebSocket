package com.GameServer.Method;

import com.GameServer.Client;
import com.GameServer.Feedback.Feedback;
import com.GameServer.Feedback.RoomCreatedFeedback;
import com.GameServer.Room.Room;
import com.GameServer.Room.RoomList;

/**
 * User not in multi-player quiz mode can send the com.GameServer.com.GameServer.Method.Method.CreateRoomMethod to Server.
 */
public class CreateRoomMethod extends Method {
    protected String quizId;

    public CreateRoomMethod(Client from, String quizId) {
        super(from);
        this.quizId = quizId;
    }

    @Override
    public Feedback execute() {
        if (this.from.isIsReady()) {
            return null;
        }
        Room newRoomAdded = RoomList.getSingleton().createRoom(from, quizId);
        Feedback feedback = new RoomCreatedFeedback(from, newRoomAdded.getId(), quizId);
        return feedback;
    }
}