package com.example.campusee;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
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
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class PublisherSignupTest {

    @Before
    public void stepThroughMainFlow(){
        onView(withId(R.id.publisher_button)).perform(click());
    }


    @Rule
    public IntentsTestRule<MainActivity> mActivity = new IntentsTestRule<MainActivity>(MainActivity.class);


    @Test
    public void checkPublisherSignupButton(){
        onView(withId(R.id.publisher_signup_building)).perform(click());
    }

    @Test
    public void checkPublisherSignupNoPassword(){
        onView(withId(R.id.publisher_signup_name))
                .perform(typeText("Ginger Dudley"), closeSoftKeyboard()); //type email and hide keyboard
        onView(withId(R.id.publisher_signup_email))
                .perform(typeText("ginger@gmail.com"), closeSoftKeyboard());//type password and hide keyboard

        onView(withId(R.id.publisher_signup_button)).perform(click()); //perform click
        onView(withId(R.id.publisher_signup_password)).check(matches(withHint("Please input a password")));
    }

    @Test
    public void checkPublisherSignupNoName(){
        onView(withId(R.id.publisher_signup_email))
                .perform(typeText("ginger@gmail.com"), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.publisher_signup_password))
                .perform(typeText("ginger"), closeSoftKeyboard());//type password and hide keyboard*/

        onView(withId(R.id.publisher_signup_button)).perform(click()); //perform click
        onView(withId(R.id.publisher_signup_name)).check(matches(withHint("Please input a name")));
    }

    @Test
    public void checkPublisherSignupNoEmail(){
        onView(withId(R.id.publisher_signup_name))
                .perform(typeText("Ginger dudley"), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.publisher_signup_password))
                .perform(typeText("ginger"), closeSoftKeyboard());//type password and hide keyboard*/
        onView(withId(R.id.publisher_signup_button)).perform(click()); //perform click
        onView(withId(R.id.publisher_signup_email)).check(matches(withHint("Please input an email")));
    }

    @Test
    public void checkPublisherSignupAllFieldsEmpty(){
        onView(withId(R.id.publisher_signup_button)).perform(click()); //perform click
        onView(withId(R.id.publisher_signup_email)).check(matches(withHint("Please input an email")));
        onView(withId(R.id.publisher_signup_name)).check(matches(withHint("Please input a name")));
        onView(withId(R.id.publisher_signup_password)).check(matches(withHint("Please input a password")));
    }

    @Test
    public void checkPublisherNextAcivity(){
        onView(withId(R.id.publisher_signup_name))
                .perform(typeText("Ginger Dudley"), closeSoftKeyboard()); //type email and hide keyboard
        onView(withId(R.id.publisher_signup_email))
                .perform(typeText("ginger@gmail.com"), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.publisher_signup_password))
                .perform(typeText("ginger"), closeSoftKeyboard());//type password and hide keyboard*/
        onView(withId(R.id.publisher_signup_button)).perform(click()); //perform click
        intended(hasComponent(PublisherMain.class.getName()));
    }

}