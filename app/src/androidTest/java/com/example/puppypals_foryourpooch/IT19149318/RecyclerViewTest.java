package com.example.puppypals_foryourpooch.IT19149318;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.puppypals_foryourpooch.BreedAdapter;
import com.example.puppypals_foryourpooch.Manage_breed_info;
import com.example.puppypals_foryourpooch.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecyclerViewTest {
    @Rule
    public ActivityTestRule<Manage_breed_info> mActivityTestRule = new ActivityTestRule<>(Manage_breed_info.class);

    @Test
    public void recyclerTest(){
        RecyclerView recyclerView = mActivityTestRule.getActivity().findViewById(R.id.brdRecycle);
        int itemCount = 15;
        Espresso.onView(withId(R.id.brdRecycle)).perform(RecyclerViewActions.scrollToPosition(itemCount-1));

    }
}
