package com.example.campusee;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.action.TypeTextAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class PublisherPageOfEventsTest {
    // Need to make sure this user starts off not subscribed


    @Rule public ActivityTestRule<MainActivity> mainActivityTest =
            new ActivityTestRule<>(MainActivity.class, true, true);

    protected void setupPath() {
        onView(withId(R.id.student_button)).perform(click());
        onView(withId(R.id.user_signup_name))
                .perform(typeText("Glory Kanes"), closeSoftKeyboard()); //type email and hide keyboard
        onView(withId(R.id.user_signup_email))
                .perform(typeText("glorykanes@email.com"), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.user_signup_password))
                .perform(typeText("password1"), closeSoftKeyboard());//type password and hide keyboard*/
        onView(withId(R.id.user_signup_button)).perform(click()); //perform click
        onView(withId(R.id.rvPublishers)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void checkSubscribesToPublisher(){
        setupPath();

        // click subscribe to publisher
        onView(withId(R.id.subscribe_btn)).perform(click()); //perform click
        onView(withId(R.id.subscribe_btn))
                .check(matches(withText("Unsubscribe")));
    }

    @Test
    public void checkUnsubscribeToPublisher() {
        setupPath();
        onView(withId(R.id.subscribe_btn))
                .check(matches(withText("Unsubscribe")));
        onView(withId(R.id.subscribe_btn)).perform(click()); //perform click
        onView(withId(R.id.subscribe_btn))
                .check(matches(withText("Subscribe")));
    }

}
