package com.example.campusee;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import java.sql.Time;
import java.util.*;

public class Event {
    public String publisherId;
    public String description;
    public String title;
    public String time;
    public int notifId;
    public double[] location;
    public int radius;
    //public Bitmap icon;
    public Boolean status; // true = published, false = unpublished

    Event() { }

    Event(String publisherId, String title, String description, String time, int notifId, double[] loc, int radius) {
        this.publisherId = publisherId;
        this.title = title;
        this.description = description;
        this.time = time;
        this.notifId = notifId;
        //this.icon = icon;
        this.status = false;
        this.radius = radius;

        location = loc;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("publisherId", publisherId);
        result.put("title", title);
        result.put("description", description);
        result.put("time", time);
        //result.put("icon", icon);
        result.put("status", status);

        return result;
    }

    public double[] getLoc(){
        return location;
    }

    public int getRadius(){
        return radius;
    }

    public String getTitle(){
        return title;
    }

}
