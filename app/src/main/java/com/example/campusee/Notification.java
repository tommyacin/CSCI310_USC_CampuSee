package com.example.campusee;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Notification {
    String sendTime;
    Event eventId;
    Long tsLong;

    Notification(Event eventId) {
        tsLong = System.currentTimeMillis()/1000;

        this.sendTime = tsLong.toString();
        this.eventId = eventId;
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

    public Event getEvent(){
        return eventId;
    }
}
