package com.example.messaging_frontend.models;

public class Contact {
    private String name;
    private String userId;
    private java.net.URL profilURL = null;



    public Contact(String name, String id){

        this.userId = id;
    }

    public String getId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.userId = id;
    }

    public java.net.URL getProfileUrl() {
        return profilURL;
    }

    /**
     * Sets profile picture URL
     * TODO: potential security risk.  A foreign URL is set without checking it out.
     */
    public void setProfilUrl(java.net.URL profilUrl) {
        this.profilURL = profilUrl;
    }
}
