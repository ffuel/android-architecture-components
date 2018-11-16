package com.a65apps.architecturecomponents.sample.presentation.sample.start;

import com.a65apps.architecturecomponents.sample.data.sample.PreferenceKeys;
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
public class FirstStartNoInternetHasCachedDataScenario
        extends BaseScenario<SampleGivenState, SampleWhenState, SampleThenState> {

    @Test
    @Story("first-start")
    public void user_should_see_data_if_has_cached_data() {
        given().no_internet_connection()
                .get_from_shared_preferences_with_key_$_return_$(PreferenceKeys.SAMPLE_DATA_KEY,
                        "ArchitectureComponents");

        when().activity_launch();

        then().user_sees_text_$("Проверьте подключение к интернету")
                .and().user_sees_text_$("ArchitectureComponents");
    }
}
