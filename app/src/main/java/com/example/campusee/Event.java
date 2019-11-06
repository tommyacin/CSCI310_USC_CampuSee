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
    public String date;
    public int notifId;
    public int radius;
    public String iconFileName;
    public double latLoc;
    public double longLoc;
    public Boolean status;

    Event() { }

    Event(String publisherId, String title, String description, String time, String date, double latLoc, double longLoc, int radius, String iconFileName) {
        this.publisherId = publisherId;
        this.title = title;
        this.description = description;
        this.time = time;
        this.date = date;
        this.iconFileName = iconFileName;
        this.status = false;
        this.radius = radius;

    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("publisherId", publisherId);
        result.put("title", title);
        result.put("description", description);
        result.put("time", time);
        result.put("date", date);
        //result.put("icon", icon);
        result.put("status", status);

        return result;
    }

    public void setStatus(Boolean publish) {
        this.status = publish;  // true = published, false = unpublished
    }

    public double getLongLoc(){
        return longLoc;
    }

    public double getLatLoc() {
        return latLoc;
    }

    public int getRadius(){
        return radius;
    }

    public String getTitle(){
        return title;
    }

}
