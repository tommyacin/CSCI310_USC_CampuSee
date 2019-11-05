package com.example.campusee;

import android.app.Application;

public class Global extends Application {
    private String currentUserID;
    private String currentPublisherID;
    private Publisher currentPublisher;

    public String getCurrentUserID() {return currentUserID;}

    public String getCurrentPublisherID() {return currentPublisherID;}

    public Publisher getCurrentPublisher() {return  currentPublisher;}

    public void setCurrentUserID(String currentUserID) {
        this.currentUserID = currentPublisherID;
    }

    public void setCurrentPublisherID(String currentPublisherID) {
        this.currentPublisherID = currentPublisherID;
    }

    public void setCurrentPublisher(Publisher currentPublisher) {
        this.currentPublisher = currentPublisher;
    }
}
