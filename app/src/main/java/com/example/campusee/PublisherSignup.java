package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        Button publisherButton = (Button) findViewById(R.id.next_button);
        publisherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                EditText name_input = (EditText) findViewById(R.id.publisher_signup_name);
                EditText username_input = (EditText) findViewById(R.id.publisher_signup_username);
                EditText email_input = (EditText) findViewById(R.id.publisher_signup_email);
                EditText building_input = (EditText) findViewById(R.id.publisher_signup_building);

                String name = name_input.getText().toString();
                String username = username_input.getText().toString();
                String email = email_input.getText().toString();
                String building = building_input.getText().toString();

                String publisherID = writeNewPublisher(email, "hello", building);

                Intent publisherIntent = new Intent(getApplicationContext(), PublisherMain.class);
                publisherIntent.putExtra("currentPublisherID", publisherID);
                PublisherSignup.this.startActivity(publisherIntent);
            }
        });
    }

    //write new publisher to database; return unique publisherID
    private String writeNewPublisher(String email, String password, String building) {
        Publisher publisher = new Publisher(email, password, building);
        Log.d("write_new_publisher", "building: " + building);

        String key = mDatabase.child("publishers").push().getKey();
        mDatabase.child("publishers").child(key).setValue(publisher);
        return key;
    }
}
