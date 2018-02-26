package com.a65apps.architecturecomponents.domain.sample;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.presentation.presenter.PresenterScope;

import dagger.Binds;
import dagger.Module;

@Module
public interface SampleDomainModule {

    @PresenterScope
    @Binds
    @NonNull
    SampleInteractor bindsTo(@NonNull SampleModel model);
}
