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

public class StudentHome extends AppCompatActivity implements HomepageRecyclerAdapter.ItemClickListener{
    private DatabaseReference mDatabase;
    private ArrayList<Publisher> mAllPublishers;
    private RecyclerView recyclerView;
    public HomepageRecyclerAdapter adapter;

    ArrayList<String> publisherNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        boolean fromUserLogin = intent.getExtras().getBoolean("fromUserLogin");
        if (fromUserLogin) {
            String currentUserID = intent.getExtras().getString("currentUserID");
            ((Global) this.getApplication()).setCurrentUserID(currentUserID);
            Log.d("student_home", currentUserID);
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAllPublishers = new ArrayList<>();
        grabAllPublishers();

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
    public void onItemClick(View view, int position) {

        Intent intent = new Intent(this, publisher_page_of_events.class);
//        intent.putExtras(bundle);
        intent.putExtra("PUBLISHER_NAME", adapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void onStart(){
        super.onStart();

        // Call grabAllPublisherEvents(publisherId);
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
                recyclerView= findViewById(R.id.rvPublishers);
                recyclerView.setLayoutManager(new LinearLayoutManager(StudentHome.this));
                adapter = new HomepageRecyclerAdapter(StudentHome.this, publisherNames);
                adapter.setClickListener(StudentHome.this);
                recyclerView.setAdapter(adapter);

                // TODO: Update UI
            }

            @Override
            public void onCancelled(DatabaseError dbe) {

            }
        });
    }

}
