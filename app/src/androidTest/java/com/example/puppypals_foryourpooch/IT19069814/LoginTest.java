package com.example.puppypals_foryourpooch.IT19069814;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.puppypals_foryourpooch.Login;
import com.example.puppypals_foryourpooch.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {

    private String username;
    private String password;

    @Rule
    public ActivityScenarioRule<Login> loginRule
            = new ActivityScenarioRule<>(Login.class);

    @Before
    public void setUp() throws Exception {
        username = "dummyUser@gmail.com";
        password = "dumdum1";
    }

    @Test
    public void test_isActivityInView() {

        onView(withId(R.id.loginBg)).check(matches(isDisplayed()));
        onView(withId(R.id.login_title)).check(matches(isDisplayed()));
        onView(withId(R.id.login_logo)).check(matches(isDisplayed()));
        onView(withId(R.id.login_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.login_username)).check(matches(isDisplayed()));
        onView(withId(R.id.login_password)).check(matches(isDisplayed()));
        onView(withId(R.id.new_here)).check(matches(isDisplayed()));
        onView(withId(R.id.signup_btn)).check(matches(isDisplayed()));

    }

    @Test
    public void test_loginWithCredentials(){

        onView(withId(R.id.login_username))
                .perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.login_password))
                .perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.login_btn)).perform(click());
        onView(withId(R.id.uProf_bg));
    }

    @After
    public void tearDown() throws Exception {
        username = null;
        password = null;
    }
}