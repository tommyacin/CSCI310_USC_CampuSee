package com.example.campusee;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;

import java.util.Vector;

public class User {
    public String email;
    public String password;

    private Context myContext;

    private List<Publisher> subscriptions;
    private List<Notification> notifications;
    private double[] location;

    //private Vector<pair<Double, Double>> currLocation;


    User() { }

    User(String email, String password, Context context) {
        this.email = email;
        this.password = password;
        this.myContext = context;

        location = new double[2];

        subscriptions = new ArrayList<Publisher>();
        notifications = new ArrayList<Notification>();
    }

    //need to implement this
    public void sendNotification(Event eventId) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(myContext, "1234")
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle(eventId.title)
                .setContentText(eventId.description)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(myContext);
        notificationManager.notify(1, builder.build());

    }

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
        location[0] = latitude;
        location[1] = longitude;
    }

    public double[] getCurrentLocation(){
        return location;
    }



}
