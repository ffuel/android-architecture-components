package com.a65apps.architecturecomponents.sample.domain.main;

import android.support.annotation.NonNull;

import com.a65apps.daggerarchitecturecomponents.presenter.PresenterScope;

import dagger.Binds;
import dagger.Module;

@Module
public interface MainDomainModule {

    @Binds
    @PresenterScope
    @NonNull
    MainInteractor bindsTo(@NonNull MainModel model);
}
