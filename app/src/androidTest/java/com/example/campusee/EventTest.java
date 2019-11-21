package com.example.campusee;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class EventTest {
    private DatabaseReference mDatabase;
    private String publisher1Key;
    private String event1Key;
    private Event testEvent;
    publisher_page_of_events publisherPageOfEvents;

    @Rule public ActivityTestRule<publisher_page_of_events> mPublisherPageOfEventsTestRule =
            new ActivityTestRule<>(publisher_page_of_events.class, true, true);

    @Before
    public void before() {
        publisherPageOfEvents = mPublisherPageOfEventsTestRule.getActivity();
    }

    /*@Test
    public void grabEventFromFirebaseTest() {
        setupDatabase();

        // call grabEvent function from firebase



        // check that the fields are the same


        cleanupDatabase();
    }

    @Test
    public void removeEventFromFirebase() {
        setupDatabase();
        // Create event in the firebase
        // Remove event in the firebase
        // check that you can't find the event
        cleanupDatabase();
    }

    @Test
    public void updateEventToUnpublish() {
        setupDatabase();
        // Create event in the firebase
        // grab event from firebase
        // call updateEventStatus
        // check that the fields are the same
        cleanupDatabase();
    }

    @Test
    public void updateEventToPublish() {
        setupDatabase();

        cleanupDatabase();
    }

    @Test
    public void writeNewEventToFirebaseTest() {
        setupDatabase();

        PublishEvent publishEvent = new PublishEvent();
        String eventTitle = "Test Event";
        String eventDate = "01/02/2019";
        String eventTime = "11:00";
        String eventDes = "Testing event";
        String eventIcon = "BOOK_ICON";
        int eventRadius = 2;


        publishEvent.writeNewEvent(
                publisher1Key,
                eventTitle,
                eventDes,
                eventTime,
                eventDate,
                eventRadius,
                eventIcon);

        // Grab event from firebase
        DatabaseReference usersRef = mDatabase.child("events");
        Query eventTitleQuery = usersRef.orderByChild("title").equalTo(eventTitle);
        eventTitleQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for(DataSnapshot u: dataSnapshot.getChildren() ){
                        Event e = u.getValue(Event.class);
                        testEvent = e;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        // check if the event is in the firebase
        assertSame("Testing title", testEvent.title, eventTitle);
        assertSame("Testing date", testEvent.date, eventDate);
        assertSame("Testing time", testEvent.time, eventTime);
        assertSame("Testing des", testEvent.description, eventDes);
        assertSame("Testing icon", testEvent.iconFileName, eventIcon);

        cleanupDatabase();
    }*/

    @Test
    public void grabAllPublisherEventsTest() {

        setupDatabase();

        publisherPageOfEvents = mPublisherPageOfEventsTestRule.getActivity();
        // Grab all publisher events
        publisherPageOfEvents.grabAllPublisherEvents(publisher1Key);

        // Check the count of the array compared to the firebase count
        int eventCount = publisherPageOfEvents.mAllEvents.size();
        int actualCount = 1;
        assertEquals(actualCount, eventCount);

        cleanupDatabase();
    }

    public void setupDatabase() {
        // Connect to database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Clear all events
        cleanupDatabase();

        // Add publisher
        Publisher publisher = new Publisher("PublisherName1", "publisher1@gmail.com", "password1", "EEB");
        final String publisherKey = mDatabase.child("publishers").push().getKey();
        publisher1Key = publisherKey;
        mDatabase.child("publishers").child(publisherKey).setValue(publisher);

        // set up global

        // Add event
        Query pubQuery = mDatabase.child("publishers").orderByKey().equalTo(publisherKey);
        pubQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Publisher curr_pub = ds.getValue(Publisher.class);

                        String eventKey = mDatabase.child("events").push().getKey();
                        event1Key = eventKey;

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
