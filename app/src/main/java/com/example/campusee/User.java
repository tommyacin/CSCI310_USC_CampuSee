package com.example.campusee;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {
    public String email;
    public String password;
    private DatabaseReference mDatabase;

    User() { }

    User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void sendNotification(Event eventId) {
        // Getting the database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Notification notif = new Notification();
        String key = mDatabase.child("notifications").push().getKey();
        mDatabase.child("notifications").child(key).setValue(notif);
    }

    public void subscribe(Publisher publisherId) {

    }

    public void unsubscribe(Publisher publisherId) {

    }
}
