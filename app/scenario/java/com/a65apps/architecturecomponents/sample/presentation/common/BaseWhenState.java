package com.a65apps.architecturecomponents.sample.presentation.common;

import com.a65apps.architecturecomponents.sample.presentation.main.MainActivity;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import org.junit.Rule;

import androidx.annotation.NonNull;
import androidx.test.rule.ActivityTestRule;

public class BaseWhenState<T extends BaseWhenState<T>> extends Stage<T> {

    @Rule
    @ExpectedScenarioState(required = true)
    public ActivityTestRule<MainActivity> activityRule;

    @NonNull
    public T activity_launch() {
        activityRule.launchActivity(null);
        return self();
    }
}
