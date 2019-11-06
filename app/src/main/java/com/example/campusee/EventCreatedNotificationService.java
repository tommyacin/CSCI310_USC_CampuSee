package com.example.campusee;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.Random;


import java.util.ArrayList;
import java.util.List;

public class EventCreatedNotificationService extends IntentService {
    private DatabaseReference mDatabase;

    private static final String TAG = "EVENT CREATED SERVICE";

    public EventCreatedNotificationService() {
        super("BackgroundNotifications");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Global application = (Global)getApplicationContext();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final String userKey = application.getCurrentUserID();

        Query geos = mDatabase.child("notifications");

        geos.addChildEventListener(new ChildEventListener() {

            // This will get called as many times as there are publishers in the database
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final Notification temp = dataSnapshot.getValue(Notification.class);
                String notifId = temp.getNotifId();

                Query notificationQuery = mDatabase.child("user-notifications").child(userKey).orderByChild("notifId").equalTo(notifId);;

                notificationQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            Random rand = new Random();
                            mDatabase.child("user-notifications").child(userKey).child(temp.getNotifId()).setValue(temp);

                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "1234")
                                    .setSmallIcon(R.drawable.app_icon)
                                    .setContentTitle(temp.getTitle())
                                    .setContentText(temp.getDescription())
                                    .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                                    .setPriority(NotificationCompat.PRIORITY_MAX);

                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                            notificationManager.notify(rand.nextInt(1000), builder.build());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
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


        Log.d(TAG, "we handled an intent");

        // Retrieve the Geofencing intent
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        String email = intent.getStringExtra("email");

    }

}
