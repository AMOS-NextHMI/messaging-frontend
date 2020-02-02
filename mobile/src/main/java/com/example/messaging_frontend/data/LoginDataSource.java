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
    private static final String SERVER_URL = "http://130.149.172.169/login";

    public Result<LoggedInUser> login(String email, String password) {


        try {
            // TODO: handle loggedInUser authentication
            /* mock up data
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
             */

            // send a login POST request

            final String email_HTTP = email;
            final String password_HTTP = password;

            final String[] responsePayload = new String[1];

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        responsePayload[0] = loginRequest(SERVER_URL, email_HTTP, password_HTTP);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
            thread.join();


            // parse response
            JSONObject responseJSON = new JSONObject().getJSONObject(responsePayload[0]);

            LoggedInUser loggedInUser =
                    new LoggedInUser(
                            responseJSON.getString("id"),
                            responseJSON.getString("username"));
//                            (int) responseJSON.get("Date"));

            return new Result.Success<>(loggedInUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }




    private static String loginRequest(String ServerUrl, String email, String password) throws Exception {
        URL url = new URL(ServerUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

//        connection.setConnectTimeout(CONNECT_TIMEOUT); // Why dis?
//        connection.setReadTimeout(READ_TIMEOUT); // Why dis?

        String jsonLoginString = new JSONObject()
                .put("email", email)
                .put("password", password)
                .toString();
        // send payload

        try(DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
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
