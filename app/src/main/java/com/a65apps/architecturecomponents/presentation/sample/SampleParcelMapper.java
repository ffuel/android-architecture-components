package com.a65apps.architecturecomponents.presentation.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.sample.SampleState;
import com.a65aps.architecturecomponents.presentation.mapper.ParcelableToStateMapper;

import javax.inject.Inject;

public class SampleParcelMapper extends ParcelableToStateMapper<SampleParcelable, SampleState> {

    @Inject
    public SampleParcelMapper() {
        // @Inject constructor
    }

    @NonNull
    @Override
    public SampleState map(@NonNull SampleParcelable sampleParcelable) {
        return SampleState.create(sampleParcelable.state(), sampleParcelable.text(),
                sampleParcelable.data(), sampleParcelable.error());
    }
}
