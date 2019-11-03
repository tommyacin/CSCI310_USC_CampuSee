package com.example.campusee;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Context;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        // Getting the database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //if statement needs to be configured such that only activates when student is created
        //probably need to move this to student sign up activity
        //currently here for testing purposes


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

//        if(true)
//        {
//            writeNewPerson("test1@gmail.com", "password");
//
//        }
    }

    // Write user to database
    private void writeNewPerson(String email, String password) {
        user = new User(email, password, getApplicationContext());
        //Event notifTester = new Event("Viterbi", "Career Fair", "Come get a job", "5:30", 1);
        //user.sendNotification(notifTester);

        String key = mDatabase.child("users").push().getKey();
        mDatabase.child("users").child(key).setValue(user);
    }

    //create channel on which to send notifications, this is called in User class
    public void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.notif_channel_name);
            String description = getString(R.string.notif_channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("1234", name, importance);
            channel.setDescription(description);
            channel.setLockscreenVisibility(1);


            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



}
