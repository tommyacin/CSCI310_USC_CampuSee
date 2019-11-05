package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PublisherMain extends AppCompatActivity implements EventRecyclerAdapter.ItemClickListener {

    private DatabaseReference mDatabase;
    private EventRecyclerAdapter adapter;
    private ArrayList<Event> mAllEvents;
    RecyclerView recyclerView;
    ArrayList<String> eventNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_main);

        //get current publisherID and set it globally
        String currentPublisherID = intent.getExtras().getString("currentPublisherID");
        ((Global) this.getApplication()).setCurrentPublisherID(currentPublisherID);

        //get current publisher and update publisher main page
        Publisher currentPublisher = ((Global) this.getApplication()).getCurrentPublisher();
        updatePublisherMainPage(currentPublisher);

        Log.d("publisher_main", currentPublisherID);

        // Initialize Database - grabbing just the publisher-events
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // data to populate the RecyclerView with
        mAllEvents = new ArrayList<>();

        grabAllPublisherEvents(currentPublisherID);

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
        // go to the next page to edit / delete item
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("KEY", adapter.getItem(position));
        Intent intent = new Intent(this, EditEvent.class);
//        intent.putExtras(bundle);
        intent.putExtra("EVENT_NAME", adapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    public void updatePublisherMainPage(Publisher curr_pub) {
        TextView name = (TextView) findViewById(R.id.publisher_main_page_name);
        TextView building = (TextView) findViewById(R.id.publisher_main_page_building);

        name.setText(curr_pub.name);
        building.setText(curr_pub.building);
    }

    public void grabAllPublisherEvents(String publisherId) {
        Query publisherEvents = mDatabase.child("publisher-events").child(publisherId);

        publisherEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // grab a single publisher
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Event event = ds.getValue(Event.class);
                    Log.d("PUBLISHER_EVENT", event.title);
                    mAllEvents.add(event);
                    eventNames.add(event.title);
                }

                // set up the RecyclerView
                recyclerView = findViewById(R.id.rvEvents);
                recyclerView.setLayoutManager(new LinearLayoutManager(PublisherMain.this));
                adapter = new EventRecyclerAdapter(PublisherMain.this, eventNames);
                adapter.setClickListener(PublisherMain.this);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError dbe) {

            }
        });
    }
}
