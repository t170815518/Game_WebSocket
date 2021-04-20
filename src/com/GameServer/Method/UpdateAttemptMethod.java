package com.GameServer.Method;

import com.GameServer.Client;
import com.GameServer.Concept.QuizRoom.Room;
import com.GameServer.Concept.QuizRoom.RoomList;
import com.GameServer.Feedback.Feedback;
import com.GameServer.Feedback.UpdateQuestionFeedback;

import java.util.ArrayList;

/**
 * only receive feedback after all users in room are ready (submitted)
 * TODO: finish room
 */
public class UpdateAttemptMethod extends Method {

	private boolean isCorrectAnswer;
	private String roomId;

	public UpdateAttemptMethod(Client from, boolean isCorrectAnswer, String roomId) {
		super(from);
		this.isCorrectAnswer = isCorrectAnswer;
		this.roomId = roomId;
	}

	public boolean isIsCorrectAnswer() {
		return this.isCorrectAnswer;
	}

	public void setIsCorrectAnswer(boolean isCorrectAnswer) {
		this.isCorrectAnswer = isCorrectAnswer;
	}

	@Override
	public Feedback execute() {
		isMultiListeners = true;
		feedbacksBuffer = new ArrayList<>();

		Room room = RoomList.getSingleton().findRoom(String.valueOf(roomId));
		room.userRespond(from, isCorrectAnswer);
		if (room.isAllReady()) {
			for (Client user: room.getUsers()) {
				Feedback feedback = new UpdateQuestionFeedback(user, room);
				feedbacksBuffer.add(feedback);
			}
		}
		return null;
	}
}