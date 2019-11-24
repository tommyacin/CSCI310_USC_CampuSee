package com.example.campusee;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class PublishEventTest {
    @Rule
    public ActivityTestRule<PublishEvent> publishEventActivityTestRule =
            new ActivityTestRule<>(PublishEvent.class, true, true);

    @Test
    public void createEventTest() {
        onView(withId(R.id.publish_event_name))
                .perform(typeText("Ginger Dudley"), closeSoftKeyboard()); //type email and hide keyboard
        onView(withId(R.id.publisher_signup_email))
                .perform(typeText("ginger@gmail.com"), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.publisher_signup_password))
                .perform(typeText("ginger"), closeSoftKeyboard());//type password and hide keyboard*/
        onView(withId(R.id.publisher_signup_button)).perform(click()); //perform click
    }
}
