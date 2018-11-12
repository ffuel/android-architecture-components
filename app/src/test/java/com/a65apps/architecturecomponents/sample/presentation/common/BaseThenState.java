package com.a65apps.architecturecomponents.sample.presentation.common;

import com.a65apps.architecturecomponents.sample.presentation.main.MainActivity;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import org.junit.Rule;

import androidx.annotation.NonNull;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class BaseThenState<T extends BaseThenState<T>> extends Stage<T> {

    @Rule
    @ExpectedScenarioState(required = true)
    public ActivityTestRule<MainActivity> activityRule;

    @NonNull
    public T user_sees_text_$(@NonNull @Quoted String text) {
        onView(withText(text)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        return self();
    }
}
