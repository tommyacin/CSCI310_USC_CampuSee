package com.example.campusee;

import android.content.Intent;
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

public class SecondActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private String mUserID;
    private boolean mUserExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Getting the database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUserID = null;
        mUserExists = false;

        Button userSignupButton = (Button) findViewById(R.id.user_signup_button);
        userSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                String userID = buttonClick();

                Intent toStudentHome = new Intent(getApplicationContext(), StudentHome.class);
                toStudentHome.putExtra("currentUserID", userID);
                SecondActivity.this.startActivity(toStudentHome);
            }
        });

        Button userLoginButton = (Button) findViewById(R.id.user_login_button);
        userLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                String userID = buttonClick();

                Intent toStudentHome = new Intent(getApplicationContext(), StudentHome.class);
                toStudentHome.putExtra("currentUserID", userID);
                SecondActivity.this.startActivity(toStudentHome);
            }
        });

    }

    private String buttonClick() {
        EditText name_input = (EditText) findViewById(R.id.user_signup_name);
        EditText email_input = (EditText) findViewById(R.id.user_signup_email);
        EditText password_input = (EditText) findViewById(R.id.user_signup_password);

        String name = name_input.getText().toString();
        String email = email_input.getText().toString();
        String password = password_input.getText().toString();

        return userSignupOrLogin(name, email, password);
    }

    // Write user to database; returns unique userID
    private String userSignupOrLogin(String name, String email, String password) {
        //check user exists
        DatabaseReference usersRef = mDatabase.child("users");
        Query emailQuery = usersRef.orderByChild("email").equalTo(email);
        emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot user: dataSnapshot.getChildren() ){
                    // Iterate through all the users with the same email
                    user.getKey();
                    mUserID = user.getKey();
                    mUserExists = true;
                    Log.d("isExistingUser", mUserID);
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //if exists, return that userID
        if (mUserExists) {
            Log.d("isExistingUser", "here: " + mUserID);
            return mUserID;
        }

        //else, create new user
        User user = new User(name, email, password, getApplicationContext());
        String key = mDatabase.child("users").push().getKey();
        Log.d("write_new_user", key);
        mDatabase.child("users").child(key).setValue(user);
        return key;
    }
}
