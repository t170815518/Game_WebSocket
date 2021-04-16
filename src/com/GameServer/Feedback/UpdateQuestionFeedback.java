package com.GameServer.Feedback;

import com.GameServer.Client;
import com.GameServer.Exceptions.QuestionRunningOutException;
import com.GameServer.Room.Room;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Signify User start playing next question
 * Notes: will send final leaderboard when the quiz completes
 */
public class UpdateQuestionFeedback extends Feedback {

	/**
	 * Snapshot of scores after each round
	 */
	private boolean isQuestionRunningOut = false;
	private Room room;

	public UpdateQuestionFeedback(Client to, Room room) {
		super(to);
		this.room = room;
		// make all user un-ready at each new round
		room.clearReadyStatus();
	}

	public boolean isIsQuestionRunningOut() {
		return this.isQuestionRunningOut;
	}

	public void setIsQuestionRunningOut(boolean isQuestionRunningOut) {
		this.isQuestionRunningOut = isQuestionRunningOut;
	}

	@Override
	public byte[] toBytes() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("method", "updateQuestion");
		jsonObject.put("quizId", room.getQuizId());
		jsonObject.put("roomId", room.getId());
		JSONArray scores = new JSONArray();
		for (Map.Entry<String, Integer> entry: room.getLeaderboards().entrySet()) {
			JSONObject score = new JSONObject();
			score.put("name", entry.getKey());
			score.put("score", entry.getValue());
			scores.put(score);
		}
		jsonObject.put("scores", scores);
		try {
			jsonObject.put("question", room.getNextQuestion());
		} catch (QuestionRunningOutException e) {
			jsonObject.put("question", "false"); // indicates quiz is over
		}
		return jsonObject.toString().getBytes(StandardCharsets.UTF_8);
	}
}