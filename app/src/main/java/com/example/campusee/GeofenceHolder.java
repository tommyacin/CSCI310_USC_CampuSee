package com.example.campusee;

public class GeofenceHolder {
    public String event;
    public double latitude;
    public double longitude;
    public long radius;
    public long duration;

    GeofenceHolder(String event, double latitude, double longitude, long radius, long duration)
    {
        this.event = event;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.duration = duration;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public String getEvent()
    {
        return event;
    }
    GeofenceHolder(){}
}
