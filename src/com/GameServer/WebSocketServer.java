package com.GameServer;

import com.GameServer.Feedback.Feedback;
import com.GameServer.Method.*;
import org.glassfish.tyrus.server.Server;
import org.json.JSONException;
import org.json.JSONObject;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Logger;


@ServerEndpoint(value = "/multi_quiz/{username}")
public class WebSocketServer {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private static Collection<Client> users = new ArrayList<>();

	public Collection<Client> getUsers() {
		return this.users;
	}

	public void setUsers(Collection<Client> users) {
		this.users = users;
	}

	@OnOpen
	public void OnOpen(Session session, @PathParam("username") String username) {
		logger.info(String.format("Session created, User %s joins the game.", username));
		// add online user
		Client client = new Client(username, session);
		users.add(client);
	}

	@OnMessage
	public void OnMessage(byte[] message) {
		String s = new String(message, StandardCharsets.UTF_8);
		s = s.substring(1, s.length() - 1).replace("\\", "");  // clean-up data for parse
		logger.info(String.format("Message got from Session: %s", s));
		JSONObject jsonObject = null;

		try {
			jsonObject = new JSONObject(s);
		} catch (JSONException e) {
			logger.warning(String.format("Cannot parse %s", s));
			return;
		}

		String methodIdentifier = jsonObject.getString("method");
		if (methodIdentifier.equals("ping")) {
			return;
		}
		String username = jsonObject.getString("username");
		String roomId;
		Client client = null;

		for (Client client1: users) {
			if (client1.getUsername().equals(username)) {
				client = client1;
			}
		}

		Method method = null;
		switch (methodIdentifier) {
			case "createRoom":
				String quizId = jsonObject.getString("quizId");
				method = new CreateRoomMethod(client, quizId);
				break;
			case "joinRoom":
				roomId = jsonObject.getString("roomId");
				method = new JoinRoomMethod(client, roomId);
				break;
			case "quizReady":
				roomId = jsonObject.getString("roomId");
				method = new ReadyForQuizMethod(client, roomId);
				break;
			case "startQuiz":
				roomId = jsonObject.getString("roomId");
				method = new StartQuizMethod(client, roomId);
				break;
			case "updateAttempt":
				boolean isCorrect = jsonObject.getBoolean("isCorrect");
				roomId = jsonObject.getString("roomId");
				method = new UpdateAttemptMethod(client, isCorrect, roomId);
		}
		Feedback feedback = method.execute();
		if (!method.isMultiListeners()) {
			sendFeedback(feedback);
		} else {
			sendFeedback(method.getFeedbacksBuffer());
		}
	}

	@OnClose
	public void OnClose(Session session) {
		for (Client user: users) {
			if (user.getSession().equals(session)) {
				users.remove(user);
				logger.info(String.format("User %s leaves the game.", user.getUsername()));
				break;
			}
		}
	}

	/**
	 * 
	 * @param feedback
	 */
	public void sendFeedback(Feedback feedback) {
		Client target = feedback.getTo();
		try {
			target.getSession().getBasicRemote().sendBinary(ByteBuffer.wrap(feedback.toBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param feedbacks
	 */
	public void sendFeedback(ArrayList<Feedback> feedbacks) {
		for (Feedback feedback: feedbacks) {
			sendFeedback(feedback);
		}
	}

	/**
	 * Entry point to start the server
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = new Server("localhost", 8025, "/websockets", null, WebSocketServer.class);

		// Assume server never stops
		try {
			try {
				server.start();
			} catch (DeploymentException e) {
				e.printStackTrace();
			}
			// to maintain the server alive
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Please press a key to stop the server.");
			reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			server.stop();
		}
	}
}