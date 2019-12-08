package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UpdateEventFields extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private String publisherId;
    private String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        publisherId = ((Global) this.getApplication()).getCurrentPublisherID();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        String event_name = intent.getStringExtra("EVENT_NAME");
        TextView event_name_tv = (TextView)findViewById(R.id.edit_event_name);
        event_name_tv.setText(event_name);
        String event_des = intent.getStringExtra("EVENT_DES");
        TextView event_des_tv = (TextView)findViewById(R.id.edit_event_description);
        event_des_tv.setText(event_des);
        String event_time = intent.getStringExtra("EVENT_TIME");
        TextView event_time_tv = (TextView)findViewById(R.id.edit_event_time);
        event_time_tv.setText(event_time);
        String event_date = intent.getStringExtra("EVENT_DATE");
        TextView event_date_tv = (TextView)findViewById(R.id.edit_event_date);
        eventId = intent.getStringExtra("EVENT_ID");
        int event_radius = intent.getIntExtra("EVENT_RADIUS", 0);

        // [TO DO]: NEED TO GRAB EVENT BUILDING, RADIUS, ICON
        String event_building = "building";
        event_radius = 12;
        String event_icon = "CS_ICON";

        updateEventFields(
                eventId,
                publisherId,
                event_building,
                event_name,
                event_des,
                event_time,
                event_date,
                event_radius,
                event_icon);

    }

    protected void updateEventFields(final String eventId, final String publisherId,
                                 final String building,
                                 final String title,
                                 final String description,
                                 final String time,
                                 final String date,
                                 final int radius,
                                 final String iconFileName) {

        updateEventBuilding(eventId, publisherId, building);
        updateEventDate(eventId, publisherId, date);
        updateEventDescription(eventId, publisherId, description);
        updateEventIcon(eventId, publisherId, iconFileName);
        updateEventTime(eventId, publisherId, time);
        updateEventTitle(eventId, publisherId, title);
        updateEventRadius(eventId, publisherId, radius);

    }

    // UPDATE HELPER FUNCTIONS
    private void updateEventStatus(final String eventId, final String publisherId, final String building) {
        mDatabase.child("events").child(eventId).child("status").setValue(false);
        mDatabase.child("publisher-events").child(publisherId).child(eventId).child("status").setValue(false);
    }

    private void updateEventTitle(final String eventId, final String publisherId, final String title) {
        mDatabase.child("events").child(eventId).child("title").setValue(title);
        mDatabase.child("publisher-events").child(publisherId).child(eventId).child("title").setValue(title);
    }

    private void updateEventBuilding(final String eventId, final String publisherId, final String building) {
        mDatabase.child("events").child(eventId).child("building").setValue(building);
        mDatabase.child("publisher-events").child(publisherId).child(eventId).child("building").setValue(building);
    }

    private void updateEventTime(final String eventId, final String publisherId, final String time) {
        mDatabase.child("events").child(eventId).child("time").setValue(time);
        mDatabase.child("publisher-events").child(publisherId).child(eventId).child("time").setValue(time);
    }

    private void updateEventDate(final String eventId, final String publisherId, final String date) {
        mDatabase.child("events").child(eventId).child("date").setValue(date);
        mDatabase.child("publisher-events").child(publisherId).child(eventId).child("date").setValue(date);
    }

    private void updateEventIcon(final String eventId, final String publisherId, final String iconFileName) {
        mDatabase.child("events").child(eventId).child("iconFileName").setValue(iconFileName);
        mDatabase.child("publisher-events").child(publisherId).child(eventId).child("iconFileName").setValue(iconFileName);
    }

    private void updateEventDescription(final String eventId, final String publisherId, final String description) {
        mDatabase.child("events").child(eventId).child("description").setValue(description);
        mDatabase.child("publisher-events").child(publisherId).child(eventId).child("description").setValue(description);
    }

    private void updateEventRadius(final String eventId, final String publisherId, final int radius) {
        mDatabase.child("events").child(eventId).child("radius").setValue(radius);
        mDatabase.child("publisher-events").child(publisherId).child(eventId).child("radius").setValue(radius);
    }
}
