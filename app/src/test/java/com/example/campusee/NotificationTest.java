package com.example.campusee;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotificationTest {
    private String title, description, time, publisherId, notifId, dbEventId, building, date, icon_file;
    private Event eventId;
    private int radius;

    @Before
    public void setUp() {
        title = "title";
        description = "desc";
        time = "12:34";
        publisherId = "1234";
        notifId = "5678";
        dbEventId = "1234";
        building = "THH";
        date = "06/06";
        radius = 250;
        icon_file = "foo";
    }

    @Test
    public void notificationConstructorTest() {
        Notification tester = new Notification(title, description, time, publisherId, notifId, dbEventId);
        assertEquals("title", tester.title);
        assertEquals("desc", tester.description);
        assertEquals("12:34", tester.time);
        assertEquals("1234", tester.publisherId);
        assertEquals("5678", tester.notifId);
        assertEquals("1234", tester.dbEventId);
    }

    @Test
    public void setEventTest() {
        Notification tester = new Notification(title, description, time, publisherId, notifId, dbEventId);
        assertEquals(null, tester.eventId);

        Event new_event = new Event(publisherId, building, title, description, time, date, radius, icon_file);
        tester.setEvent(new_event);
        assertEquals(new_event, tester.eventId);
    }

    @Test
    public void getTitleTest() {
        Notification tester = new Notification(title, description, time, publisherId, notifId, dbEventId);
        assertEquals(title, tester.getTitle());
    }

    @Test
    public void getDescriptionTest() {
        Notification tester = new Notification(title, description, time, publisherId, notifId, dbEventId);
        assertEquals(description, tester.getDescription());
    }

    @Test
    public void getNotifIdTest() {
        Notification tester = new Notification(title, description, time, publisherId, notifId, dbEventId);
        assertEquals(notifId, tester.getNotifId());
    }

    @Test
    public void getPublisherIdTest() {
        Notification tester = new Notification(title, description, time, publisherId, notifId, dbEventId);
        assertEquals(publisherId, tester.getPublisherId());
    }
}