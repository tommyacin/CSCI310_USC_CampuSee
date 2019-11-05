package com.example.campusee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class publisher_page_of_events extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_page_of_events);

        Intent intent = getIntent();
        String publisher_name = intent.getStringExtra("PUBLISHER_NAME");
    }
}
