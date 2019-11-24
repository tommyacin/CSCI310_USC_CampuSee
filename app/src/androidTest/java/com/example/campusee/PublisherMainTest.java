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
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class PublisherMainTest {
    @Rule
    public IntentsTestRule<MainActivity> mActivity = new IntentsTestRule<MainActivity>(MainActivity.class);


    public void stepThroughMainFlow(){
        onView(withId(R.id.publisher_button)).perform(click());
        onView(withId(R.id.publisher_signup_name))
                .perform(typeText("Ginger Dudley"), closeSoftKeyboard()); //type email and hide keyboard
        onView(withId(R.id.publisher_signup_email))
                .perform(typeText("ginger@gmail.com"), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.publisher_signup_password))
                .perform(typeText("ginger"), closeSoftKeyboard());//type password and hide keyboard*/
        onView(withId(R.id.publisher_signup_button)).perform(click()); //perform click
    }

    @Test
    public void checkPublisherCreateEventButton(){
        stepThroughMainFlow();
        onView(withId(R.id.create_button)).perform(click()); //perform click
        intended(hasComponent(CreateEvent.class.getName()));
    }
}