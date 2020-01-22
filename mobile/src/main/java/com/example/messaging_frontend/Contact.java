package com.example.messaging_frontend;

public class Contact {
    private String name;
    private String id;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Contact(String name, String id){
        this.name = name;
        this.id = id;
    }
}
