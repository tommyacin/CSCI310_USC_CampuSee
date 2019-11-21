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
import java.util.HashMap;
import java.util.Map;

public class publisher_page_of_events extends AppCompatActivity implements EventRecyclerAdapter.ItemClickListener {

    DatabaseReference mDatabase;
    Button subscribeButton;
    private EventRecyclerAdapter adapter;
    protected ArrayList<Event> mAllEvents;
    RecyclerView recyclerView;
    ArrayList<String> eventNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_page_of_events);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        //final String publisher_name = intent.getStringExtra("PUBLISHER_NAME");
        final String currentPublisherID = intent.getStringExtra("currentPublisherID");
        ((Global) this.getApplication()).setCurrentPublisherID(currentPublisherID);

        Log.d("inPublisherPageOfEvents", "publisher_id_global: " + ((Global) this.getApplication()).getCurrentPublisherID());
        Log.d("inPublisherPageOfEvents", "user_id_global: " + ((Global) this.getApplication()).getCurrentUserID());

        updatePublisherProfilePage(currentPublisherID);
        grabAllPublisherEvents(currentPublisherID);

        // check if subscription exists
        final String currentUserID = ((Global) this.getApplication()).getCurrentUserID();
        subscribeButton = (Button) findViewById(R.id.subscribe_btn);
        checkSubscription(currentUserID, currentPublisherID);


        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                if (subscribeButton.getText().equals("Subscribe")){
                    subscribeToPublisher(currentUserID, currentPublisherID);
                    subscribeButton.setText("Unsubscribe");
                } else{
                    unsubscribeUser(currentUserID, currentPublisherID);
                    subscribeButton.setText("Subscribe");
                }
            }
        });
    }

    private void checkSubscription(final String userId, final String publisherId) {
        Query subscriptionQuery = mDatabase.child("user-publishers").child(userId).child(publisherId);
        subscriptionQuery.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        if (!dataSnapshot.exists()) {
                            subscribeButton.setText("Subscribe");
                        } else {
                            subscribeButton.setText("Unsubscribe");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private void subscribeToPublisher(final String userId, final String publisherId) {
        DatabaseReference publisherRef = mDatabase.child("publishers").child(publisherId);

        publisherRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        Publisher publisher = dataSnapshot.getValue(Publisher.class);

                        // Write new post
                        writeNewUserSubscription(userId, publisherId, publisher);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("subscribeToPublisher", "getSubscription:onCancelled", databaseError.toException());
                    }
                });
    }

    protected void writeNewUserSubscription(String userId, String publisherId, Publisher publisher) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/user-publishers/" + userId + "/" + publisherId, publisher.toMap());

        mDatabase.updateChildren(childUpdates);
    }

    private void unsubscribeUser(String userId, String publisherId) {
        DatabaseReference subscriptionRef = mDatabase.child("user-publishers").child(userId);
        subscriptionRef.child(publisherId).removeValue();
    }

    public void updatePublisherProfilePage(String pub_ID) {
        Query pubQuery = mDatabase.child("publishers").orderByKey().equalTo(pub_ID);
        pubQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Publisher curr_pub = ds.getValue(Publisher.class);

                        TextView name = (TextView) findViewById(R.id.publisher_profile_page_name);
                        TextView building = (TextView) findViewById(R.id.publisher_profile_page_building);
                        name.setText(curr_pub.name);
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
        mAllEvents =  new ArrayList<>();
        eventNames =  new ArrayList<>();
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
                recyclerView = findViewById(R.id.publisher_profile_page_rv);
                recyclerView.setLayoutManager(new LinearLayoutManager(publisher_page_of_events.this));
                adapter = new EventRecyclerAdapter(publisher_page_of_events.this, mAllEvents);
                adapter.setClickListener(publisher_page_of_events.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError dbe) {
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        //do nothing
    }
}