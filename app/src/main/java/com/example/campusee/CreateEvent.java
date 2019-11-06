package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    private void writeNewGeofence(double longitude, double latitude, long radius, String eventId, long duration){
        String geoKey = mDatabase.child("geofences").push().getKey();

        GeofenceHolder geofence = new GeofenceHolder(eventId, latitude, longitude, radius, duration);

        mDatabase.child("geofences").child(geoKey).setValue(geofence);
    }

}
