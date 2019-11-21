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
public class SubscriptionTest {
    private Context context = ApplicationProvider.getApplicationContext();
    private DatabaseReference mDatabase;

    @Test
    public void addNewUserSubscriptionTest() {
        setupDatabase();
        publisher_page_of_events publisherPageOfEvents = new publisher_page_of_events();
        // publisherPageOfEvents.writeNewUserSubscription(String userId, String publisherId, Publisher publisher);

        cleanupDatabase();
    }

    @Test
    public void unsubscribeUserTest() {
        setupDatabase();

        cleanupDatabase();
    }

    public void setupDatabase() {
        // Connect to database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Clear users, publishers, subscriptions
        cleanupDatabase();

        // Add publisher
        Publisher publisher = new Publisher("PublisherName1", "publisher1@gmail.com", "password1", "EEB");
        String publisherKey = mDatabase.child("publishers").push().getKey();
        mDatabase.child("publishers").child(publisherKey).setValue(publisher);

        // Add user
        User user = new User("UserName1", "user1@gmail.com", "password1");
        String userKey = mDatabase.child("users").push().getKey();
        mDatabase.child("users").child(userKey).setValue(user);
    }

    public void cleanupDatabase() {
        // Clear all users
        DatabaseReference userDatabaseRef = mDatabase.child("users");
        userDatabaseRef.removeValue();

        // Clear all subscriptions
        DatabaseReference subscriptionsDatabaseRef = mDatabase.child("user-publishers");
        subscriptionsDatabaseRef.removeValue();

        // Clear all publishers
        DatabaseReference publisherDatabaseRef = mDatabase.child("publishers");
        publisherDatabaseRef.removeValue();
    }
}
