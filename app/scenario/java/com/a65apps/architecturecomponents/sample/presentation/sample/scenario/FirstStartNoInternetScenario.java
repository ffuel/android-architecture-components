package com.a65apps.architecturecomponents.sample.presentation.sample.scenario;

import com.a65apps.architecturecomponents.sample.data.sample.PreferenceKeys;
import com.a65apps.architecturecomponents.sample.presentation.common.BaseScenario;
import com.a65apps.architecturecomponents.sample.presentation.common.Story;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FirstStartNoInternetScenario extends BaseScenario<SampleGivenState, SampleWhenState, SampleThenState> {

    @Test
    @Story("first-start")
    public void user_should_see_no_internet_error() {
        given().no_internet_connection()
                .and().get_from_shared_preferences_with_key_$_return_$(PreferenceKeys.SAMPLE_DATA_KEY, "");

        when().activity_launch();

        then().user_sees_text_$("Проверьте подключение к интернету");
    }
}
