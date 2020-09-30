package com.example.puppypals_foryourpooch;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.example.puppypals_foryourpooch.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddBreedInfoTest {

    @Rule
    public ActivityTestRule<AddBreedInfo> mActivityTestRule = new ActivityTestRule<>(AddBreedInfo.class);







 @Test
public void addBreedInfoTest() {

        onView(withText(R.string.toast_breed_add))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

//        ViewInteraction appCompatEditText = onView(
//                allOf(withId(R.id.breedName),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                0)));
//        appCompatEditText.perform(scrollTo(), replaceText("Drever"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText2 = onView(
//                allOf(withId(R.id.hno),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.LinearLayout")),
//                                        1),
//                                0)));
//        appCompatEditText2.perform(scrollTo(), replaceText("3"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText3 = onView(
//                allOf(withId(R.id.wno),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.LinearLayout")),
//                                        1),
//                                1)));
//        appCompatEditText3.perform(scrollTo(), replaceText("9"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText4 = onView(
//                allOf(withId(R.id.lsno),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.LinearLayout")),
//                                        1),
//                                2)));
//        appCompatEditText4.perform(scrollTo(), replaceText("12"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText5 = onView(
//                allOf(withId(R.id.adapget),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                2)));
//        appCompatEditText5.perform(scrollTo(), replaceText("aaa"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText6 = onView(
//                allOf(withId(R.id.intelget),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                3)));
//        appCompatEditText6.perform(scrollTo(), replaceText("bbb"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText7 = onView(
//                allOf(withId(R.id.feedget),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                4)));
//        appCompatEditText7.perform(scrollTo(), replaceText("ccc"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText8 = onView(
//                allOf(withId(R.id.healget),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                5)));
//        appCompatEditText8.perform(scrollTo(), replaceText("ddd"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText9 = onView(
//                allOf(withId(R.id.infoLink),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                6)));
//        appCompatEditText9.perform(scrollTo(), replaceText("eee"), closeSoftKeyboard());
//
//        ViewInteraction appCompatImageButton = onView(
//                allOf(withId(R.id.addImgBtn),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.LinearLayout")),
//                                        7),
//                                1)));
//        appCompatImageButton.perform(scrollTo(), click());
//
//        ViewInteraction appCompatImageButton2 = onView(
//                allOf(withId(R.id.addImgBtn),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.LinearLayout")),
//                                        7),
//                                1)));
//        appCompatImageButton2.perform(scrollTo(), click());
//
//        ViewInteraction appCompatButton = onView(
//                allOf(withId(R.id.saveBreed), withText("Save"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.LinearLayout")),
//                                        8),
//                                1)));
//        appCompatButton.perform(scrollTo(), click());
//
//        ViewInteraction appCompatImageButton3 = onView(
//                allOf(withId(R.id.manageBreed),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.LinearLayout")),
//                                        1),
//                                3),
//                        isDisplayed()));
//        appCompatImageButton3.perform(click());
//
//        ViewInteraction appCompatTextView = onView(
//                allOf(withId(R.id.breedname), withText("Drever"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.removeBreedInfo),
//                                        0),
//                                1),
//                        isDisplayed()));
//        appCompatTextView.perform(click());
//
//        ViewInteraction textView = onView(
//                allOf(withId(R.id.cusBreednm), withText("Drever"),
//                        childAtPosition(
//                                childAtPosition(
//                                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
//                                        0),
//                                0),
//                        isDisplayed()));
//        textView.check(matches(withText("Drever")));
//    }
//
//    private static Matcher<View> childAtPosition(
//            final Matcher<View> parentMatcher, final int position) {
//
//        return new TypeSafeMatcher<View>() {
//            @Override
//            public void describeTo(Description description) {
//                description.appendText("Child at position " + position + " in parent ");
//                parentMatcher.describeTo(description);
//            }
//
//            @Override
//            public boolean matchesSafely(View view) {
//                ViewParent parent = view.getParent();
//                return parent instanceof ViewGroup && parentMatcher.matches(parent)
//                        && view.equals(((ViewGroup) parent).getChildAt(position));
//            }
//        };
   }
}
