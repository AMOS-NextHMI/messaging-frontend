package com.example.messaging_frontend.models;

import java.util.List;

public class Conversation {
    private String conversationId;
    private String name;
    private List<Contact> member;
    private List<Message> messages;

    public Conversation(String conversationId, String name, List<Contact> member, List<Message> messages) {
        this.conversationId = conversationId;
        this.name = name;
        this.member = member;
        this.messages = messages;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Contact> getMember() {
        return member;
    }

    public void setMember(List<Contact> member) {
        this.member = member;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
