package com.example.campusee;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

//import static androidx.test.espresso.intent.Intents.intended;
//import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.Espresso.withId;

@RunWith(AndroidJUnit4.class)
public class SecondActivityTest {
    /*@Before
    public void before(){
        Intents.init();
    }*/

    @Rule
    /*public ActivityTestRule<SecondActivity> menuActivityTestRule =
            new ActivityTestRule<>(SecondActivity.class, true, true);*/
    public IntentsTestRule<SecondActivity> mActivity = new IntentsTestRule<SecondActivity>(SecondActivity.class);


    @Test
    public void checkUserSignupButton(){
        onView(withId(R.id.user_signup_button)).perform(click());
    }


    @Test
    public void checkUserSignupNoPassword(){
        onView(withId(R.id.user_signup_name))
                .perform(typeText("Glory Kanes"), closeSoftKeyboard()); //type email and hide keyboard
        onView(withId(R.id.user_signup_email))
                .perform(typeText("glorykanes@email.com"), closeSoftKeyboard());//type password and hide keyboard

        onView(withId(R.id.user_signup_button)).perform(click()); //perform click
        onView(withId(R.id.user_signup_password)).check(matches(withHint("Please input a password")));
    }

    @Test
    public void checkUserSignupNoName(){
        onView(withId(R.id.user_signup_email))
                .perform(typeText("glorykanes@email.com"), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.user_signup_password))
                .perform(typeText("password1"), closeSoftKeyboard());//type password and hide keyboard*/

        onView(withId(R.id.user_signup_button)).perform(click()); //perform click
        onView(withId(R.id.user_signup_name)).check(matches(withHint("Please input a name")));
    }

    @Test
    public void checkUserSignupNoEmail(){
        onView(withId(R.id.user_signup_name))
                .perform(typeText("Glory Kanes"), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.user_signup_password))
                .perform(typeText("password1"), closeSoftKeyboard());//type password and hide keyboard*/
        onView(withId(R.id.user_signup_button)).perform(click()); //perform click
        onView(withId(R.id.user_signup_email)).check(matches(withHint("Please input an email")));
    }

    @Test
    public void checkUserSignupAllFieldsEmpty(){
        onView(withId(R.id.user_signup_button)).perform(click()); //perform click
        onView(withId(R.id.user_signup_email)).check(matches(withHint("Please input an email")));
        onView(withId(R.id.user_signup_name)).check(matches(withHint("Please input a name")));
        onView(withId(R.id.user_signup_password)).check(matches(withHint("Please input a password")));
    }

    @Test
    public void checkUserSignupNextAcivity(){
        onView(withId(R.id.user_signup_name))
                .perform(typeText("Glory Kanes"), closeSoftKeyboard()); //type email and hide keyboard
        onView(withId(R.id.user_signup_email))
                .perform(typeText("glorykanes@email.com"), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.user_signup_password))
                .perform(typeText("password1"), closeSoftKeyboard());//type password and hide keyboard*/
        onView(withId(R.id.user_signup_button)).perform(click()); //perform click
        intended(hasComponent(StudentHome.class.getName()));
    }



   /* @After
    public void after(){
        //clean up code
    }
    @Test
    public void userSignupOrLoginTest(){

    }*/

}