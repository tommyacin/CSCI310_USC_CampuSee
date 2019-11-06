package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CreateEvent extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.activity_create_event);

        Button nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent iconIntent = new Intent(getApplicationContext(), IconPage.class);
                CreateEvent.this.startActivity(iconIntent);
            }
        });
        TimePicker picker=(TimePicker)findViewById(R.id.timePicker1);
        picker.setIs24HourView(false);
    }

    private void writeNewEvent(String publisherId, String title, String description, String time, int ID, double[] loc, int radius) {
        String eventKey = mDatabase.child("events").push().getKey();

        Event newEvent = new Event(publisherId, title, description, time, ID, loc, radius);

        Map<String, Object> eventValues = newEvent.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/events/" + eventKey, eventValues);
        childUpdates.put("/publisher-events/" + publisherId + "/" + eventKey, eventValues);

        mDatabase.updateChildren(childUpdates);
    }



    private void writeNewGeofence(double longitude, double latitude, long radius, String eventId, long duration){
        String geoKey = mDatabase.child("geofences").push().getKey();

        GeofenceHolder geofence = new GeofenceHolder(eventId, latitude, longitude, radius, duration);

        mDatabase.child("geofences").child(geoKey).setValue(geofence);
    }

    private void addNotificationToDatabase(String title, String description, String time, String publisherId) {

        String key = mDatabase.child("publishers").push().getKey();
        Notification notification = new Notification(title, description, time, publisherId, key);
        Log.d("writeNotif", key);
        mDatabase.child("publishers").child(key).setValue(notification);

    }

}
