package com.example.campusee;

import android.app.Application;

public class Global extends Application {
    private String currentUserID;
    private String currentPublisherID;

    public String getCurrentUserID() {return currentUserID;}

    public String getCurrentPublisherID() {return currentPublisherID;}

    public void setCurrentUserID(String currentUserID) {
        this.currentUserID = currentPublisherID;
    }

    public void setCurrentPublisherID(String currentPublisherID) {
        this.currentPublisherID = currentPublisherID;
    }
}
