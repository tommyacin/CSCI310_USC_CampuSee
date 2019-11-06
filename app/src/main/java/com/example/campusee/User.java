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

    private Context myContext;

    private List<Publisher> subscriptions;
    private List<Notification> notifications;

    private double latitude, longitude;

    User() { }

    User(String name, String email, String password, Context context) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.myContext = context;

        subscriptions = new ArrayList<Publisher>();
        notifications = new ArrayList<Notification>();

        latitude = 0;
        longitude = 0;
    }

    //need to implement this
//    public void sendNotification(Event eventId) {
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(myContext, "1234")
//                .setSmallIcon(R.drawable.app_icon)
//                .setContentTitle(eventId.title)
//                .setContentText(eventId.description)
//                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
//                .setPriority(NotificationCompat.PRIORITY_MAX);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(myContext);
//        notificationManager.notify(eventId.notifId, builder.build());
//
//        Notification newNotif = new Notification(eventId, eventId.notifId);
//        addNotification(newNotif);
//
//    }

    public void subscribe(Publisher publisherId) {
        subscriptions.add(publisherId);
    }

    public void unsubscribe(Publisher publisherId){
        subscriptions.remove(publisherId);
    }

    public List<Publisher> getSubscriptions(){
        return subscriptions;
    }

    public List<Notification> getNotifications(){
        return notifications;
    }

    public void addNotification(Notification newNotif){
        notifications.add(newNotif);
    }

    public void setCurrentLocation(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
