package com.example.campusee;

import java.sql.Timestamp;

public class Notification {
    String sendTime;

    Notification() {
        Long tsLong = System.currentTimeMillis()/1000;
        this.sendTime = tsLong.toString();
    }
}
