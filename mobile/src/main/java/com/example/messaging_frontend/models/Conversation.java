package com.example.messaging_frontend.models;

import java.util.List;

public class Conversation {
    private String conversationID;
    private String conversationName;
    private List<Contact> members;
    private List<Message> messages;

    public Conversation(String conversationID, String conversationName, List<Contact> members, List<Message> messages) {
        this.conversationID = conversationID;
        this.conversationName = conversationName;
        this.members = members;
        this.messages = messages;
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public String getConversationName() {
        return conversationName;
    }

    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }

    public List<Contact> getMembers() {
        return members;
    }

    public void setMembers(List<Contact> members) {
        this.members = members;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
