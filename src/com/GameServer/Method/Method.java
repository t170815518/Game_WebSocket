package com.GameServer.Method;

import com.GameServer.Client;
import com.GameServer.Feedback.Feedback;

import java.util.ArrayList;

/**
 * Represents commands sent by client.
 */
public abstract class Method {

	/**
	 * from which Client the message from
	 */
	protected Client from;
	protected ArrayList<Feedback> feedbacksBuffer;
	protected boolean isMultiListeners = false;

	public Client getFrom() {
		return this.from;
	}

	public void setFrom(Client from) {
		this.from = from;
	}

	public Method(Client from) {
		this.from = from;
	}

	/**
	 * processes User's command.
	 * com.GameServer.Feedback.Feedback will be sent after each execution.
	 * @return
	 */
	public abstract Feedback execute();

	public ArrayList<Feedback> getFeedbacksBuffer() {
		return feedbacksBuffer;
	}

	public void setFeedbacksBuffer(ArrayList<Feedback> feedbacksBuffer) {
		this.feedbacksBuffer = feedbacksBuffer;
	}

	public boolean isMultiListeners() {
		return isMultiListeners;
	}

	public void setMultiListeners(boolean multiListeners) {
		isMultiListeners = multiListeners;
	}

}