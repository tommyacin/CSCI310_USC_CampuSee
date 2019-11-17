package com.example.campusee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class EventDetails extends AppCompatActivity {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        String notif_name = intent.getStringExtra("NOTIF_NAME");
        TextView event_name = (TextView)findViewById(R.id.event_name);
        event_name.setText(notif_name);
        String notif_des = intent.getStringExtra("NOTIF_DES");
        TextView event_des = (TextView)findViewById(R.id.event_description);
        event_des.setText(notif_des);
        String notif_time = intent.getStringExtra("NOTIF_TIME");
        TextView event_time_tv = (TextView)findViewById(R.id.event_time);
        event_time_tv.setText(notif_time);
    }
}
