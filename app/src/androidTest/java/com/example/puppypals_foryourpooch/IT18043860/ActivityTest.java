package com.example.puppypals_foryourpooch.IT18043860;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import com.example.puppypals_foryourpooch.R;
import com.example.puppypals_foryourpooch.UserChat;
import com.example.puppypals_foryourpooch.ViewProfile;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class ActivityTest {

    @Rule
    public ActivityTestRule<ViewProfile> viewProfileActivityTestRule =new ActivityTestRule(ViewProfile.class);

    private ViewProfile proview=null;

    Instrumentation.ActivityMonitor monitor=getInstrumentation().addMonitor(UserChat.class.getName(),null,false);

    @Before
    public void setUp() throws Exception{

        proview=viewProfileActivityTestRule.getActivity();
    }
    @Test
    public void testActivityLauncher(){
        assertNotNull(proview.findViewById(R.id.viewProf_btn_chat));
        onView(withId(R.id.viewProf_btn_chat)).perform(click());
        Activity UserChat=getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(UserChat);
        UserChat.finish();
    }
    @After
    public void tearDown() throws Exception{
        proview=null;
    }
}
