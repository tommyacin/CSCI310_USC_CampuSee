package com.example.campusee;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class EditEvent extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void removeEvent(String eventId, String publisherId) {
        DatabaseReference eventDatabaseRef = mDatabase.child("events").child(eventId);
        DatabaseReference publisherEventDatabaseRef = mDatabase.child("publisher-events").child(publisherId).child(eventId);

        eventDatabaseRef.removeValue();
        publisherEventDatabaseRef.removeValue();
    }

    private void updateEventStatue(String eventId, String publisherId, boolean status) {
        DatabaseReference eventDatabaseRef= mDatabase.child("events").child(eventId);
        DatabaseReference publisherEventDatabaseRef = mDatabase.child("publisher-events").child(publisherId).child(eventId);

        if (status) {
            eventDatabaseRef.child("status").setValue(true);
            publisherEventDatabaseRef.child("status").setValue(true);
        } else {
            eventDatabaseRef.child("status").setValue(false);
            publisherEventDatabaseRef.child("status").setValue(false);
        }

    }
}
