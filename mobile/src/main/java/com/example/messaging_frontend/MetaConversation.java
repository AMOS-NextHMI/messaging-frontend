package com.example.messaging_frontend;

import android.os.Build;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Contains the meta data about a conversation.  Used as a cleaner way of sending Data
 * TODO: includes a contact instant and other relevant data, such as the latest message
 */
public class MetaConversation {
    private Contact myContact;
    private Message latestMessage;


    public MetaConversation(Builder builder)
    {
        this.myContact = builder.myContact;
        this.latestMessage = builder.latestMessage;
    }

    // builder
    public static class Builder {
        private Contact myContact;
        private Message latestMessage;

        public static Builder newInstance() {
            return new Builder();
        }

        private Builder() {
        }

        // setters
        public Builder setMyContact(Contact myContact) {
            this.myContact = myContact;
            return this;
        }

        public Builder setLatestMessage(Message latestMessage) {
            this.latestMessage = latestMessage;
            return this;
        }

        public MetaConversation build()
        {
            return new MetaConversation(this);
        }


    }





    // getters
    public Contact getContact() {
        return myContact;
    }

    public Message getLatestMessage() {
        return latestMessage;
    }


    @Override
    public String toString()
    {
        return "Contact = " + this.myContact + ", latest Message = " + this.latestMessage + ".";
    }


}
