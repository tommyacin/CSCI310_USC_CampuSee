package com.example.campusee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_map extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent notificationPage = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Button notificationButton = (Button) findViewById(R.id.notificationToolbarButton);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent notificationPageIntent = new Intent(getApplicationContext(), NotificationPage.class);
                activity_map.this.startActivity(notificationPageIntent);
            }
        });

        Button studentHomeButton = (Button) findViewById(R.id.publishersToolbarButton);
        studentHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent publisherPageIntent = new Intent(getApplicationContext(), StudentHome.class);
                activity_map.this.startActivity(publisherPageIntent);
            }
        });
    }
}
