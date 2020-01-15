package com.example.messaging_frontend;

public class Contact {
    private String name;
    private int id;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Contact(String name, int id){
        this.name = name;
        this.id = id;
    }
}
