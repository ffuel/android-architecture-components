package com.a65apps.architecturecomponents.sample.presentation.mvi;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.di.ViewModule;
import com.a65apps.architecturecomponents.presentation.fragment.FragmentDelegate;
import com.a65apps.architecturecomponents.sample.domain.mvi.MviState;
import com.a65apps.daggerarchitecturecomponents.view.DaggerViewModule;

import dagger.Module;
import dagger.Provides;

@Module
public class MviFragmentModule extends DaggerViewModule<MviState, MviParcelable,
        MviStateMapper, MviParcelMapper> {

    @Provides
    @NonNull
    ViewModule<MviState, MviParcelable, MviStateMapper,
            MviParcelMapper> providesModule() {
        return new ViewModule<MviState, MviParcelable, MviStateMapper,
                MviParcelMapper>() {
            @NonNull
            @Override
            public MviStateMapper provideStateToParcelableMapper() {
                return new MviStateMapper();
            }

            @NonNull
            @Override
            public MviParcelMapper provideParcelableToStateMapper() {
                return new MviParcelMapper();
            }
        };
    }

    @Provides
    @NonNull
    FragmentDelegate.FragmentInterface<MviParcelable, MviPresenter> providesFragmentInterface(
            @NonNull MviFragment fragment
    ) {
        return fragment;
    }
}
