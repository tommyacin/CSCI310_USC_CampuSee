package com.example.campusee;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.location.Geofence;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Global extends Application {

    private DatabaseReference mDatabase;

    private HashMap<String, Constants.Building> allBuildings;

    private String currentUserID;
    private String currentPublisherID;

    private List<GeofenceHolder> allEventsForUser;
    private List<Geofence> geofenceForNotifications;
    private List<String> existingPublishers;
    private List<String> subscribedPublisherKeys;
    private List<Notification> allUserNotifications;

    private List<GeofenceHolder> userEvents;

    public void initializeBuildings() {
        Constants constants = new Constants();
        allBuildings = constants.allBuildings;
    }

    public HashMap<String, Constants.Building> getAllBuildings() {
        return allBuildings;
    }

    public String getCurrentUserID() {return currentUserID;}

    public String getCurrentPublisherID() {return currentPublisherID;}


    public List<GeofenceHolder> getAllEventsForUser() {return allEventsForUser;}

    public List<String> getExistingPublishers() {return existingPublishers;}

    public List<Geofence> getGeofenceForNotifications() {return geofenceForNotifications;}

    public void setCurrentUserID(String currentUserID) {
        this.currentUserID = currentUserID;
    }

    public void setCurrentPublisherID(String currentPublisherID) {
        this.currentPublisherID = currentPublisherID;
    }

    public void grabAllPublishers() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Query geos = mDatabase.child("publishers");

        existingPublishers = new ArrayList<String>();

        geos.addChildEventListener(new ChildEventListener() {

            // This will get called as many times as there are publishers in the database
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // grab a single publisher
                // GeofenceHolder geofence = dataSnapshot.getValue(GeofenceHolder.class);
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();


                String building = (String)map.get("building");

                if(Constants.allBuildings.containsKey(building))
                {
                    existingPublishers.add(building);
                }

                //Log.d("grabgeofences", "geofence: " + duration + eventId + latitude + longitude + radius);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("LoadPublishers error", "publishers:onCancelled:" + databaseError.getMessage());
            }
        });
    }

    public void grabAllGeofenceHolders() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Query geos = mDatabase.child("geofences");

        allEventsForUser = new ArrayList<GeofenceHolder>();

        geos.addChildEventListener(new ChildEventListener() {

            // This will get called as many times as there are publishers in the database
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // grab a single publisher
                // GeofenceHolder geofence = dataSnapshot.getValue(GeofenceHolder.class);
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                long duration = (long)map.get("duration");
                String eventId = (String)map.get("event");
                double latitude = (double)map.get("latitude");
                double longitude = (double)map.get("longitude");
                long radius = (long)map.get("radius");

                GeofenceHolder geofence = new GeofenceHolder(eventId, latitude, longitude, radius, duration);

                allEventsForUser.add(geofence);

                //Log.d("grabgeofences", "geofence: " + duration + eventId + latitude + longitude + radius);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("LoadPublishers error", "publishers:onCancelled:" + databaseError.getMessage());
            }
        });
    }

    public void grabAllGeofences() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Query geos = mDatabase.child("geofences");

        geofenceForNotifications = new ArrayList<Geofence>();

        geos.addChildEventListener(new ChildEventListener() {

            // This will get called as many times as there are publishers in the database
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // grab a single publisher
                // GeofenceHolder geofence = dataSnapshot.getValue(GeofenceHolder.class);
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                long duration = (long)map.get("duration");
                String eventId = (String)map.get("event");
                double latitude = (double)map.get("latitude");
                double longitude = (double)map.get("longitude");
                long radius = (long)map.get("radius");

                Geofence geofence = new Geofence.Builder()
                        .setRequestId(eventId)
                        .setCircularRegion(latitude, longitude, radius)
                        .setExpirationDuration(duration)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                                Geofence.GEOFENCE_TRANSITION_EXIT |
                                Geofence.GEOFENCE_TRANSITION_DWELL)
                        .setLoiteringDelay(1)
                        .build();

                geofenceForNotifications.add(geofence);

                Log.d("grabgeofences", "geofence: " + duration + eventId + latitude + longitude + radius);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("LoadPublishers error", "publishers:onCancelled:" + databaseError.getMessage());
            }
        });
    }

    public void grabAllSubscribedPublisherKeys() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Query geos = mDatabase.child("user-publishers").child(currentUserID);
        subscribedPublisherKeys = new ArrayList<String>();

        geos.addChildEventListener(new ChildEventListener() {

            // This will get called as many times as there are publishers in the database
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                subscribedPublisherKeys.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("LoadPublishers error", "publishers:onCancelled:" + databaseError.getMessage());
            }
        });
    }

    public void grabAllUserNotifications() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Query geos = mDatabase.child("user-notifications").child(currentUserID);
        allUserNotifications = new ArrayList<Notification>();

        geos.addChildEventListener(new ChildEventListener() {

            // This will get called as many times as there are publishers in the database
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                allUserNotifications.add(dataSnapshot.getValue(Notification.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("LoadPublishers error", "publishers:onCancelled:" + databaseError.getMessage());
            }
        });
    }


}
