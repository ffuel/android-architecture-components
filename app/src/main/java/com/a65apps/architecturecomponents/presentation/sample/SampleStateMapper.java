package com.a65apps.architecturecomponents.presentation.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.sample.SampleState;
import com.a65aps.architecturecomponents.presentation.mapper.StateToParcelableMapper;

import javax.inject.Inject;

public class SampleStateMapper extends StateToParcelableMapper<SampleState, SampleParcelable> {

    @Inject
    public SampleStateMapper() {
        super();
    }

    @NonNull
    @Override
    public SampleParcelable map(@NonNull SampleState mainState) {
        return SampleParcelable.create(mainState.text());
    }
}
