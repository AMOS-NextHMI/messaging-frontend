package com.example.messaging_frontend.models;

/**
 * describes a singular message
 * Messages can be sorted by time_stamp
 */
public class Message {
    private String senderUserId;
    private String messageText;
    private long timestamp;

    public Message(String senderUserId, String messageText, long timestamp) {
        this.senderUserId = senderUserId;
        this.messageText = messageText;
        this.timestamp = timestamp;
    }

    public String getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(String senderUserId) {
        this.senderUserId = senderUserId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
