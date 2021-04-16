package com.GameServer.Method;

import com.GameServer.Client;
import com.GameServer.Feedback.Feedback;
import com.GameServer.Feedback.UserReadyFeedback;
import com.GameServer.Room.Room;
import com.GameServer.Room.RoomList;

import java.util.ArrayList;

/**
 * System sends feedback to all users to startQuiz only when all users are ready
 */
public class ReadyForQuizMethod extends Method {

	private String roomId;

	public ReadyForQuizMethod(Client from, String roomId) {
		super(from);
		this.roomId = roomId;
	}

	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Override
	public Feedback execute() {
		feedbacksBuffer = new ArrayList<>();
		isMultiListeners = true;

		Room room = RoomList.getSingleton().findRoom(roomId);
		room.userReady(from);

		isMultiListeners = true;
		for (Client client: room.getUsers()) {
			Feedback feedback = new UserReadyFeedback(client, from);
			feedbacksBuffer.add(feedback);
		}
		return null;
	}
}