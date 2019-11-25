package com.example.campusee;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.VerificationModes.times;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class NotificationPageTest {
    @Rule
    public IntentsTestRule<MainActivity> mActivity = new IntentsTestRule<MainActivity>(MainActivity.class);

    public void mainThroughStudentHome(){
        onView(withId(R.id.student_button)).perform(click());
        onView(withId(R.id.user_signup_name))
                .perform(typeText("Glory Kanes"), closeSoftKeyboard()); //type email and hide keyboard
        onView(withId(R.id.user_signup_email))
                .perform(typeText("glorykanes@email.com"), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.user_signup_password))
                .perform(typeText("password1"), closeSoftKeyboard());//type password and hide keyboard*/
        onView(withId(R.id.user_login_button)).perform(click()); //perform click
        onView(withId(R.id.notificationToolbarButton)).perform(click());
    }

    @Test
    public void checkNotificationPublishersButton(){
        mainThroughStudentHome();
        onView(withId(R.id.publishersToolbarButton)).perform(click());
        intended(hasComponent(StudentHome.class.getName()), times(2));
    }

    @Test
    public void checkNotificationMapButton(){
        mainThroughStudentHome();
        onView(withId(R.id.mapToolbarButton)).perform(click());
        intended(hasComponent(activity_map.class.getName()));
    }

    @Test
    public void checkNotificationNoticationButton(){
        mainThroughStudentHome();
        onView(withId(R.id.notificationToolbarButton)).perform(click());
        intended(hasComponent(NotificationPage.class.getName()));
    }

    @Test
    public void checkClickNotification(){
        onView(withId(R.id.student_button)).perform(click());

        onView(withId(R.id.user_signup_name))
                .perform(typeText("Glory Kanes"), closeSoftKeyboard()); //type email and hide keyboard
        onView(withId(R.id.user_signup_email))
                .perform(typeText("glorykanes@email.com"), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.user_signup_password))
                .perform(typeText("password1"), closeSoftKeyboard());//type password and hide keyboard*/
        onView(withId(R.id.user_signup_button)).perform(click());
        onView(withId(R.id.notificationToolbarButton)).perform(click());

    }

}