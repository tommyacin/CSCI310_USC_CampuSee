package com.example.campusee;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SubscriptionTest {
    private Context context = ApplicationProvider.getApplicationContext();

    @Test
    public void addNewUserSubscriptionTest() {
        publisher_page_of_events publisherPageOfEvents = new publisher_page_of_events();
        // publisherPageOfEvents.writeNewUserSubscription(String userId, String publisherId, Publisher publisher);
    }

    @Test
    public void unsubscribeUserTest() {

    }
}
