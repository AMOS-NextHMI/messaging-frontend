package com.example.messaging_frontend;

/**
 * describes a singular message
 * Messages can be sorted by time_stamp
 */
public class Message {
    private Object sender;
    private Object body;
    private long time_stamp;


    //TODO: set up a proper builder.
    public Message(Object sender, Object body, long time_stamp) {
        this.sender = sender;
        this.body = body;
        this.time_stamp = time_stamp;
    }

    //TODO: set up getters and setters
}
