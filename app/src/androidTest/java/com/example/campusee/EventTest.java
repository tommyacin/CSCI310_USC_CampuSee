package com.example.campusee;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class EventTest {
    private Context context = ApplicationProvider.getApplicationContext();
    private DatabaseReference mDatabase;

    @Test
    public void grabEventFromFirebase() {
        // Create event in the firebase
        // call grabEvent function from firebase
        // check that the fields are the same
        /*
        assertThat(resultName).isEqualTo(eventName);
        assertThat(resultDate).isEqualTo(eventDate);
        assertThat(resultTime).isEqualTo(eventTime);
         */
    }

    @Test
    public void removeEventFromFirebase() {
        // Create event in the firebase
        // Remove event in the firebase
        // check that you can't find the event
    }

    @Test
    public void updateEventToUnpublish() {
        // Create event in the firebase
        // grab event from firebase
        // call updateEventStatus
        // check that the fields are the same
    }

    @Test
    public void updateEventToPublish() {

    }

    @Test
    public void writeNewEventToFirebaseTest() {
        /*PublishEvent publishEvent = new PublishEvent();
        String existingPublisherId = "";
        publishEvent.writeNewEvent(
                existingPublisherId,
                "TestEvent",
                "Testing event",
                "11:00 PM",
                "",
                0,
                "");*/

        // check if the event is in the firebase
        /*
        assertThat(resultName).isEqualTo(eventName);
        assertThat(resultDate).isEqualTo(eventDate);
        assertThat(resultTime).isEqualTo(eventTime);
         */
    }

    @Test
    public void grabAllPublisherEventsTest() {
        // Call grab
        publisher_page_of_events publisherPageOfEvents = new publisher_page_of_events();
        String publisherId = "hello";
        publisherPageOfEvents.grabAllPublisherEvents(publisherId);
        // Check the count of the array compared to the firebase count
        int eventCount = publisherPageOfEvents.mAllEvents.size();
        int actualCount = 1;
        assertEquals(actualCount, eventCount);

        // Remove Events
    }

    public void setupDatabase() {
        // Connect to database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Clear all events
        cleanupDatabase();

        // Add publisher
        Publisher publisher = new Publisher("PublisherName1", "publisher1@gmail.com", "password1", "EEB");
        final String publisherKey = mDatabase.child("publishers").push().getKey();
        mDatabase.child("publishers").child(publisherKey).setValue(publisher);

        // Add event
        Query pubQuery = mDatabase.child("publishers").orderByKey().equalTo(publisherKey);
        pubQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Publisher curr_pub = ds.getValue(Publisher.class);

                        String eventKey = mDatabase.child("events").push().getKey();

                        Event newEvent = new Event(
                                publisherKey,
                                "EEB",
                                "Publisher 1's Event",
                                "",
                                "10:00",
                                "10/14/2019",
                                5,
                                "FOOD_ICON");


                        Map<String, Object> eventValues = newEvent.toMap();
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/events/" + eventKey, eventValues);
                        childUpdates.put("/publisher-events/" + publisherKey + "/" + eventKey, eventValues);
                        mDatabase.updateChildren(childUpdates);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void cleanupDatabase() {
        // Clear all events
        DatabaseReference eventDatabaseRef = mDatabase.child("events");
        DatabaseReference publisherEventDatabaseRef = mDatabase.child("publisher-events");

        eventDatabaseRef.removeValue();
        publisherEventDatabaseRef.removeValue();

        DatabaseReference publisherDatabaseRef = mDatabase.child("publishers");
        publisherDatabaseRef.removeValue();
    }
}
