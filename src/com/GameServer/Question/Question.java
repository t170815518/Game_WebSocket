package com.GameServer.Question;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;


public class Question {
    private JSONObject json;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private static final String QUIZ_SEARCH_BASE_URL = "https://ssad-api.herokuapp.com/api/v1/quiz";
    private static final String QUESTION_SEARCH_BASE_URL = "https://ssad-api.herokuapp.com/api/v1/question";

    public Question(JSONObject json) {
        this.json = json;
    }

    public static ArrayList<Question> queryQuestions(String quizId) {
        try {
            ArrayList<Question> questionsArray = new ArrayList<>();
            URL quizURL = new URL(QUIZ_SEARCH_BASE_URL + "/" + quizId);
            JSONObject quizJson = queryURL(quizURL);
            JSONArray questions = quizJson.getJSONArray("question_list");

            for (int i = 0; i < questions.length(); i++) {
                String questionId = questions.getString(i);
                URL questionUrl = new URL(QUESTION_SEARCH_BASE_URL + "/" + questionId);
                JSONObject questionJson = queryURL(questionUrl);
                Question q = new Question(questionJson);
                questionsArray.add(q);
            return questionsArray;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JSONObject queryURL(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setDoOutput(true);
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String line = "";
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            line += inputLine;
        }
        in.close();

        JSONObject quizJson = new JSONObject(line);
        return quizJson;
    }
}
