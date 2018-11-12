package com.a65apps.architecturecomponents.sample.presentation.sample.scenario;

import com.a65apps.architecturecomponents.sample.TestsApp;
import com.a65apps.architecturecomponents.sample.presentation.common.BaseScenario;
import com.a65apps.architecturecomponents.sample.presentation.common.Story;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

@RunWith(AndroidJUnit4.class)
@Config(application = TestsApp.class)
@LargeTest
public class FirstStartLoadingScenario extends BaseScenario<SampleGivenState, SampleWhenState, SampleThenState> {

    @Test
    @Story("first-start")
    public void user_should_see_loading_indicator_at_first_start() {
        given().has_internet_connection()
                .and().network_source_no_emit_data();

        when().activity_launch();

        then().user_should_see_loading();
    }
}
