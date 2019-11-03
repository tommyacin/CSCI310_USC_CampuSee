package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentHome extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        HomepageRecyclerAdapter adapter;
        // data to populate the RecyclerView with
        RecyclerView recyclerView = findViewById(R.id.rvPublishers);
        ArrayList<String> publisherNames = new ArrayList<>();
        //adapter = new PublisherRecyclerviewAdapter(this, animalNames);

        publisherNames.add("Viterbi");
        publisherNames.add("Dornsife");
        publisherNames.add("Marshall");
        publisherNames.add("Roski");
        publisherNames.add("Leventhal");

        User user = new User("foo", "foo", getApplicationContext());
        Event notifTester = new Event("Viterbi", "Career Fair", "Come get a job", "5:30", 1);
        user.sendNotification(notifTester);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HomepageRecyclerAdapter(this, publisherNames);
        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        //something something to get list of publishers from database


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

}
