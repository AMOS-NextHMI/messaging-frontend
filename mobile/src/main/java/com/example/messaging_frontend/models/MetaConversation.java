package com.example.messaging_frontend.models;

/**
 * Contains the meta data about a conversation.  Used as a cleaner way of sending Data
 * TODO: includes a contact instant and other relevant data, such as the latest message
 */
public class MetaConversation {
    private String conversationId;
    private Message lastMessage;


    public MetaConversation(String conversationId, Message lastMessage) {
        this.conversationId = conversationId;
        this.lastMessage = lastMessage;
    }

    public String getConversationId() {
        return conversationId;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }
}

