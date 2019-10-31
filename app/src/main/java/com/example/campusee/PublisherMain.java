package com.example.campusee;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PublisherMain extends AppCompatActivity {

    private DatabaseReference mPublisherEventReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_main);

        // Initialize Database - grabbing just the publisher-events
        mPublisherEventReference = FirebaseDatabase.getInstance().getReference()
                .child("publisher-events");

    }

    @Override
    public void onStart(){
        super.onStart();

        // Call grabAllPublisherEvents(publisherId);
    }



    public void grabAllPublisherEvents(String publisherId) {
        Query publisherEvents = mPublisherEventReference.child(publisherId);

        publisherEvents.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (DataSnapshot eventChild: dataSnapshot.getChildren()) {
                    Event event = eventChild.getValue(Event.class);

                    // TODO: Update UI
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
                Log.e("LoadEvents error", "events:onCancelled:" + databaseError.getMessage());
            }
        });
    }
}
