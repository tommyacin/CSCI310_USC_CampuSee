package com.example.campusee;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Notification {
    String title;
    String description;
    String time;
    String publisherId;
    String notifId;
    String dbEventId;
    Event eventId;


    String sendTime;

    Long tsLong;

    Notification(){}

    Notification(String title, String description, String time, String publisherId, String notifId, String dbEventId) {
        tsLong = System.currentTimeMillis()/1000;

        this.notifId = notifId;
        this.sendTime = tsLong.toString();
        this.title =  title;
        this.description = description;
        this.time = time;
        this.publisherId = publisherId;
        this.dbEventId = dbEventId;

    }


    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("sendTime", sendTime);
        result.put("eventId", eventId);

        return result;
    }

    public Long getTimestamp(){
        return tsLong;
    }

    public void setTimestamp(Long newTime){
        tsLong = newTime;
    }

    public void setEvent(Event event){
        eventId = event;
    }

    public String getTitle(){
        return title;
    }


    public String getDescription() {return description;}

    public String getNotifId() {return notifId;}

    public String getPublisherId(){return publisherId;}

    public String getDbEventId(){return dbEventId;}
}

