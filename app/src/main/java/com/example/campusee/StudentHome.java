package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class StudentHome extends AppCompatActivity{
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button studentButton = (Button) findViewById(R.id.notificationToolbarButton);
            studentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // add code here for what will happen when the user selects the student button
                    Intent notificationPageIntent = new Intent(getApplicationContext(), NotificationPage.class);
                    StudentHome.this.startActivity(notificationPageIntent);
                }
            });

        Button mapButton = (Button) findViewById(R.id.mapToolbarButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent mapPageIntent = new Intent(getApplicationContext(), activity_map.class);
                StudentHome.this.startActivity(mapPageIntent);
            }
        });

    }

    public void grabAllPublishers() {
        Query publishers = mDatabase.child("publishers");

        publishers.addChildEventListener(new ChildEventListener() {

            // This will get called as many times as there are publishers in the database
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // grab a single publisher
                Publisher publisher = dataSnapshot.getValue(Publisher.class);
                Log.d("grabpublishers", "publisher: " + publisher);

                // TODO: Update UI
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("LoadPublishers error", "publishers:onCancelled:" + databaseError.getMessage());
            }
        });
    }

}
