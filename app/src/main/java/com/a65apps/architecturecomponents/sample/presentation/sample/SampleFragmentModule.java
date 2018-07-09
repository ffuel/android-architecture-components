package com.a65apps.architecturecomponents.sample.presentation.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.fragment.FragmentDelegate;
import com.a65apps.architecturecomponents.sample.domain.sample.SampleState;
import com.a65apps.architecturecomponents.di.ViewModule;
import com.a65apps.daggerarchitecturecomponents.view.DaggerViewModule;

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

    @Provides
    @NonNull
    FragmentDelegate.FragmentInterface<SampleParcelable, SamplePresenter> providesFragmentInterface(
            @NonNull SampleFragment fragment
    ) {
        return fragment;
    }
}
