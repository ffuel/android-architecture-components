package com.a65apps.architecturecomponents.sample.presentation.common;

import com.a65apps.architecturecomponents.sample.AndroidJGivenTestRule;
import com.tngtech.jgiven.junit.JGivenClassRule;
import com.tngtech.jgiven.junit.ScenarioTest;

import org.junit.Rule;

public class BaseScenario<GIVEN extends BaseGivenState<GIVEN>,
        WHEN extends BaseWhenState<WHEN>,
        THEN extends BaseThenState<THEN>> extends ScenarioTest<GIVEN, WHEN, THEN> {

    @Rule
    public final JGivenClassRule writerRule = new JGivenClassRule();
    @Rule
    public final AndroidJGivenTestRule androidJGivenTestRule = new AndroidJGivenTestRule(this.getScenario());
}
