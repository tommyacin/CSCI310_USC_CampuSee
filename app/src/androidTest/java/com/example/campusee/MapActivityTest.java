package com.example.campusee;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.Espresso.onView;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import java.util.List;

import static androidx.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
public class MapActivityTest {
    @Rule
    public IntentsTestRule<MainActivity> mActivity = new IntentsTestRule<MainActivity>(MainActivity.class);

    // for some reason this test never works the first run? second and after..
    @Test
    public void checkMapDisplays(){
        onView(withId(R.id.student_button)).perform(click());
        onView(withId(R.id.user_signup_name))
                .perform(typeText("Glory Kanes"), closeSoftKeyboard()); //type email and hide keyboard
        onView(withId(R.id.user_signup_email))
                .perform(typeText("glorykanes@email.com"), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.user_signup_password))
                .perform(typeText("password1"), closeSoftKeyboard());//type password and hide keyboard*/
        onView(withId(R.id.user_signup_button)).perform(click()); //perform click
        onView(withId(R.id.mapToolbarButton)).perform(click());
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }

    @Test
    public void checkMapTrackers()
    {
        Global application = (Global)mActivity.getActivity().getApplicationContext();
        List<String> existingPublishers = application.getExistingPublishers();

        onView(withId(R.id.student_button)).perform(click());
        onView(withId(R.id.user_signup_name))
                .perform(typeText("Glory Kanes"), closeSoftKeyboard()); //type email and hide keyboard
        onView(withId(R.id.user_signup_email))
                .perform(typeText("glorykanes@email.com"), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.user_signup_password))
                .perform(typeText("password1"), closeSoftKeyboard());//type password and hide keyboard*/
        onView(withId(R.id.user_signup_button)).perform(click()); //perform click
        onView(withId(R.id.mapToolbarButton)).perform(click());
        onView(withId(R.id.map)).check(matches(isDisplayed()));

        assert(existingPublishers.size() > 0);
    }
}
