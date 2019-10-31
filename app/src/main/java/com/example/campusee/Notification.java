package com.example.campusee;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Notification {
    String sendTime;
    Event eventId;

    Notification(Event eventId) {
        Long tsLong = System.currentTimeMillis()/1000;
        this.sendTime = tsLong.toString();

        this.eventId = eventId;
    }


    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("sendTime", sendTime);
        result.put("eventId", eventId);

        return result;
    }
}
