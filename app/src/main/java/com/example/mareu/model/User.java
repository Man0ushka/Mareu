package com.example.mareu.model;

public class User {
    String name;
    String eMail;

    public User(String name, String eMail)
    {
        this.name=name;
        this.eMail=eMail;
    }
    public User(String eMail)
    {
        this.eMail=eMail;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return eMail;
    }

    public void setEmail(String eMail) {
        this.eMail = eMail;
    }

    public void setName(String name) {
        this.name = name;
    }
}
