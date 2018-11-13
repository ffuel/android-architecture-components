package com.a65apps.architecturecomponents.sample.presentation.common;

import com.a65apps.architecturecomponents.sample.TestsApp;
import com.tngtech.jgiven.junit.JGivenClassRule;
import com.tngtech.jgiven.junit.ScenarioTest;

import org.junit.Rule;
import org.robolectric.annotation.Config;

@Config(application = TestsApp.class)
public class BaseScenario<GIVEN extends BaseGivenState<GIVEN>,
        WHEN extends BaseWhenState<WHEN>,
        THEN extends BaseThenState<THEN>> extends ScenarioTest<GIVEN, WHEN, THEN> {

    @Rule
    public final JGivenClassRule writerRule = new JGivenClassRule();
}
