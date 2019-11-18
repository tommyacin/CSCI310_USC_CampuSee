package com.example.campusee;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    private String name, email, password;

    @Before
    public void setUp() {
        name = "Tommy";
        email = "hello@usc.edu";
        password = "thisismypassword";
    }

    @Test
    public void userConstructorTest() {
        User tester = new User(name, email, password);
        assertEquals("Tommy", tester.name);
        assertEquals("hello@usc.edu", tester.email);
        assertEquals("thisismypassword", tester.password);
    }
}