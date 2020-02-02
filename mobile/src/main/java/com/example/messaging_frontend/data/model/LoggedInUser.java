package com.example.messaging_frontend.data.model;

import java.util.Date;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String loginToken;
    private String email;

    public LoggedInUser(String loginToken, String email/*, int expiration*/) {
        this.loginToken = loginToken;
        this.email = email;
        /*this.expiration = expiration;*/
    }

    public String getLoginToken() {
        return loginToken;
    }

    public String getEmail() {
        return email;
    }



}
