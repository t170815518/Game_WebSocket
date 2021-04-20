package com.GameServer.Method;

import com.GameServer.Client;
import com.GameServer.Concept.QuizRoom.Room;
import com.GameServer.Concept.QuizRoom.RoomList;
import com.GameServer.Feedback.Feedback;
import com.GameServer.Feedback.RoomJoinedFeedback;

public class JoinRoomMethod extends Method {

	private String roomId;

	public JoinRoomMethod(Client from, String roomId) {
		super(from);
		this.roomId = roomId;
	}

	// todo: send feedback to all other users
	@Override
	public Feedback execute() {
		Room roomToJoin = RoomList.getSingleton().findRoom(String.valueOf(roomId));
		if (roomToJoin == null) {
			return null;
		}
		roomToJoin.joinUser(from);
		Feedback feedback = new RoomJoinedFeedback(from, from, roomToJoin);
		return feedback;
	}
}