package com.example.campusee;


public class User {
    public String email;
    public String password;

    User() { }

    User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void sendNotification(Event eventId) {

    }

    public void subscribe(Publisher publisherId) {

    }

    public void unsubscribe(Publisher publisherId) {

    }
}
