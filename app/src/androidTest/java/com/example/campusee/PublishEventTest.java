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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class PublishEventTest {
    @Rule
    public IntentsTestRule<MainActivity> mActivity = new IntentsTestRule<MainActivity>(MainActivity.class);

    public void stepThroughMainToPublishEventActivity(){
        onView(withId(R.id.publisher_button)).perform(click());
        onView(withId(R.id.publisher_signup_name))
                .perform(typeText("Ginger Dudley"), closeSoftKeyboard()); //type email and hide keyboard
        onView(withId(R.id.publisher_signup_email))
                .perform(typeText("ginger@gmail.com"), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.publisher_signup_password))
                .perform(typeText("ginger"), closeSoftKeyboard());//type password and hide keyboard*/
        onView(withId(R.id.publisher_signup_button)).perform(click()); //perform click
        onView(withId(R.id.create_button)).perform(click());
        onView(withId(R.id.create_event_name))
                .perform(typeText("Ginger's sick event"), closeSoftKeyboard()); //type email and hide keyboard
        onView(withId(R.id.create_description))
                .perform(typeText("this is my event"), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.create_radius))
                .perform(typeText("4"), closeSoftKeyboard());//type password and hide keyboard*/
        onView(withId(R.id.create_next_button)).perform(click());
        onView(withId(R.id.icon4)).perform(click());
    }

    @Test
    public void testPublishTextFields(){
        stepThroughMainToPublishEventActivity();
        onView(withId(R.id.publish_event_name)).check(matches(withText("Ginger's sick event")));
        onView(withId(R.id.publish_description)).check(matches(withText("this is my event")));
        onView(withId(R.id.publish_radius)).check(matches(withText("4")));
    }

    @Test
    public void testPublishButtonNextActivity(){
        stepThroughMainToPublishEventActivity();
        onView(withId(R.id.publish_button));
        intended(hasComponent(PublisherMain.class.getName()));
    }

    @Test
    public void testMakeChangesButton(){
        stepThroughMainToPublishEventActivity();
        onView(withId(R.id.edit_button));
        intended(hasComponent(CreateEvent.class.getName()));
    }

}