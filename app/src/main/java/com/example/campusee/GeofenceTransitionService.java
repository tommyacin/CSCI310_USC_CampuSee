package com.example.campusee;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GeofenceTransitionService extends IntentService {

    private static final String TAG = "googly";
    public static final int GEOFENCE_NOTIFICATION_ID = 0;

    private DatabaseReference mDatabase;

    public GeofenceTransitionService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "we handled an intent");
        // Retrieve the Geofencing intent
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        // Handling errors
        if ( geofencingEvent.hasError() ) {
            String errorMsg = getErrorString(geofencingEvent.getErrorCode() );
            Log.e( TAG, errorMsg );
            return;
        }

        // Retrieve GeofenceTrasition
        int geoFenceTransition = geofencingEvent.getGeofenceTransition();
        // Check if the transition type
        if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT ) {
            // Get the geofence that were triggered
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            // Create a detail message with Geofences received
            String geofenceTransitionDetails = getGeofenceTrasitionDetails(geoFenceTransition, triggeringGeofences );
            // Send notification details as a String
            sendNotification( geofenceTransitionDetails, triggeringGeofences);
        }
    }

    // Create a detail message with Geofences received
    private String getGeofenceTrasitionDetails(int geoFenceTransition, List<Geofence> triggeringGeofences) {
        // get the ID of each geofence triggered
        ArrayList<String> triggeringGeofencesList = new ArrayList<>();
        for ( Geofence geofence : triggeringGeofences ) {
            triggeringGeofencesList.add( geofence.getRequestId() );
        }

        String status = null;
        if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER )
            status = "Entering ";
        else if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT )
            status = "Exiting ";
        return status + TextUtils.join( ", ", triggeringGeofencesList);
    }

    // Send a notification
    private void sendNotification( String msg,  List<Geofence> triggeringGeofences) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Log.i(TAG, "sendNotification: " + msg);
        Global application = (Global) getApplicationContext();

        for (int i = 0; i < triggeringGeofences.size(); i++) {
            Geofence triggered = triggeringGeofences.get(i);
            final String eventID = triggered.getRequestId();

            Query geos = mDatabase.child("events");

            //Query notificationQuery = mDatabase.child("events").child();

            geos.addChildEventListener(new ChildEventListener() {

                // This will get called as many times as there are publishers in the database
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    // grab a single publisher
                    // GeofenceHolder geofence = dataSnapshot.getValue(GeofenceHolder.class);
                    Log.d("hello", eventID);
                    Log.d("hello", "data key" + dataSnapshot.getKey());
                    if(dataSnapshot.getKey().equals(eventID)) {

                        Log.d("making", "event now");

                        Event temp = dataSnapshot.getValue(Event.class);

                        String key = mDatabase.child("notifications").push().getKey();
                        Notification notification = new Notification(temp.title, temp.description, temp.time, temp.publisherId, key);

                        mDatabase.child("notifications").child(key).setValue(notification);

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "1234")
                                .setSmallIcon(R.drawable.app_icon)
                                .setContentTitle(temp.title)
                                .setContentText(temp.description)
                                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                                .setPriority(NotificationCompat.PRIORITY_MAX);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                        notificationManager.notify(1, builder.build());
                    }
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

//            notificationQuery.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Log.d("hello", eventID);
//                    Log.d("hello", "data key" + dataSnapshot.getKey());
//                    if (dataSnapshot.getKey().equals(eventID)) {
//                        Log.d("hello", "hi i found the event");
//
//                        Event temp = dataSnapshot.getValue(Event.class);
//
//                        String key = mDatabase.child("notifications").push().getKey();
//                        Notification notification = new Notification(temp.title, temp.description, temp.time, temp.publisherId, key);
//
//                        mDatabase.child("notifications").child(key).setValue(notification);
//
//                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "1234")
//                                .setSmallIcon(R.drawable.app_icon)
//                                .setContentTitle(temp.title)
//                                .setContentText(temp.description)
//                                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
//                                .setPriority(NotificationCompat.PRIORITY_MAX);
//
//                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
//                        notificationManager.notify(1, builder.build());
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                }
//            });

//            geos.addChildEventListener(new ChildEventListener() {
//
//                // This will get called as many times as there are publishers in the database
//                @Override
//                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                    // grab a single publisher
//                    // GeofenceHolder geofence = dataSnapshot.getValue(GeofenceHolder.class);
//                    if(dataSnapshot.getKey() != eventID)
//                    {
//                        return;
//                    }
//                    Event temp = dataSnapshot.getValue(Event.class);
//
//                    String key = mDatabase.child("notifications").push().getKey();
//                    Notification notification = new Notification(temp.title, temp.description, temp.time, temp.publisherId, key);
//
//                    mDatabase.child("notifications").child(key).setValue(notification);
//
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "1234")
//                            .setSmallIcon(R.drawable.app_icon)
//                            .setContentTitle(temp.title)
//                            .setContentText(temp.description)
//                            .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
//                            .setPriority(NotificationCompat.PRIORITY_MAX);
//
//                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
//                    notificationManager.notify(1, builder.build());
//                }
//
//                @Override
//                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
//
//                @Override
//                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
//
//                @Override
//                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Log.e("LoadPublishers error", "publishers:onCancelled:" + databaseError.getMessage());
//                }
//            });
//        }
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        Notification temp = new Notification(title, description, time, publisherId, key);
//
//        mDatabase.child("user-notifications").child(application.getCurrentUserID()).child(temp.getNotifId()).setValue(temp);

        }

    // Handle errors
    private static String getErrorString(int errorCode) {
        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return "GeoFence not available";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return "Too many GeoFences";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return "Too many pending intents";
            default:
                return "Unknown error.";
        }
    }
}
