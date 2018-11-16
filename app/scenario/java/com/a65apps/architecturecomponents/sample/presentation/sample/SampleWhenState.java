package com.a65apps.architecturecomponents.sample.presentation.sample;

import com.a65apps.architecturecomponents.sample.presentation.common.BaseWhenState;

import androidx.annotation.NonNull;

public class SampleWhenState extends BaseWhenState<SampleWhenState> {

    @NonNull
    public SampleWhenState user_click_mvi() {
        return activity_launch()
                .user_click_on_$("MVI");
    }

    @NonNull
    public SampleWhenState user_click_contacts() {
        return activity_launch()
                .user_click_on_$("Поиск контакта");
    }

    @NonNull
    public SampleWhenState user_click_posts() {
        return activity_launch()
                .user_click_on_$("Топики GitHub Ruby");
    }
}
