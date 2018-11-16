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
public class FirstStartScenario extends BaseScenario<SampleGivenState, SampleWhenState, SampleThenState> {

    @Test
    @Story("first-start")
    public void open_app_first_time_success() {
        given().has_internet_connection()
                .and().network_source_emit_$_data("ArchitectureComponents")
                .and().put_to_shared_preferences_whit_key_$_and_value_$_succeed(PreferenceKeys.SAMPLE_DATA_KEY,
                "ArchitectureComponents")
                .and().get_from_shared_preferences_with_key_$_return_$(PreferenceKeys.SAMPLE_DATA_KEY,
                "ArchitectureComponents");

        when().activity_launch();

        then().user_sees_text_$("ArchitectureComponents")
                .and().user_sees_text_$("Hello World!")
                .and().user_sees_text_$("Поиск контакта")
                .and().user_sees_text_$("Топики GitHub Ruby")
                .and().user_sees_text_$("MVI");
    }
}
