package com.example.puppypals_foryourpooch.IT19241906;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;


import com.example.puppypals_foryourpooch.pup_add_page;
import com.example.puppypals_foryourpooch.pup_register;
import com.example.puppypals_foryourpooch.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;


public class TestForButtonTransition {

    @Rule
    public ActivityTestRule<pup_add_page> submitToast =new ActivityTestRule(pup_add_page.class);

    private pup_add_page list=null;

    Instrumentation.ActivityMonitor monitor=getInstrumentation().addMonitor(pup_register.class.getName(),null,false);

    @Before
    public void setUp() throws Exception{

        list=submitToast.getActivity();
    }
    @Test
    public void testActivityLauncher(){
        assertNotNull(list.findViewById(R.id.newadd));
        onView(withId(R.id.newadd)).perform(click());
        Activity createShoppingList=getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(createShoppingList);
        createShoppingList.finish();
    }
    @After
    public void tearDown() throws Exception{
        list=null;
    }

}
