package com.example.campusee;

import android.graphics.Bitmap;

import java.sql.Time;
import java.util.*;

public class Event {
    public String publisherId;
    public String description;
    public String title;
    public String time;
    //public Bitmap icon;
    public Boolean status; // true = published, false = unpublished

    Event() { }

    Event(String publisherId, String title, String description, String time) {
        this.publisherId = publisherId;
        this.title = title;
        this.description = description;
        this.time = time;
        //this.icon = icon;
        this.status = false;
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
}
