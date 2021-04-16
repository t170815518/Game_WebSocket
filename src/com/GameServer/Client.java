package com.GameServer;

import javax.websocket.Session;


/**
 * Represents the client server connected with WebSocket, to store information about the connection
 */
public class Client {

	private String username;
	/**
	 * WebSocket uses Session to send message back to Client
	 */
	private Session session;
	private int avatarId = 1;
	private boolean isInQuiz = false;
	private boolean isReadyQuiz = false;

	public Client(String username, Session session) {
		this.username = username;
		this.session = session;
	}

	public int getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(int avatarId) {
		this.avatarId = avatarId;
	}

	public boolean isInQuiz() {
		return isInQuiz;
	}

	public void setInQuiz(boolean inQuiz) {
		isInQuiz = inQuiz;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Session getSession() {
		return this.session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public boolean isIsReady() {
		return this.isInQuiz;
	}

	public void setIsReady(boolean isReady) {
		this.isInQuiz = isReady;
	}

	public boolean isReadyQuiz() {
		return isReadyQuiz;
	}

	public void setReadyQuiz(boolean readyQuiz) {
		isReadyQuiz = readyQuiz;
	}
}