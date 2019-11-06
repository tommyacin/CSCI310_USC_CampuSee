package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PublishEvent extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_event);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button nextButton = (Button) findViewById(R.id.publish_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent publishIntent = new Intent(getApplicationContext(), PublisherMain.class);
                PublishEvent.this.startActivity(publishIntent);
            }
        });

        Button editButton = (Button) findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent intent = new Intent(getApplicationContext(), CreateEvent.class);
                PublishEvent.this.startActivity(intent);
            }
        });

        TextView name_tv = findViewById(R.id.publish_event_name);
        TextView date_tv = findViewById(R.id.publish_date);
        TextView time_tv = findViewById(R.id.publish_time);
        TextView radius_tv = findViewById(R.id.publish_radius);
        TextView description_tv = findViewById(R.id.publish_description);
        ImageView icon_image = findViewById(R.id.icon_image);
        name_tv.setText(getIntent().getStringExtra("EVENT_NAME"));
        radius_tv.setText(getIntent().getStringExtra("EVENT_RADIUS"));
        description_tv.setText(getIntent().getStringExtra("EVENT_DESCRIPTION"));
        int hour = getIntent().getIntExtra("EVENT_HOUR", 0);
        int minute = getIntent().getIntExtra("EVENT_MINUTE", 0);
        int month = getIntent().getIntExtra("EVENT_MONTH", 0);
        int day = getIntent().getIntExtra("EVENT_DAY", 0);
        int year = getIntent().getIntExtra("EVENT_YEAR", 0);
        String time_string = String.valueOf(hour) + ":" + String.valueOf(minute);
        String date_string = String.valueOf(month) + "/" + String.valueOf(day) + "/" + String.valueOf(year);
        date_tv.setText(date_string);
        time_tv.setText(time_string);

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
}
