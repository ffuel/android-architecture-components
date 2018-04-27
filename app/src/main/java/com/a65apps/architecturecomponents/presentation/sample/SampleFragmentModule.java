package com.a65apps.architecturecomponents.presentation.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.sample.SampleState;
import com.a65aps.architecturecomponents.di.ViewModule;
import com.a65aps.daggerarchitecturecomponents.view.DaggerViewModule;

import dagger.Module;
import dagger.Provides;

@Module
public class SampleFragmentModule extends DaggerViewModule<SampleState, SampleParcelable,
        SampleStateMapper, SampleParcelMapper> {

    @Provides
    @NonNull
    ViewModule<SampleState, SampleParcelable, SampleStateMapper,
            SampleParcelMapper> providesModule() {
        return new ViewModule<SampleState, SampleParcelable, SampleStateMapper,
                SampleParcelMapper>() {
            @NonNull
            @Override
            public SampleStateMapper provideStateToParcelableMapper() {
                return new SampleStateMapper();
            }

            @NonNull
            @Override
            public SampleParcelMapper provideParcelableToStateMapper() {
                return new SampleParcelMapper();
            }
        };
    }
}
