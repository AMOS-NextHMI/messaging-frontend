package com.example.messaging_frontend.data.model;

import java.util.Date;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String userName;
    private Date expiration;

    public LoggedInUser(String userId, String displayName, Date expiration) {
        this.userId = userId;
        this.userName = displayName;
        this.expiration = expiration;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Date getExpiration() {
        return expiration;
    }
}
