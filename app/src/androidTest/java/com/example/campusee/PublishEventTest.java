package com.example.campusee;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SdkSuppress;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class PublishEventTest {
    private UiDevice device;

    @Rule
    public ActivityTestRule<CreateEvent> createEventActivityTestRule =
            new ActivityTestRule<>(CreateEvent.class, true, true);



    @Test
    public void createEventTest() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        String eventTitle = "Test Event";
        String eventDate = "01/02/2019";
        String eventTime = "11:00";
        String eventDes = "Testing event";
        String eventRadius = "2";

        UiObject date_widget = device.findObject(new UiSelector().resourceId("create_datepicker"));

        onView(withId(R.id.create_event_name))
                .perform(typeText(eventTitle), closeSoftKeyboard()); //type email and hide keyboard
        onView(withId(R.id.publish_date))
                .perform(typeText(eventDate), closeSoftKeyboard());//type password and hide keyboard
        onView(withId(R.id.publish_time))
                .perform(typeText(eventTime), closeSoftKeyboard());//type password and hide keyboard*/
        onView(withId(R.id.create_radius)).perform(typeText(eventRadius), closeSoftKeyboard());
        onView(withId(R.id.create_description)).perform(typeText(eventDes), closeSoftKeyboard());

        onView(withId(R.id.publisher_signup_button)).perform(click()); //perform click
    }
}
