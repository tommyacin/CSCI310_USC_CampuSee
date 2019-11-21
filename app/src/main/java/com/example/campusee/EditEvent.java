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

public class EditEvent extends AppCompatActivity {

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

        Button deleteButton = (Button) findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeEvent(eventId, publisherId);
                EditEvent.this.finish();
            }
        });

        Button republishButton = (Button) findViewById(R.id.republish_button);
        republishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEventStatus(eventId, publisherId, true);
                EditEvent.this.finish();
            }
        });

        Button unpublishButton = (Button) findViewById(R.id.unpublish_button);
        unpublishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEventStatus(eventId, publisherId, false);
                EditEvent.this.finish();
            }
        });
    }

    private void setImageView(String image_name){
        ImageView icon_image = findViewById(R.id.edit_event_icon);
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

    protected void removeEvent(String eventId, String publisherId) {
        DatabaseReference eventDatabaseRef = mDatabase.child("events").child(eventId);
        DatabaseReference publisherEventDatabaseRef = mDatabase.child("publisher-events").child(publisherId).child(eventId);

        eventDatabaseRef.removeValue();
        publisherEventDatabaseRef.removeValue();
    }

    protected void updateEventStatus(final String eventId, final String publisherId, final boolean status) {
        mDatabase.child("events").child(eventId).child("status").setValue(status);
        mDatabase.child("publisher-events").child(publisherId).child(eventId).child("status").setValue(status);
    }
}
