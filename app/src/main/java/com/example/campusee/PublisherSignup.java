package com.example.campusee;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PublisherSignup extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private String publisherID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_signup);

        // Getting the database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //sign up
        Button publisherSignUpButton = (Button) findViewById(R.id.publisher_signup_button);
        publisherSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick();
            }
        });

        //login
        Button publisherLoginButton = (Button) findViewById(R.id.publisher_login_button);
        publisherLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick();
            }
        });
    }

    private void buttonClick() {
        EditText name_input = (EditText) findViewById(R.id.publisher_signup_name);
        EditText email_input = (EditText) findViewById(R.id.publisher_signup_email);
        EditText password_input = (EditText) findViewById(R.id.publisher_signup_password);
        EditText building_input = (EditText) findViewById(R.id.publisher_signup_building);

        String name = name_input.getText().toString();
        String password = password_input.getText().toString();
        String email = email_input.getText().toString();
        String building = building_input.getText().toString();

        Boolean continue_signup = true;
        if(name == null || name.isEmpty()){
            name_input.setHint("Please input a name");
            name_input.setHintTextColor(Color.parseColor("#990000"));
            continue_signup = false;
        }
        if(email == null || email.isEmpty()){
            email_input.setHint("Please input an email");
            email_input.setHintTextColor(Color.parseColor("#990000"));
            continue_signup = false;
        }
        if(password == null || password.isEmpty()){
            password_input.setHint("Please input a password");
            password_input.setHintTextColor(Color.parseColor("#990000"));
            continue_signup = false;
        }
        if(continue_signup){
            publisherSignupOrLogin(name, email, password, building);
        }
    }

    private void publisherSignupOrLogin(final String name, final String email, final String password, final String building) {
        //check publisher exists
        publisherID = null;
        Query emailQuery = mDatabase.child("publishers").orderByChild("email").equalTo(email);
        emailQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Log.d("isExistingPublisher", String.valueOf(dataSnapshot.getChildrenCount()));
                        Publisher publisher = ds.getValue(Publisher.class);
                        publisherID = ds.getKey();
                        Log.d("isExistingPublisher", publisherID);
                    }
                }

                if (publisherID == null) {
                    publisherID = writeNewPublisher(name, email, password, building);
                }

                Intent publisherIntent = new Intent(getApplicationContext(), PublisherMain.class);
                publisherIntent.putExtra("currentPublisherID", publisherID);
                PublisherSignup.this.startActivity(publisherIntent);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private String writeNewPublisher(String name, String email, String password, String building) {
        Publisher publisher = new Publisher(name, email, password, building);
        String key = mDatabase.child("publishers").push().getKey();
        Log.d("write_new_publisher", key);
        mDatabase.child("publishers").child(key).setValue(publisher);
        return key;
    }
}
