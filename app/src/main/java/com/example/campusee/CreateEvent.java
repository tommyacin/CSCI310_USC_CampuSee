package com.example.campusee;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
                buttonClick();
            }
        });
        TimePicker picker=(TimePicker)findViewById(R.id.create_timepicker);
        picker.setIs24HourView(false);

        String pubID = ((Global) this.getApplication()).getCurrentPublisherID();
        writeNewEvent(pubID, "test", "desc", "1:00 PM", 123, null, 10);
    }

    private void buttonClick(){
        EditText event_name_input = (EditText) findViewById(R.id.create_event_name);
        EditText event_radius_input = (EditText) findViewById(R.id.create_radius);
        EditText event_description_input = (EditText) findViewById(R.id.create_description);
        TimePicker time_picker = findViewById(R.id.create_timepicker);
        DatePicker date_picker = findViewById(R.id.create_datepicker);
        Boolean continue_event = true;

        String event_name = event_name_input.getText().toString();
        String geo_radius = event_radius_input.getText().toString();
        String event_description = event_description_input.getText().toString();
        int hour = time_picker.getHour();
        int minute = time_picker.getMinute();
        int month = date_picker.getMonth();
        int day = date_picker.getDayOfMonth();
        int year = date_picker.getYear();


        if(event_name == null || event_name.isEmpty()){
            event_name_input.setHint("Please input event name");
            event_name_input.setHintTextColor(Color.parseColor("#990000"));
            continue_event = false;
        }
        if(geo_radius == null || event_name.isEmpty()){
            event_radius_input.setHint("Please input a radius");
            event_radius_input.setHintTextColor(Color.parseColor("#990000"));
            continue_event = false;
        }
        if(event_description == null || event_description.isEmpty()){
            event_description_input.setHint("Please input a description");
            event_description_input.setHintTextColor(Color.parseColor("#990000"));
            continue_event = false;
        }
        if (continue_event){
            Intent iconIntent = new Intent(getApplicationContext(), IconPage.class);
            iconIntent.putExtra("EVENT_NAME", event_name);
            iconIntent.putExtra("EVENT_RADIUS", geo_radius);
            iconIntent.putExtra("EVENT_DESCRIPTION", event_description);
            iconIntent.putExtra("EVENT_HOUR", hour);
            iconIntent.putExtra("EVENT_MINUTE", minute);
            iconIntent.putExtra("EVENT_MONTH", month);
            iconIntent.putExtra("EVENT_DAY", day);
            iconIntent.putExtra("EVENT_YEAR", year);
            CreateEvent.this.startActivity(iconIntent);
        }
    }

    private void writeNewGeofence(double longitude, double latitude, long radius, String eventId, long duration){
        String geoKey = mDatabase.child("geofences").push().getKey();

        GeofenceHolder geofence = new GeofenceHolder(eventId, latitude, longitude, radius, duration);

        mDatabase.child("geofences").child(geoKey).setValue(geofence);
    }

}
