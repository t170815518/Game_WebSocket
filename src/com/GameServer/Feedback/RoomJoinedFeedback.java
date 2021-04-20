package com.GameServer.Feedback;

import com.GameServer.Client;
import com.GameServer.Concept.QuizRoom.Room;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class RoomJoinedFeedback extends Feedback {

	private Client newUser;
	private Room room;

	public RoomJoinedFeedback(Client to, Client newUser, Room room) {
		super(to);
		this.newUser = newUser;
		this.room = room;
	}

    public Client getNewUser() {
		return this.newUser;
	}

	public void setNewUser(Client newUser) {
		this.newUser = newUser;
	}

	@Override
	public byte[] toBytes() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("method", "roomJoined");
		jsonObject.put("quizId", room.getQuizId());
		jsonObject.put("roomId", room.getId());
		return jsonObject.toString().getBytes(StandardCharsets.UTF_8);
	}
}