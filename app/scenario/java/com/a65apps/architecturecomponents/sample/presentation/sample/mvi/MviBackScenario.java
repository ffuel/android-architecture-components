package com.a65apps.architecturecomponents.sample.presentation.sample.mvi;

import com.a65apps.architecturecomponents.sample.presentation.common.BaseScenario;
import com.a65apps.architecturecomponents.sample.presentation.common.Story;
import com.a65apps.architecturecomponents.sample.presentation.sample.SampleGivenState;
import com.a65apps.architecturecomponents.sample.presentation.sample.SampleThenState;
import com.a65apps.architecturecomponents.sample.presentation.sample.SampleWhenState;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MviBackScenario extends BaseScenario<SampleGivenState, SampleWhenState, SampleThenState> {

    @Test
    @Story("mvi")
    public void click_back_on_mvi_screen_should_return_to_main_screen() {
        given().first_start_with_success();

        when().user_click_mvi()
                .and().user_press_back_button();

        then().user_sees_text_$("ArchitectureComponents");
    }
}
