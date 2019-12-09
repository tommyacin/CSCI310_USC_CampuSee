package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private ArrayList<String> mAllEventIds;
    RecyclerView recyclerView;
    public String currentPublisherName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_main);

        //get current publisherID and set it globally
        String currentPublisherID = intent.getExtras().getString("currentPublisherID");
        currentPublisherName = intent.getExtras().getString("currentPublisherName");
        final String currentPublisherEmail = intent.getExtras().getString("currentPublisherEmail");


        TextView name = (TextView) findViewById(R.id.publisher_main_page_name);
        name.setText(currentPublisherName);


        ((Global) this.getApplication()).setCurrentPublisherID(currentPublisherID);

        Log.d("publisher_main", currentPublisherID);

        // Initialize Database - grabbing just the publisher-events
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // data to populate the RecyclerView with
        mAllEvents = new ArrayList<>();
        mAllEventIds = new ArrayList<>();

        grabAllPublisherEvents(currentPublisherID);
        updatePublisherMainPage(currentPublisherID);

        Button createButton = (Button) findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent createIntent = new Intent(getApplicationContext(), CreateEvent.class);
                PublisherMain.this.startActivity(createIntent);
            }
        });

        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PublisherMain.this.startActivity(mainActivityIntent);
            }
        });

        Button profileButton = (Button) findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(getApplicationContext(), PublisherProfilePage.class);
                profileIntent.putExtra("currentPublisherName", currentPublisherName);
                profileIntent.putExtra("currentPublisherEmail", currentPublisherEmail);
                PublisherMain.this.startActivity(profileIntent);
            }
        });


    }

    @Override
    public void onItemClick(View view, int position) {
        Intent editEventIntent = new Intent(this, EditEvent.class);
        Log.d("onItemClick", adapter.getItem(position).title);
        Log.d("onItemClick", adapter.getItem(position).description);
        editEventIntent.putExtra("EVENT_NAME", adapter.getItem(position).title);
        editEventIntent.putExtra("EVENT_DES", adapter.getItem(position).description);
        editEventIntent.putExtra("EVENT_TIME", adapter.getItem(position).time);
        editEventIntent.putExtra("EVENT_DATE", adapter.getItem(position).date);
        editEventIntent.putExtra("EVENT_ICON", adapter.getItem(position).iconFileName);
        editEventIntent.putExtra("EVENT_RADIUS", adapter.getItem(position).radius);
        editEventIntent.putExtra("EVENT_BUILDING", adapter.getItem(position).building);
        editEventIntent.putExtra("EVENT_ID", mAllEventIds.get(position));
        editEventIntent.putExtra("PUBLISHER_ID", adapter.getItem(position).publisherId)
        Log.d("onItemClick", adapter.getItem(position).iconFileName);
        startActivity(editEventIntent);
    }

    @Override
    public void onStart(){
        super.onStart();
        TextView name = (TextView) findViewById(R.id.publisher_main_page_name);
        name.setText(currentPublisherName);

    }

    public void updatePublisherMainPage(String pub_ID) {
        Query pubQuery = mDatabase.child("publishers").orderByKey().equalTo(pub_ID);
        pubQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Publisher curr_pub = ds.getValue(Publisher.class);

                        TextView building = (TextView) findViewById(R.id.publisher_main_page_building);

                        building.setText(curr_pub.building);

                        Log.d("update_publisher_page", curr_pub.name);
                        Log.d("update_publisher_page", curr_pub.building);

                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void grabAllPublisherEvents(String publisherId) {
        Query publisherEvents = mDatabase.child("publisher-events").child(publisherId);

        publisherEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // grab a single publisher
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Event event = ds.getValue(Event.class);
                    mAllEvents.add(event);
                    mAllEventIds.add(ds.getKey());
                }

                // set up the RecyclerView
                recyclerView = findViewById(R.id.rvEvents);
                recyclerView.setLayoutManager(new LinearLayoutManager(PublisherMain.this));
                adapter = new EventRecyclerAdapter(PublisherMain.this, mAllEvents);
                adapter.setClickListener(PublisherMain.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError dbe) {
            }
        });
    }
}
