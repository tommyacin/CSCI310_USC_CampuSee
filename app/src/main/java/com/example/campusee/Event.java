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
    public Boolean status;
    public String building;

    Event() { }

    Event(String publisherId, String building, String title, String description, String time, String date, int radius, String iconFileName) {
        this.publisherId = publisherId;
        this.building = building;
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
        result.put("iconFileName", iconFileName);
        result.put("building", building);
        result.put("status", status);

        return result;
    }

    public void setStatus(Boolean publish) {
        this.status = publish;  // true = published, false = unpublished
    }

    public int getRadius(){
        return radius;
    }
}
