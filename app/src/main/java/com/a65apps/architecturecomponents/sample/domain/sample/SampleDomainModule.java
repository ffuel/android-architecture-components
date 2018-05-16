package com.a65apps.architecturecomponents.sample.domain.sample;

import android.support.annotation.NonNull;

import dagger.Binds;
import dagger.Module;

@Module
public interface SampleDomainModule {

    @Binds
    @NonNull
    SampleInteractor bindsTo(@NonNull SampleModel model);
}
