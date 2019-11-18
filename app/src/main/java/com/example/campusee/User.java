package com.example.campusee;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;

import java.util.Vector;

public class User {
    public String name;
    public String email;
    public String password;

    private List<Notification> notifications;

    User() { }

    User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.notifications = new ArrayList<>();
    }

    public List<Notification> getNotifications(){
        return notifications;
    }
}
