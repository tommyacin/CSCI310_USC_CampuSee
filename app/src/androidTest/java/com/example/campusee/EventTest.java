package com.example.campusee;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class EventTest {
    private Context context = ApplicationProvider.getApplicationContext();

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
    public void changeFromUnpublishToPublishEvent() {
        // Create event in the firebase
        // grab event from firebase
        // call updateEventStatus
        // check that the fields are the same
    }

    @Test
    public void changeFromPublishToUnpublishEvent() {

    }

    @Test
    public void writeNewEventToFirebase() {
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
}
