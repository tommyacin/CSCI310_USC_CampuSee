package com.example.campusee;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

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

    private void seeEventDetails(String eventId) {
        DatabaseReference eventDatabaseRef = mDatabase.child("events").child(eventId);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Event event = dataSnapshot.getValue(Event.class);

                // TODO: Update UI
                /* E.g. from firebase:
                    mAuthorView.setText(post.author);
                    mTitleView.setText(post.title);
                    mBodyView.setText(post.body);
                */
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.e("LoadEvent error", "event:onCancelled:" + databaseError.getMessage());
            }
        };
        eventDatabaseRef.addValueEventListener(eventListener);
    }
}
