package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button studentButton = (Button) findViewById(R.id.student_button);
        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent studentIntent = new Intent(getApplicationContext(), SecondActivity.class);
                MainActivity.this.startActivity(studentIntent);
            }
        });

        Button publisherButton = (Button) findViewById(R.id.publisher_button);
        publisherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent publisherIntent = new Intent(getApplicationContext(), PublisherSignup.class);
                MainActivity.this.startActivity(publisherIntent);
            }
        });
    }
}
