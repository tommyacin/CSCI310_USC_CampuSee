package com.example.campusee;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class EditEventTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityTest =
            new ActivityTestRule<>(MainActivity.class, true, true);


    protected void setupPath() {
        onView(withId(R.id.publisher_button)).perform(click());
        onView(withId(R.id.publisher_signup_name))
                .perform(typeText("Ginger Dudley"), closeSoftKeyboard()); //type email and hide keyboard
        onView(withId(R.id.publisher_signup_email))
                .perform(typeText("ginger@gmail.com"), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.publisher_signup_password))
                .perform(typeText("ginger"), closeSoftKeyboard());//type password and hide keyboard*/
        onView(withId(R.id.publisher_signup_button)).perform(click()); //perform click
        onView(withId(R.id.rvEvents)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void unpublishEventTest() {
        setupPath();
        onView(withId(R.id.unpublish_button)).perform(click()); //perform click
        onView(withId(R.id.rvEvents)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.unpublish_button))
                .check(matches(withText("Unpublish")));
    }

    @Test
    public void publishEventTest() {
        setupPath();
        onView(withId(R.id.unpublish_button))
                .check(matches(withText("Unpublish")));
        onView(withId(R.id.unpublish_button)).perform(click()); //perform click
        onView(withId(R.id.unpublish_button))
                .check(matches(withText("Republish")));

    }

    @Test
    public void removeEventTest() {
        onView(withId(R.id.publisher_button)).perform(click());
        onView(withId(R.id.publisher_signup_name))
                .perform(typeText("Ginger Dudley"), closeSoftKeyboard()); //type email and hide keyboard
        onView(withId(R.id.publisher_signup_email))
                .perform(typeText("ginger@gmail.com"), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.publisher_signup_password))
                .perform(typeText("ginger"), closeSoftKeyboard());
        onView(withId(R.id.publisher_signup_button)).perform(click()); //perform click

        onView(withId(R.id.rvEvents)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.delete_button)).perform(click());

        // Verify that items count has changed
        onView(withId(R.id.rvEvents))
                .check(new RecyclerViewItemCountAssertion(not(3)));
    }
}
