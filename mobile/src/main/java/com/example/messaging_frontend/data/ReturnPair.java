package com.example.messaging_frontend.data;

public class ReturnPair {
    public String message;
    public int code;
    public ReturnPair(String message, int code){
        this.message = message;
        this.code = code;

    }

    public String toString(){
        return "Message: " + this.message + " code: " + Integer.toString(this.code);
    }
}
