package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PublisherSignup extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private String mPublisherID;
    private boolean mPublisherExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_signup);

        // Getting the database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mPublisherID = null;
        mPublisherExists = false;

        //sign up
        Button publisherSignUpButton = (Button) findViewById(R.id.publisher_signup_button);
        publisherSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                String publisherID = buttonClick();

                Intent publisherIntent = new Intent(getApplicationContext(), PublisherMain.class);
                publisherIntent.putExtra("currentPublisherID", publisherID);
                PublisherSignup.this.startActivity(publisherIntent);
            }
        });

        //login
        Button publisherLoginButton = (Button) findViewById(R.id.publisher_login_button);
        publisherLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                String publisherID = buttonClick();

                Intent publisherIntent = new Intent(getApplicationContext(), PublisherMain.class);
                publisherIntent.putExtra("currentPublisherID", publisherID);
                PublisherSignup.this.startActivity(publisherIntent);
            }
        });
    }

    private String buttonClick() {
        EditText name_input = (EditText) findViewById(R.id.publisher_signup_name);
        EditText email_input = (EditText) findViewById(R.id.publisher_signup_email);
        EditText password_input = (EditText) findViewById(R.id.publisher_signup_password);
        EditText building_input = (EditText) findViewById(R.id.publisher_signup_building);

        String name = name_input.getText().toString();
        String password = password_input.getText().toString();
        String email = email_input.getText().toString();
        String building = building_input.getText().toString();

        return publisherSignupOrLogin(email, password, building);
    }

    private String publisherSignupOrLogin(String email, String password, String building) {
        //check user exists
        DatabaseReference publishersRef = mDatabase.child("publishers");
        Query emailQuery = publishersRef.orderByChild("email").equalTo(email);
        emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot  publisher: dataSnapshot.getChildren() ){
                    // Iterate through all the publishers with the same email
                    publisher.getKey();
                    mPublisherID = publisher.getKey();
                    mPublisherExists = true;
                    Log.d("isExistingpublisher", mPublisherID);
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //if exists, return that publisherID
        if (mPublisherExists) {
            Log.d("isExisting", "here: " + mPublisherID);
            return mPublisherID;
        }

        //else, create new publisher
        Publisher publisher = new Publisher(email, password, building);
        Log.d("write_new_publisher", "email + building: " + email + " " + building);
        String key = mDatabase.child("publishers").push().getKey();
        mDatabase.child("publishers").child(key).setValue(publisher);
        return key;
    }
}
