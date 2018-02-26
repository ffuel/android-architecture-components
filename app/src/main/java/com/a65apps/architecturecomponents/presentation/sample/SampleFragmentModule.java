package com.a65apps.architecturecomponents.presentation.sample;

import com.a65apps.architecturecomponents.domain.sample.SampleState;
import com.a65aps.architecturecomponents.presentation.component.ViewModule;

import dagger.Module;

@Module
public interface SampleFragmentModule extends ViewModule<SampleState, SampleParcelable,
        SampleStateMapper, SampleParcelMapper> {
}
