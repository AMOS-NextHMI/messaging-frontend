package com.example.messaging_frontend.data.model;

/* --------------------------------------------------------------------------------------------
 * Copyright (c) 2017 TypeFox GmbH (http://www.typefox.io). All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 * ------------------------------------------------------------------------------------------ */


import java.util.Date;

public class UserMessage {

    /**
     * A user posted this message.
     */
    private String userId;
    private String userName;

    /**
     * A content of this message.
     */
    private String content;
    private long timestamp;

    public UserMessage(String userId, String UserName, String content, long timestamp) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreatedAt() {
        return timestamp;
    }
    public void setCreatedAt(long timestamp) {
        this.timestamp =timestamp;
    }
}
