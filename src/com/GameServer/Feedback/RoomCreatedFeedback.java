package com.GameServer.Feedback;

import com.GameServer.Client;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class RoomCreatedFeedback extends Feedback {

	/**
	 * identifies the room System assigns to User
	 */
	private String roomId;
	private String quizId;

	public RoomCreatedFeedback(Client to, String roomId, String quizId) {
		super(to);
		this.roomId = roomId;
		this.quizId = quizId;
	}

	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Override
	public byte[] toBytes() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("method", "roomJoined");
		jsonObject.put("roomId", roomId);
		jsonObject.put("quizId", quizId);
		return jsonObject.toString().getBytes(StandardCharsets.UTF_8);
	}
}