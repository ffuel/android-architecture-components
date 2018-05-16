package com.a65apps.architecturecomponents.sample.domain.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.model.ReloadingInteractor;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

import dagger.Binds;
import dagger.Module;

@Module
public interface SampleDomainModule {

    @Binds
    @NonNull
    ReloadingInteractor<SampleState, Router> bindsTo(@NonNull SampleModel model);
}
