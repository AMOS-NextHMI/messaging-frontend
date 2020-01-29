package com.example.messaging_frontend.data;

import android.util.Log;

import com.example.messaging_frontend.data.model.LoggedInUser;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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
            Log.i("login", "login: initialized.");
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
             *   "name": "STRING",
             *   "password": "STRING"
             * }
             *
             * JSON Payload Response - A JWT
             * {
             *   "id": "STRING",
             *   "exp": "Date",
             *   "name": "STRING"
             * }
             */
            // send a login POST request
            Log.i("login", "login: sending request.");

            String responsePayload = loginRequest("http://localhost/login", username, password);

            Log.i("login", "login: response received.  Parsing response.");

            // parse response
            JSONObject responseJSON = new JSONObject().getJSONObject(responsePayload);

            LoggedInUser loggedInUser =
                    new LoggedInUser(
                            responseJSON.getString("id"),
                            responseJSON.getString("name"),
                            (Date) responseJSON.get("Date"));

            Log.i("login", "A brother made it");
            return new Result.Success<>(loggedInUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }




    private static String loginRequest(String ServerUrl, String userName, String password) throws Exception {
        Log.i("login", "login: login request: Initializing URL.");
        URL url = new URL(ServerUrl);  // TODO: This isn't working
        Log.i("login", "login: login request: initialized URL, now defining connection.");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

//        connection.setConnectTimeout(CONNECT_TIMEOUT); // Why dis?
//        connection.setReadTimeout(READ_TIMEOUT); // Why dis?
        Log.i("login", "login: login request: Creating payload.");

        String jsonLoginString = new JSONObject()
                .put("name", userName)
                .put("password", password)
                .toString();
        // send payload

        Log.i("login", "login: login request: Sending payload.");

        try(DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
            Log.i("login", "login: login request: Sending payload: putting payload in a byte array.");
            byte[] input = jsonLoginString.getBytes(StandardCharsets.UTF_8);
            Log.i("login", "login: login request: Sending payload: writing to output stream.");
            outputStream.write(input, 0, input.length);
        }
        Log.i("login", "login: login request: Awaiting response.");

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
