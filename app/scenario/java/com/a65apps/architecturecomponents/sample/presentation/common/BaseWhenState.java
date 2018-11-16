package com.a65apps.architecturecomponents.sample.presentation.common;

import com.a65apps.architecturecomponents.sample.presentation.main.MainActivity;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import org.junit.Rule;

import androidx.annotation.NonNull;
import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class BaseWhenState<T extends BaseWhenState<T>> extends Stage<T> {

    @Rule
    @ExpectedScenarioState(required = true)
    public ActivityTestRule<MainActivity> activityRule;

    @NonNull
    public T activity_launch() {
        activityRule.launchActivity(null);
        return self();
    }

    @NonNull
    public T user_click_on_$(@NonNull @Quoted String text) {
        onView(withText(text)).perform(click());
        return self();
    }

    @NonNull
    public T user_press_back_button() {
        Espresso.pressBack();
        return self();
    }
}
