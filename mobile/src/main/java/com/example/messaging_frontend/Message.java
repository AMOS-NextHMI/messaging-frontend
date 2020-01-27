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


    private int userID;

    private String title;
    private String body;

       public Message(int userID,String title,String body) {
            this.userID = userID;

            this.title = title;
            this.body=body;
    }

    public String getBody() {
        return body;
    }

    public int getUserID() {
        return userID;
    }


    public String getTitle() {
        return title;
    }



    public void setUserID(int contactID) {
        this.userID = userID;
    }



    public void setTitle(String title) {
        this.title = title;
    }


}
