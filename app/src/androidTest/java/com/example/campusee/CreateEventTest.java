package com.example.campusee;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class CreateEventTest {
    @Rule
    public IntentsTestRule<CreateEvent> createEventActivityTestRule =
            new IntentsTestRule<>(CreateEvent.class, true, true);


    @Test
    public void createEventTest() {
        String eventTitle = "Test Event";
        String eventDes = "Testing event";
        String eventRadius = "2";


        onView(withId(R.id.create_event_name))
                .perform(typeText(eventTitle), closeSoftKeyboard());
        onView(withId(R.id.create_radius)).perform(typeText(eventRadius), closeSoftKeyboard());
        onView(withId(R.id.create_description)).perform(typeText(eventDes), closeSoftKeyboard());
        onView(withId(R.id.create_next_button)).perform(click()); //perform click

        intended(allOf(
                hasComponent(IconPage.class.getName()),
                hasExtra("EVENT_NAME", eventTitle),
                hasExtra("EVENT_RADIUS", eventRadius),
                hasExtra("EVENT_DESCRIPTION",  eventDes)
        ));
    }

}
