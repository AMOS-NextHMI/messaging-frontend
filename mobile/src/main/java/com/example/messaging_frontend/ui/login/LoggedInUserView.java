package com.example.messaging_frontend.ui.login;

import com.auth0.android.jwt.JWT;

import java.net.URI;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private URI profilePicture; //optional
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }
}

