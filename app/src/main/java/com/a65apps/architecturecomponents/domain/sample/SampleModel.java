package com.a65apps.architecturecomponents.domain.sample;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.model.BaseModel;

import javax.inject.Inject;

public final class SampleModel extends BaseModel<SampleState> implements SampleInteractor {

    @Inject
    public SampleModel(@NonNull SampleSource source) {
        setState(SampleState.create(source.text()));
    }
}
