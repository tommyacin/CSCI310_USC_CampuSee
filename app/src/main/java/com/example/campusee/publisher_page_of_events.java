package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class publisher_page_of_events extends AppCompatActivity {

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_page_of_events);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        final String publisher_name = intent.getStringExtra("PUBLISHER_NAME");
        final Button subscribeButton = (Button) findViewById(R.id.subscribe_btn);
        final String currentUserId = ((Global) this.getApplication()).getCurrentPublisherID();
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                if (subscribeButton.getText().equals("Subscribe")){
//                    subscribeToPublisher(currentUserId, publisher_name);
                    subscribeButton.setText("Unsubscribe");
                } else{
//                    unsubscribeUser(currentUserId, publisher_name);
                    subscribeButton.setText("Subscribe");
                }
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

}