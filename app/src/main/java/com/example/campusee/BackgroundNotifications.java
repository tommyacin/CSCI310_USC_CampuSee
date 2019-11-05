package com.example.campusee;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;


import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BackgroundNotifications extends IntentService {

    private static final String ACTION_FOO = "com.example.campusee.action.FOO";
    private static final String ACTION_BAZ = "com.example.campusee.action.BAZ";

    private static final String EXTRA_PARAM1 = "com.example.campusee.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.campusee.extra.PARAM2";

    private List<Geofence> geofences;
    private DatabaseReference mDatabase;
    private GeofencingClient geofencingClient;
    private PendingIntent geofencePendingIntent = null;



    public BackgroundNotifications() {
        super("BackgroundNotifications");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Global application = (Global)getApplicationContext();

        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        geofences = application.getGeofenceForNotifications();

        Log.d("size", "" + geofences.size());


        grabAllGeofences();
        Geofence geofence = new Geofence.Builder()
                .setRequestId("Ayo")
                .setCircularRegion(37.422001, -122.084061, 500)
                .setExpirationDuration(1000000)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT |
                        Geofence.GEOFENCE_TRANSITION_DWELL)
                .setLoiteringDelay(1)
                .build();

        geofences.add(geofence);

        geofencingClient = LocationServices.getGeofencingClient(this);
        geofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent());

        return super.onStartCommand(intent, flags, startId);
    }

    public void grabAllGeofences() {
        Query geos = mDatabase.child("geofences");

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

                geofences.add(geofence);

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

    // TODO: Customize helper method
    public static void startActionGeotrack(Context context, String param1, String param2) {
        Intent intent = new Intent(context, BackgroundNotifications.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofences);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }

        Log.i("hello", "hi i do get here");
        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);

        geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }



    class GeofenceBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("hello2", "i have been received");

            GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
            if (geofencingEvent.hasError()) {
//            String errorMessage = GeofenceErrorMessages.getErrorString(this,
//                    geofencingEvent.getErrorCode());
                return;
            }

            // Get the transition type.
            int geofenceTransition = geofencingEvent.getGeofenceTransition();

            // Test that the reported transition was of interest.
            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {

                List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

                // Get the transition details as a String.

                for(int i=0; i<triggeringGeofences.size(); i++)
                {
                    sendNotification(triggeringGeofences.get(i));
                }

                // Send notification and log the transition details.

                //Log.i(TAG, geofenceTransitionDetails);
            } else {
                // Log the error.
//                Log.e(TAG, getString(R.string.geofence_transition_invalid_type,
//                        geofenceTransition));
            }
        }

        public void sendNotification(Geofence geofence) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "1234")
                    .setSmallIcon(R.drawable.app_icon)
                    .setContentTitle(geofence.getRequestId())
                    .setContentText("temp description")
                    .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                    .setPriority(NotificationCompat.PRIORITY_MAX);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
            notificationManager.notify(1, builder.build());

        }
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
