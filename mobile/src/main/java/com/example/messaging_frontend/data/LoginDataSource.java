package com.example.messaging_frontend.data;

import android.util.Log;

import com.example.messaging_frontend.data.model.LoggedInUser;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.HttpURLConnection;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private static final int CONNECT_TIMEOUT = 4000;
    private static final int READ_TIMEOUT = 4000;
    private static final String SERVER_URL = "http://130.149.172.169/login/";
//    private static final String SERVER_URL = "http://127.0.0.1/login";

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
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");

        String jsonLoginString = new JSONObject()
                .put("email", email)
                .put("password", password)
                .toString();
        // send payload
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(jsonLoginString, JSON);
        Request request = new Request.Builder()
                .url(ServerUrl)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String token = response.body().string();
            return token;
        }


    }

    public void logout() {
        // TODO: revoke authentication
        // TODO: Does that entail deleting the LoggedInUser instance?
    }
}
