package com.example.campusee;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class CreateEventTest {

    private DatabaseReference mDatabase;

    @Rule
    public IntentsTestRule<MainActivity> createEventActivityTestRule =
            new IntentsTestRule<>(MainActivity.class, true, true);

    @Test
    public void createEventTest() {
        String eventTitle = "Test Event";
        String eventDes = "Testing event";
        String eventRadius = "2";

        onView(withId(R.id.publisher_button)).perform(click());


        onView(withId(R.id.publisher_signup_name))
                .perform(typeText("Ginger Dudley"), closeSoftKeyboard()); //type email and hide keyboard
        onView(withId(R.id.publisher_signup_email))
                .perform(typeText("ginger@gmail.com"), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.publisher_signup_password))
                .perform(typeText("ginger"), closeSoftKeyboard());//type password and hide keyboard*/
        onView(withId(R.id.publisher_signup_button)).perform(click()); //perform click
        onView(withId(R.id.create_button)).perform(click()); //perform click


        onView(withId(R.id.create_event_name))
                .perform(typeText(eventTitle), closeSoftKeyboard());
        onView(withId(R.id.create_radius)).perform(typeText(eventRadius), closeSoftKeyboard());
        onView(withId(R.id.create_description)).perform(typeText(eventDes), closeSoftKeyboard());
        onView(withId(R.id.create_next_button)).perform(click()); //perform click

        intended(allOf(
                hasComponent(IconPage.class.getName()),
                hasExtra("EVENT_NAME", eventTitle),
                hasExtra("EVENT_RADIUS", eventRadius),
                hasExtra("EVENT_DESCRIPTION",  eventDes)
        ));
    }

    @Test
    public void testAddNotificationToDatabase(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        onView(withId(R.id.publisher_button)).perform(click());


        onView(withId(R.id.publisher_signup_name))
                .perform(typeText("Ginger Dudley"), closeSoftKeyboard()); //type email and hide keyboard
        onView(withId(R.id.publisher_signup_email))
                .perform(typeText("ginger@gmail.com"), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.publisher_signup_password))
                .perform(typeText("ginger"), closeSoftKeyboard());//type password and hide keyboard*/
        onView(withId(R.id.publisher_signup_button)).perform(click()); //perform click
        onView(withId(R.id.create_button)).perform(click()); //perform click

        onView(withId(R.id.create_event_name))
                .perform(typeText("Event Name"), closeSoftKeyboard()); //type email and hide keyboard
        onView(withId(R.id.create_radius))
                .perform(typeText("150"), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.create_description))
                .perform(typeText("Event Desc"), closeSoftKeyboard());//type password and hide keyboard*/
        onView(withId(R.id.create_next_button)).perform(click()); //perform click
        onView(withId(R.id.icon1)).perform(click()); //perform click
        onView(withId(R.id.publish_button)).perform(click()); //perform click

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.hasChild("notifications")) {

                    int div = 5/0;
                    // run some code
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
