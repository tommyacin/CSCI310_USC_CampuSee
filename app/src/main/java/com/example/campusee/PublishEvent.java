package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

public class PublishEvent extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private String publisherID;
    private String eventID;
    private HashMap<String, Constants.Building> buildings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_event);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        publisherID = ((Global) this.getApplication()).getCurrentPublisherID();
        buildings = ((Global) this.getApplication()).getAllBuildings();


        TextView name_tv = findViewById(R.id.publish_event_name);
        TextView date_tv = findViewById(R.id.publish_date);
        TextView time_tv = findViewById(R.id.publish_time);
        TextView radius_tv = findViewById(R.id.publish_radius);
        TextView description_tv = findViewById(R.id.publish_description);

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

        final String date = date_tv.getText().toString();
        final String time = time_tv.getText().toString();
        final String name = name_tv.getText().toString();
        final String description = description_tv.getText().toString();
        final String radius = radius_tv.getText().toString();
        final String iconName = getIntent().getStringExtra("EVENT_ICON");
        setImageView(iconName);

        //writeNewEvent(publisherID, name, description, time, date, Integer.parseInt(radius), iconName);

        Button nextButton = (Button) findViewById(R.id.publish_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewEvent(publisherID, name, description, time, date, Integer.parseInt(radius), iconName);
                addNotificationToDatabase(name, description, time, publisherID);
                PublishEvent.this.finish();
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

    }

    private void setImageView(String image_name){
        ImageView icon_image = findViewById(R.id.icon_image);
        if(image_name.equals("cs_icon")){
            icon_image.setImageResource(R.drawable.cs_icon);
        } else if(image_name.equals("team_icon")){
            icon_image.setImageResource(R.drawable.team_icon);
        } else if(image_name.equals("science_icon")){
            icon_image.setImageResource(R.drawable.science_icon);
        } else if(image_name.equals("presentation_icon")){
            icon_image.setImageResource(R.drawable.presentation_icon);
        } else if(image_name.equals("book_icon")){
            icon_image.setImageResource(R.drawable.book_icon);
        } else if(image_name.equals("news_icon")){
            icon_image.setImageResource(R.drawable.news_icon);
        } else if(image_name.equals("job_icon")){
            icon_image.setImageResource(R.drawable.job_icon);
        } else if(image_name.equals("suitcase_icon")){
            icon_image.setImageResource(R.drawable.suitcase_icon);
        } else{
            icon_image.setImageResource(R.drawable.food_icon);
        }
    }

    private void writeNewEvent(final String publisherId,
                               final String title,
                               final String description,
                               final String time,
                               final String date,
                               final int radius,
                               final String iconFileName) {
        // Grab publisher's events
        Query pubQuery = mDatabase.child("publishers").orderByKey().equalTo(publisherId);
        pubQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Publisher curr_pub = ds.getValue(Publisher.class);
                        String building = curr_pub.building;

                        String eventKey = mDatabase.child("events").push().getKey();

                        Event newEvent = new Event(publisherId, building, title, description, time, date, radius, iconFileName);

                        //creating geofence here too
                        String geoKey = mDatabase.child("geofences").push().getKey();

                        double latitude = Constants.allBuildings.get(building).latLoc;
                        double longitude = Constants.allBuildings.get(building).longLoc;

                        GeofenceHolder newGeofence = new GeofenceHolder(eventKey, latitude, longitude, radius, 1000000);

                        mDatabase.child("geofences").child(geoKey).setValue(newGeofence);

                        //ending geofence creation


                        Map<String, Object> eventValues = newEvent.toMap();

                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/events/" + eventKey, eventValues);
                        childUpdates.put("/publisher-events/" + publisherId + "/" + eventKey, eventValues);

                        mDatabase.updateChildren(childUpdates);

                        /*
                        Intent publishIntent = new Intent(getApplicationContext(), PublisherMain.class);
                        PublishEvent.this.startActivity(publishIntent);
                         */
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void addNotificationToDatabase(String title, String description, String time, String publisherId) {

        String key = mDatabase.child("notifications").push().getKey();
        Notification notification = new Notification(title, description, time, publisherId, key);

        mDatabase.child("notifications").child(key).setValue(notification);

    }

}
