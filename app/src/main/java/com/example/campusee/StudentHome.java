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
import com.google.firebase.database.ValueEventListener;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentHome extends AppCompatActivity{
    private DatabaseReference mDatabase;
    private ArrayList<Publisher> mAllPublishers;

    HomepageRecyclerAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<String> publisherNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAllPublishers = new ArrayList<>();
        grabAllPublishers();

        recyclerView = findViewById(R.id.rvPublishers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HomepageRecyclerAdapter(this, publisherNames);
        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        /*
        //this is all here for notif testing
        User user = new User("foo", "foo", getApplicationContext());
        double[] loc = {123.32, 123.32};
        Event notifTester = new Event("Viterbi", "Career Fair", "Come get a job", "5:30", 1, loc, 100);
        user.sendNotification(notifTester);
         */

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

    public void grabAllPublishers(/*android.content.Context context*/) {
        Query publishers = mDatabase.child("publishers");

        publishers.addValueEventListener(new ValueEventListener() {

            // This will get called as many times as there are publishers in the database
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // grab a single publisher
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Publisher publisher = ds.getValue(Publisher.class);
                    Log.d("grabpublishers", "publisher: " + publisher);
                    mAllPublishers.add(publisher);
                    publisherNames.add(publisher.building);
                }
                adapter = new HomepageRecyclerAdapter(StudentHome.this, publisherNames);
                recyclerView.setAdapter(adapter);

                // TODO: Update UI
            }

            @Override
            public void onCancelled(DatabaseError dbe) {

            }

            /*
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
             */
        });
    }

}
