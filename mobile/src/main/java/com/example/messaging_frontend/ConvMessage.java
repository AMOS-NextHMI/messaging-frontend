package com.example.messaging_frontend;

public class ConvMessage {
    private Contact sender;
    private String convID;
    private String body;
    private long time_stamp;


    public ConvMessage(Builder builder) {
        this.sender = builder.sender;
        this.body = builder.body;
        this.time_stamp = builder.time_stamp;
        this.convID = builder.convID;
    }

    public String getBody() {
        return body;
    }

    public static class Builder {

        private String body;
        private long time_stamp;
        private String convID;
        private Contact sender;

        public static Builder newInstance() { return new Builder(); }

        private Builder() {}

        // setters


        public Builder setBody(String val) {
            body = val;
            return this;
        }

        public Builder setTime_stamp(long val) {
            time_stamp = val;
            return this;
        }

        public Builder setConvID(String val) {
            convID = val;
            return this;
        }

        public Builder setSender(Contact val) {
            sender = val;
            return this;
        }

        public ConvMessage build() {
            return new ConvMessage(this);
        }
    }



}
