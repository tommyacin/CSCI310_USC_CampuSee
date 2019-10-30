package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getting to notification page
        Intent homePage = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);

        Button studentButton = (Button) findViewById(R.id.publishersToolbarButton);
        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent publisherPageIntent = new Intent(getApplicationContext(), StudentHome.class);
                NotificationPage.this.startActivity(publisherPageIntent);
            }
        });

        Button mapButton = (Button) findViewById(R.id.mapToolbarButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent mapPageIntent = new Intent(getApplicationContext(), activity_map.class);
                NotificationPage.this.startActivity(mapPageIntent);
            }
        });
    }
}





