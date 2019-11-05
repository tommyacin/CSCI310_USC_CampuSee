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

public class SecondActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Getting the database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button studentButton = (Button) findViewById(R.id.button);
        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                EditText name_input = (EditText) findViewById(R.id.user_signup_name);
                EditText email_input = (EditText) findViewById(R.id.user_signup_email);
                EditText password_input = (EditText) findViewById(R.id.user_signup_password);

                String name = name_input.getText().toString();
                String email = email_input.getText().toString();
                String password = password_input.getText().toString();

                Log.d("user_signup", "User: " + name + " " + email);

                String userID = writeNewUser(name, email, password);

                Intent toStudentHome = new Intent(getApplicationContext(), StudentHome.class);
                toStudentHome.putExtra("currentUserID", userID);
                SecondActivity.this.startActivity(toStudentHome);
            }
        });

    }

    // Write user to database; returns unique userID
    private String writeNewUser(String name, String email, String password) {
        User user = new User(name, email, password, getApplicationContext());
        String key = mDatabase.child("users").push().getKey();
        mDatabase.child("users").child(key).setValue(user);
        return key;
    }


}
