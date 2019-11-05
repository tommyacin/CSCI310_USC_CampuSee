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
    private ArrayList<Event> mAllEvents;
    RecyclerView recyclerView;
    ArrayList<String> eventNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_page_of_events);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        final String publisher_name = intent.getStringExtra("PUBLISHER_NAME");
        final String publisher_id = intent.getStringExtra("currentPublisherID");
        ((Global) this.getApplication()).setCurrentPublisherID(publisher_id);

        Log.d("inPublisherPageOfEvents", "publisher_id_global: " + ((Global) this.getApplication()).getCurrentPublisherID());
        Log.d("inPublisherPageOfEvents", "user_id_global: " + ((Global) this.getApplication()).getCurrentUserID());


        TextView nameTV = findViewById(R.id.nameOfPublisher);
        nameTV.setText(publisher_name);
        subscribeButton = (Button) findViewById(R.id.subscribe_btn);
        final String currentUserId = ((Global) this.getApplication()).getCurrentUserID();
        final String currentPublisherId = ((Global) this.getApplication()).getCurrentPublisherID();

        // check if subscription exists
        checkSubscription(currentUserId, currentPublisherId);


        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                if (subscribeButton.getText().equals("Subscribe")){
                    subscribeToPublisher(currentUserId, currentPublisherId);
                    subscribeButton.setText("Unsubscribe");
                } else{
                    unsubscribeUser(currentUserId, currentPublisherId);
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

    private void writeNewUserSubscription(String userId, String publisherId, Publisher publisher) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/user-publishers/" + userId + "/" + publisherId, publisher.toMap());

        mDatabase.updateChildren(childUpdates);
    }

    private void unsubscribeUser(String userId, String publisherId) {
        DatabaseReference subscriptionRef = mDatabase.child("user-publishers").child(userId);
        subscriptionRef.child(publisherId).removeValue();
    }

    public void updatePublisherMainPage(String pub_ID) {
        Query pubQuery = mDatabase.child("publishers").orderByKey().equalTo(pub_ID);
        pubQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Publisher curr_pub = ds.getValue(Publisher.class);

                        TextView name = (TextView) findViewById(R.id.publisher_main_page_name);
                        TextView building = (TextView) findViewById(R.id.publisher_main_page_building);
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
                recyclerView.setLayoutManager(new LinearLayoutManager(publisher_page_of_events.this));
                adapter = new EventRecyclerAdapter(publisher_page_of_events.this, eventNames);
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

    }
}