package com.GameServer.Feedback;

import com.GameServer.Client;

public abstract class Feedback {

	private Client to;
	private boolean isMultiListeners = false;

	public Client getTo() {
		return this.to;
	}

	public Feedback(Client to) {
		this.to = to;
	}

	public void setTo(Client to) {
		this.to = to;
	}

	/**
	 * Convert the class to jsonObject in bytes[]
	 */
	public abstract byte[] toBytes();

	public boolean isMultiListeners() {
		return isMultiListeners;
	}

	public void setMultiListeners(boolean multiListeners) {
		isMultiListeners = multiListeners;
	}
}