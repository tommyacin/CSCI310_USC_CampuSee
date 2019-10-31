package com.example.campusee;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PublisherSignup extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_signup);

        // Getting the database
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void writeNewPublisher(String email, String password, String building) {
        Publisher publisher = new Publisher(email, password, building);

        String key = mDatabase.child("publishers").push().getKey();
        mDatabase.child("publishers").child(key).setValue(publisher);
    }
}
