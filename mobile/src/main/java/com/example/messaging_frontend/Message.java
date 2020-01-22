package com.example.messaging_frontend;

import java.util.Date;

/**
 * describes a singular message
 * Messages can be sorted by time_stamp
 */
public class Message {
    private String convID;
    private String body;
    private Date time_stamp;

    public Message(ConvMessage.Builder builder) {
    }


    //TODO: set up a proper builder.

    public static class Builder {
        private String body;
        private Date time_stamp;
        private String convID;

        public static Message.Builder newInstance() {
            return new Message.Builder();
        }

        Builder() {}

        // setters


        public Message.Builder setBody(String val) {
            body = val;
            return this;
        }

        public Message.Builder setTime_stamp(Date val) {
            time_stamp = val;
            return this;
        }

        public Message.Builder setConvID(String val) {
            convID = val;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }


    protected Message(Message.Builder builder) {
        this.body = builder.body;
        this.time_stamp = builder.time_stamp;
        this.convID = builder.convID;
    }


    //TODO: set up getters and setters

    public String getBody() {
        return this.body;
    }

}
