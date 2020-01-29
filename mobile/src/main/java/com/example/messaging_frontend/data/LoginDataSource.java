package com.example.messaging_frontend.data;

import android.util.Log;

import com.example.messaging_frontend.data.model.LoggedInUser;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.HttpURLConnection;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private static final int CONNECT_TIMEOUT = 4000;
    private static final int READ_TIMEOUT = 4000;

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            /* mock up data
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
             */
            /* Request:
             * RequestTyp: POST
             * RequestURL: http://130.149.172.169/login
             * Response: 200 - Token -> JSON
             * Error:
             * - 401 - { "error": "STRING" }
             * - 422 - Unprocessable Entity
             *
             * JSON Payload Post
             * {
             *   "username": "STRING",
             *   "password": "STRING"
             * }
             *
             * JSON Payload Response - A JWT
             * {
             *   "id": "STRING",
             *   "exp": "Date",
             *   "username": "STRING"
             * }
             */
            // send a login POST request
            String responsePayload = loginRequest("http://130.149.172.169/login", username, password);

            // parse response
            JSONObject responseJSON = new JSONObject().getJSONObject(responsePayload);

            LoggedInUser loggedInUser =
                    new LoggedInUser(
                            responseJSON.getString("id"),
                            responseJSON.getString("username"),
                            (Date) responseJSON.get("Date"));

            Log.i("login", "A brother made it");
            return new Result.Success<>(loggedInUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }




    private static String loginRequest(String ServerUrl, String userName, String password) throws Exception {
        URL url = new URL(ServerUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        connection.setConnectTimeout(CONNECT_TIMEOUT); // Why dis?
        connection.setReadTimeout(READ_TIMEOUT); // Why dis?
        connection.connect(); // Why dis?

        String jsonLoginString = new JSONObject()
                .put("username", userName)
                .put("password", password)
                .toString();

        // send payload
        try(OutputStream outputStream = connection.getOutputStream()) {
        byte[] input = jsonLoginString.getBytes(StandardCharsets.UTF_8);
        outputStream.write(input, 0, input.length);
        }

        // receive response
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return response.toString();
                }
    }

    public void logout() {
        // TODO: revoke authentication
        // TODO: Does that entail deleting the LoggedInUser instance?
    }
}
