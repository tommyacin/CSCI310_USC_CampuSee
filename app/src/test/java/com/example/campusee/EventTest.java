package com.example.campusee;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class EventTest {
    private String publisherId, building, title, description, time, date, iconFileName;
    private int radius;

    @Before
    public void setUp() {
        title = "title";
        description = "desc";
        time = "12:34";
        publisherId = "1234";
        building = "THH";
        date = "06/06";
        iconFileName = "foo";
        radius = 250;
    }

    @Test
    public void eventConstructorTest() {
        Event tester = new Event(publisherId, building, title, description, time, date, radius, iconFileName);
        assertEquals("title", tester.title);
        assertEquals("desc", tester.description);
        assertEquals("12:34", tester.time);
        assertEquals("1234", tester.publisherId);
        assertEquals("THH", tester.building);
        assertEquals("06/06", tester.date);
        assertEquals("foo", tester.iconFileName);
        assertEquals(250, tester.radius);
    }

    @Test
    public void toMapTest() {
        Event tester = new Event(publisherId, building, title, description, time, date, radius, iconFileName);
        Map<String, Object> tester_map = tester.toMap();
        assertEquals(tester_map.get("title"), tester.title);
        assertEquals(tester_map.get("description"), tester.description);
        assertEquals(tester_map.get("time"), tester.time);
        assertEquals(tester_map.get("publisherId"), tester.publisherId);
        assertEquals(tester_map.get("building"), tester.building);
        assertEquals(tester_map.get("date"), tester.date);
        assertEquals(tester_map.get("iconFileName"), tester.iconFileName);
    }

    @Test
    public void setStatusTest() {
        Event tester = new Event(publisherId, building, title, description, time, date, radius, iconFileName);
        assertEquals(tester.status, false);

        tester.setStatus(true);
        assertEquals(tester.status, true);

        tester.setStatus(false);
        assertEquals(tester.status, false);
    }

    @Test
    public void getRadiusTest() {
        Event tester = new Event(publisherId, building, title, description, time, date, radius, iconFileName);
        assertEquals(radius, tester.getRadius());
    }
}