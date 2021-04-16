package com.GameServer.Method;

import com.GameServer.Client;
import com.GameServer.Feedback.Feedback;
import com.GameServer.Feedback.RoomJoinedFeedback;
import com.GameServer.Method.Method;
import com.GameServer.Room.Room;
import com.GameServer.Room.RoomList;

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