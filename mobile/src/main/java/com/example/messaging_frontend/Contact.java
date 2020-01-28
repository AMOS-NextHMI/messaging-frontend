package com.example.messaging_frontend;

public class Contact {
    private String name;
    private String id;
    private java.net.URL profilUrl = null;

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
