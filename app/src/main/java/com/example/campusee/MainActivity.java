package com.example.campusee;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting the database
        mDatabase = FirebaseDatabase.getInstance().getReference();

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

    // Write user to database
    private void writeNewPerson(String email, String password) {
        User user = new User(email, password);

        String key = mDatabase.child("users").push().getKey();
        mDatabase.child("users").child(key).setValue(user);
    }

    private void writeNewPublisher(String email, String password, String building) {
        Publisher publisher = new Publisher(email, password, building);

        String key = mDatabase.child("publishers").push().getKey();
        mDatabase.child("publishers").child(key).setValue(publisher);
    }
}
