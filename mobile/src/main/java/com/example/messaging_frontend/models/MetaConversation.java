package com.example.messaging_frontend.models;

import java.util.List;

/**
 * Contains the meta data about a conversation.  Used as a cleaner way of sending Data
 * TODO: includes a contact instant and other relevant data, such as the latest message
 */
public class MetaConversation {
    private String _id;

    String name;
    List<String> members;
    List<Message> messages;

    public MetaConversation(String _id, String name, List<String> members, List<Message> messages) {
        this._id = _id;
        this.name = name;
        this.members = members;
        this.messages = messages;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}

