package com.example.messaging_frontend.models;

/**
 * describes a singular message
 * Messages can be sorted by time_stamp
 */
public class Message {
    private String userId;
    private String _id;
    String  createdAt;
    String conversationId;
    String messageText;

    public Message(String userId, String _id, String createdAt, String conversationId, String messageText) {
        this.userId = userId;
        this._id = _id;
        this.createdAt = createdAt;
        this.conversationId = conversationId;
        this.messageText = messageText;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
