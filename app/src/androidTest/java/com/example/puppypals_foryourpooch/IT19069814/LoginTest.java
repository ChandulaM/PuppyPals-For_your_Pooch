package com.example.puppypals_foryourpooch.IT19069814;

import android.view.View;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.puppypals_foryourpooch.Login;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginTest {
    @Rule
    public ActivityScenarioRule rule
            = new ActivityScenarioRule<>(Login.class);
    private ActivityScenario scenario = null;

    @Before
    public void setUp() throws Exception {
        scenario = rule.getScenario();
    }

    @Test
    public void testLaunch(){
        assertTrue(scenario.getState().isAtLeast(Lifecycle.State.CREATED));
    }

    @After
    public void tearDown() throws Exception {
    }
}