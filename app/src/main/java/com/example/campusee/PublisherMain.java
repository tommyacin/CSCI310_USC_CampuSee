package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class PublisherMain extends AppCompatActivity implements EventRecyclerAdapter.ItemClickListener {

    private DatabaseReference mPublisherEventReference;
    private EventRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_main);

        // Initialize Database - grabbing just the publisher-events
        mPublisherEventReference = FirebaseDatabase.getInstance().getReference()
                .child("publisher-events");

        // data to populate the RecyclerView with
        ArrayList<String> eventNames = new ArrayList<>();
        eventNames.add("Viterbi Expo");
        eventNames.add("Career fair");
        eventNames.add("Robotics fair");
        eventNames.add("Industry Q & A");
        eventNames.add("Ice cream and pizza!");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventRecyclerAdapter(this, eventNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        Button createButton = (Button) findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent createIntent = new Intent(getApplicationContext(), CreateEvent.class);
                PublisherMain.this.startActivity(createIntent);
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
//        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(){
        super.onStart();

        // Call grabAllPublisherEvents(publisherId);
    }



    public void grabAllPublisherEvents(String publisherId) {
        Query publisherEvents = mPublisherEventReference.child("publisher-events").child(publisherId);

        Log.d("grabevents", "here");
        publisherEvents.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // dataSnapshot = a single child under the particular publisher
                Event event = dataSnapshot.getValue(Event.class);

                // TODO: Update UI
                // Can grab parts of the data by doing event.<insert member variable>
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("LoadEvents error", "events:onCancelled:" + databaseError.getMessage());
            }
        });
    }
}
