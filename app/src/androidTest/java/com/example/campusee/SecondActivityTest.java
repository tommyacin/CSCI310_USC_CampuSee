package com.example.campusee;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
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
import androidx.test.ext.junit.runners.AndroidJUnit4;

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
    @Before
    public void before(){
        //initial setup code
    }

    @Rule
    //public ActivityTestRule<MainActivity> mActivityTestRule = new  ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkButtonClick(){
        onView(withId(R.id.user_signup_button)).perform(click());
    }

    @After
    public void after(){
        //clean up code
    }
    @Test
    public void userSignupOrLoginTest(){

    }

}