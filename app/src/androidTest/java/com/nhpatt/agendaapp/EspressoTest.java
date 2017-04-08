package com.nhpatt.agendaapp;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void espressoTest() {
        ViewInteraction appCompatEditText = onView(childAtPosition(childAtPosition(withId(R.id.name_edit), 0), 0));
        appCompatEditText.perform(replaceText("Hola!"), closeSoftKeyboard());

        ViewInteraction submitButton = onView(withId(R.id.submit_button));
        submitButton.perform(click());
        submitButton.check(matches(isEnabled()));
    }

    @Test
    public void addTalk() {
        ViewInteraction appCompatEditText = onView(childAtPosition(childAtPosition(withId(R.id.name_edit), 0), 0));
        String stringToBeSet = "Hola!";
        appCompatEditText.perform(replaceText(stringToBeSet), closeSoftKeyboard());

        ViewInteraction submitButton = onView(withId(R.id.submit_button));
        submitButton.perform(click());

        onView(withText(stringToBeSet)).check(matches(isDisplayed()));
    }

    @Before
    public void registerIntentServiceIdlingResource() {
        Espresso.registerIdlingResources(activityTestRule.getActivity());
    }

    @After
    public void unregisterIntentServiceIdlingResource() {
        Espresso.unregisterIdlingResources(activityTestRule.getActivity());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
