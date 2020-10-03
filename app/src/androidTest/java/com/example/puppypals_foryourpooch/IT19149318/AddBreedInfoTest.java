package com.example.puppypals_foryourpooch.IT19149318;



import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.puppypals_foryourpooch.AddBreedInfo;
import com.example.puppypals_foryourpooch.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddBreedInfoTest {

    @Rule
    public ActivityTestRule<AddBreedInfo> mActivityTestRule = new ActivityTestRule<>(AddBreedInfo.class);

 @Test
public void addBreedInfoTest() {
     //This will test weather the correct toast message displays.
        onView(ViewMatchers.withText(R.string.toast_breed_add))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

}
