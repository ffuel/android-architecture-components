package com.a65apps.architecturecomponents.sample.presentation.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.domain.sample.SampleState;
import com.a65apps.architecturecomponents.presentation.mapper.StateToParcelableMapper;

import javax.inject.Inject;

public class SampleStateMapper extends StateToParcelableMapper<SampleState, SampleParcelable> {

    @Inject
    public SampleStateMapper() {
        super();
    }

    @NonNull
    @Override
    public SampleParcelable map(@NonNull SampleState sampleState) {
        return SampleParcelable.create(sampleState.state(), sampleState.text(), sampleState.data(),
                sampleState.error());
    }
}
