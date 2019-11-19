package com.example.campusee;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GlobalTest {

    @Before
    public void setUp() {
    }

    @Test
    public void initializeBuildings() {
    }

    @Test
    public void getAllBuildings() {
    }

    @Test
    public void getCurrentUserIDTest() {
        Global tester = new Global();
        assertEquals(null, tester.getCurrentUserID());

        tester.setCurrentUserID("user1");
        assertEquals("user1", tester.getCurrentUserID());

        tester.setCurrentUserID("user2");
        assertEquals("user2", tester.getCurrentUserID());

        tester.setCurrentUserID("user3");
        assertEquals("user3", tester.getCurrentUserID());
    }

    @Test
    public void getCurrentPublisherIDTest() {
        Global tester = new Global();
        assertEquals(null, tester.getCurrentPublisherID());

        tester.setCurrentPublisherID("pub1");
        assertEquals("pub1", tester.getCurrentPublisherID());

        tester.setCurrentPublisherID("pub2");
        assertEquals("pub2", tester.getCurrentPublisherID());

        tester.setCurrentPublisherID("pub3");
        assertEquals("pub3", tester.getCurrentPublisherID());
    }

    @Test
    public void getAllEventsForUser() {
    }

    @Test
    public void getExistingPublishers() {
    }

    @Test
    public void getPublisherNames() {
    }

    @Test
    public void getGeofenceForNotifications() {
    }

    @Test
    public void setCurrentUserIDTest() {
        Global tester = new Global();
        tester.setCurrentUserID("user1");
        assertEquals("user1", tester.getCurrentUserID());

        tester.setCurrentUserID("user2");
        assertEquals("user2", tester.getCurrentUserID());

        tester.setCurrentUserID("user3");
        assertEquals("user3", tester.getCurrentUserID());
    }

    @Test
    public void setCurrentPublisherIDTest() {
        Global tester = new Global();
        tester.setCurrentPublisherID("pub1");
        assertEquals("pub1", tester.getCurrentPublisherID());

        tester.setCurrentPublisherID("pub2");
        assertEquals("pub2", tester.getCurrentPublisherID());

        tester.setCurrentPublisherID("pub3");
        assertEquals("pub3", tester.getCurrentPublisherID());
    }

    @Test
    public void grabAllPublishers() {
    }

    @Test
    public void grabAllGeofenceHolders() {
    }

    @Test
    public void grabAllGeofences() {
    }

    @Test
    public void grabAllSubscribedPublisherKeys() {
    }

    @Test
    public void grabAllUserNotifications() {
    }
}