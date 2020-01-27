package com.example.messaging_frontend;

/**
 * describes a singular message
 * Messages can be sorted by time_stamp
 */
public class Message {
//    private Contact sender;
//
//    private String body;
//    private long time_stamp;
//
//
//    //TODO: set up a proper builder.
//    public Message(Contact sender, String body, long time_stamp) {
//        this.sender = sender;
//        this.body = body;
//        this.time_stamp = time_stamp;
//    }
//
//    public String getBody() {
//        return this.body;
//    }
//
//    public Contact getSender() {
//        return this.sender;
//    }
//
//    //TODO: set up getters and setters


    private int contactID;
    private int id;
    private String title;
    private Boolean completed;

       public Message(int contactID,int id,String title,Boolean completed) {
            this.contactID = contactID;
            this.id = id;
            this.title = title;
            this.completed=completed;
    }

    public int getContactID() {
        return contactID;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
