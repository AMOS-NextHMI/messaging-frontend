package com.example.messaging_frontend.models;

public class Contact {
    private String name;
    private String id;
    private java.net.URL profilUrl = null;



    public Contact(String name, String id){
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public java.net.URL getProfileUrl() {
        return profilUrl;
    }

    /**
     * Sets profile picture URL
     * TODO: potential security risk.  A foreign URL is set without checking it out.
     */
    public void setProfilUrl(java.net.URL profilUrl) {
        this.profilUrl = profilUrl;
    }
}
