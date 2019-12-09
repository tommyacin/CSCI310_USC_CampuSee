package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditEvent extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private String publisherId;
    private String eventId;
    Button unpublishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        publisherId = ((Global) this.getApplication()).getCurrentPublisherID();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        final String event_name = intent.getStringExtra("EVENT_NAME");
        TextView event_name_tv = (TextView)findViewById(R.id.edit_event_name);
        event_name_tv.setText(event_name);
        final String event_des = intent.getStringExtra("EVENT_DES");
        TextView event_des_tv = (TextView)findViewById(R.id.edit_event_description);
        event_des_tv.setText(event_des);
        final String event_time = intent.getStringExtra("EVENT_TIME");
        TextView event_time_tv = (TextView)findViewById(R.id.edit_event_time);
        event_time_tv.setText(event_time);
        final String event_date = intent.getStringExtra("EVENT_DATE");
        TextView event_date_tv = (TextView)findViewById(R.id.edit_event_date);
        eventId = intent.getStringExtra("EVENT_ID");
        final String icon_name = intent.getStringExtra("EVENT_ICON");
//        Log.i("icon name", icon_name);
        setIconImageView(icon_name);
        final int event_radius = intent.getIntExtra("EVENT_RADIUS", 5);
        TextView event_radius_tv = findViewById(R.id.edit_event_radius);
        event_radius_tv.setText(String.valueOf(event_radius));

        Button deleteButton = (Button) findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeEvent(eventId, publisherId);
                EditEvent.this.finish();
            }
        });

        Button editButton = (Button) findViewById(R.id.edit_page_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCreateIntent(event_name, event_des, event_time, event_radius, eventId, icon_name, event_date);
            }
        });

        checkStatus(eventId, publisherId);
        unpublishButton = (Button) findViewById(R.id.unpublish_button);
        unpublishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unpublishButton.getText().equals("Unpublish")){
                    unpublishButton.setText("Republish");
                    updateEventStatus(eventId, publisherId, true);
                } else{
                    unpublishButton.setText("Unpublish");
                    updateEventStatus(eventId, publisherId, false);
                }
//                updateEventStatus(eventId, publisherId, published);
                EditEvent.this.finish();
            }
        });
    }

    private void setCreateIntent(String event_name, String event_des, String event_time, int event_radius,
                                 String event_id, String event_icon, String event_date){
        Intent createIntent = new Intent(this, CreateEvent.class);
        createIntent.putExtra("EVENT_NAME", event_name);
        createIntent.putExtra("EVENT_DES", event_des);
        createIntent.putExtra("EVENT_TIME", event_time);
        createIntent.putExtra("EVENT_DATE", event_date);
        createIntent.putExtra("EVENT_ICON", event_icon);
        createIntent.putExtra("EVENT_RADIUS", event_radius);
        createIntent.putExtra("EVENT_ID", event_id);
        createIntent.putExtra("IS_EDIT", "true");
        startActivity(createIntent);
    }

    private void setIconImageView(String iconName){
        iconName = iconName.toLowerCase();
        ImageView icon_image = findViewById(R.id.edit_event_icon);
        if(iconName.equals("cs_icon")){
            icon_image.setImageResource(R.drawable.cs_icon);
        } else if(iconName.equals("team_icon")){
            icon_image.setImageResource(R.drawable.team_icon);
        } else if(iconName.equals("science_icon")){
            icon_image.setImageResource(R.drawable.science_icon);
        } else if(iconName.equals("presentation_icon")){
            icon_image.setImageResource(R.drawable.presentation_icon);
        } else if(icon_image.equals("book_icon")){
            icon_image.setImageResource(R.drawable.book_icon);
        } else if(iconName.equals("news_icon")){
            icon_image.setImageResource(R.drawable.news_icon);
        } else if(iconName.equals("job_icon")){
            icon_image.setImageResource(R.drawable.job_icon);
        } else if(iconName.equals("suitcase_icon")){
            icon_image.setImageResource(R.drawable.suitcase_icon);
        } else{
            icon_image.setImageResource(R.drawable.food_icon);
        }
    }

    private void checkStatus(final String eventId, final String publisherId) {
        Query statusQuery = statusQuery = mDatabase.child("publisher-events").child(publisherId).child(eventId);

        statusQuery.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                            Event curr_event = dataSnapshot.getValue(Event.class);

                            if (curr_event.status == false) {
                                unpublishButton.setText("Unpublish");

                            } else {
                                unpublishButton.setText("Republish");
                            }

                            return;


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }


    private void removeEvent(String eventId, String publisherId) {
        DatabaseReference eventDatabaseRef = mDatabase.child("events").child(eventId);
        DatabaseReference publisherEventDatabaseRef = mDatabase.child("publisher-events").child(publisherId).child(eventId);

        eventDatabaseRef.removeValue();
        publisherEventDatabaseRef.removeValue();
    }

    private void updateEventStatus(final String eventId, final String publisherId, final boolean status) {
        mDatabase.child("events").child(eventId).child("status").setValue(status);
        mDatabase.child("publisher-events").child(publisherId).child(eventId).child("status").setValue(status);
    }
}
