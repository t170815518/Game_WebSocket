package com.GameServer;

import org.glassfish.tyrus.client.ClientManager;
import org.json.JSONObject;

import javax.websocket.ClientEndpoint;
import javax.websocket.DeploymentException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;


@ClientEndpoint
public class ClientTestServer {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    static Session webSocketServer;

    String roomId;

    public static void main(String[] args) {
        ClientManager client = ClientManager.createClient();
        CountDownLatch latch = new CountDownLatch(1);
        try {
            client.connectToServer(ClientTestServer.class, new URI("ws://localhost:8025/websockets/multi_quiz/test_server"));
            latch.await();
        } catch (DeploymentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @OnOpen
    public void OnOpen(Session session) {
        String createRoom = "\"{\"method\": \"createRoom\", \"username\": \"test_server\", \"quizId\": \"60652e8becd0f6001569a181\"}\"";
        try {
            session.getBasicRemote().sendBinary(ByteBuffer.wrap(createRoom.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        webSocketServer = session;
    }

    @OnMessage
    public void OnMessage(byte[] bytes) {
        String s = new String(bytes, StandardCharsets.UTF_8);
        logger.info(String.format("Receive Info: %s", s));

        String roomId = "0";
        JSONObject jsonObject = new JSONObject(s);
        String identifer = jsonObject.getString("method");
        try {
            switch (identifer) {
                case "roomJoined":
                    roomId = jsonObject.getString("roomId");
                    String readyQuiz = String.format("\"{\"method\": \"quizReady\", \"username\": \"test_server\", \"roomId\": \"%s\"}\"", roomId);
                    webSocketServer.getBasicRemote().sendBinary(ByteBuffer.wrap(readyQuiz.getBytes(StandardCharsets.UTF_8)));
                    break;
                case "userReady":
                    String username = jsonObject.getString("username");
                    if (!username.equals("test_server")) {
                        String startQuiz = String.format("\"{\"method\": \"startQuiz\", \"username\": \"test_server\", \"roomId\": \"%s\"}\"", roomId);
                        webSocketServer.getBasicRemote().sendBinary(ByteBuffer.wrap(startQuiz.getBytes(StandardCharsets.UTF_8)));
                    }
                    break;
                case "updateQuestion":
                    String answer = String.format("\"{\"method\": \"updateAttempt\", \"isCorrect\": \"true\", \"roomId\": " +
                            "\"%s\", \"username\": \"test_server\"}\"", roomId);
                    webSocketServer.getBasicRemote().sendBinary(ByteBuffer.wrap(answer.getBytes(StandardCharsets.UTF_8)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
