package com.example.campusee;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PublisherTest {
    private String name, email, password, building;

    @Before
    public void setUp(){
        name = "Tommy";
        email = "hello@usc.edu";
        password = "thisismypassword";
        building = "THH";
    }

    @Test
    public void publisherConstructorTest() {
        Publisher tester = new Publisher(name, email, password, building);
        assertEquals("Tommy", tester.name);
        assertEquals("hello@usc.edu", tester.email);
        assertEquals("thisismypassword", tester.password);
        assertEquals("THH", tester.building);
    }

    @Test
    public void toMapTest() {
        Publisher tester = new Publisher(name, email, password, building);
        Map<String, Object> tester_map = tester.toMap();
        assertEquals(tester_map.get("name"), tester.name);
        assertEquals(tester_map.get("email"), tester.email);
        assertEquals(tester_map.get("password"), tester.password);
        assertEquals(tester_map.get("building"), tester.building);
    }
}