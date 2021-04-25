package com.GameServer.Concept.QuizRoom;

import com.GameServer.Client;
import com.GameServer.Concept.Question.Question;
import com.GameServer.Exceptions.QuestionRunningOutException;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;


/**
 * Represents a channel for different battles
 */
public class Room {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private String id;
	/**
	 * The list of User in the room.
	 * TODO: When User disconnects/leave, users' list will be updated
	 */
	private ArrayList<Client> users = new ArrayList<>();
	/**
	 * HashMap<username, score>
	 */
	private HashMap<String, Integer> leaderboards = new HashMap<>();
	private int questionNum;
	private Client admin;
	private String quizId;
	private boolean isStart = false;
	private ArrayList<Question> questions;
	private int currentQuestionId = 0;

	public Room(String id, Client user, String quizId) {
		this.id = id;
		admin = user;
		this.quizId = quizId;
		leaderboards.put(user.getUsername(), 0);  // default value is 0
		this.questions = Question.queryQuestions(quizId);  // load question
	}



	public String getId() {
		return this.id;
	}

	public ArrayList<Client> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<Client> users) {
		this.users = users;
	}

	public HashMap<String, Integer> getLeaderboards() {
		return this.leaderboards;
	}

	public void setLeaderboards(HashMap<String, Integer> leaderboards) {
		this.leaderboards = leaderboards;
	}

	public int getQuestionNum() {
		return this.questionNum;
	}

	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}

	/**
	 * executes when the quiz is ended
	 */
	public void finish() {
		// TODO - implement Room.finish
		throw new UnsupportedOperationException();
	}

	public void joinUser(Client client) {
		users.add(client);
		leaderboards.put(client.getUsername(), 0);
		logger.info(String.format("User %s joins the room %s", client.getUsername(), this.id));
	}

	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}

	public boolean isAllReady() {
		for (Client user: users) {
			if (!user.isReadyQuiz()) {
				return false;
			}
		}
		return true;
	}

	public void userReady(Client user) {
		for (Client user1: users) {
			if (user1.getUsername().equals(user.getUsername())) {
				user.setReadyQuiz(true);
				break;
			}
		}
		logger.info(String.format("User %s in Room %s is ready.", user.getUsername(), id));
	}

	public void clearReadyStatus() {
		for (Client user1: users) {
			user1.setReadyQuiz(false);
		}
	}

	public ArrayList<Session> getAllSessions() {
		ArrayList<Session> sessions = new ArrayList<>();
		for (Client user: users) {
			sessions.add(user.getSession());
		}
		return sessions;
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean start) {
		isStart = start;
	}

	public void userRespond(Client user, boolean isCorrect) {
		user.setReadyQuiz(true);
		if (isCorrect) {
			int originScore = leaderboards.get(user.getUsername());
			leaderboards.put(user.getUsername(), originScore + 1);
		}
	}

	public Question getNextQuestion() throws QuestionRunningOutException {
		try {
			Question q = questions.get(currentQuestionId);
			currentQuestionId += 1;
			return q;
		} catch (IndexOutOfBoundsException e) {
			throw new QuestionRunningOutException();
		}
	}
}