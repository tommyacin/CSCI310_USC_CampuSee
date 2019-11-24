package com.example.campusee;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class PublisherPageOfEventsTest {

    @Rule public ActivityTestRule<publisher_page_of_events> mPublisherPageOfEventsTestRule =
            new ActivityTestRule<>(publisher_page_of_events.class, true, true);


    @Test
    public void checkSubscribesToPublisher(){
        // click subscribe to publisher
        //
    }

}
