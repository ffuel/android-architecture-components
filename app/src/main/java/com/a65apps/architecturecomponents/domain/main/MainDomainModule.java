package com.a65apps.architecturecomponents.domain.main;

import android.support.annotation.NonNull;

import com.a65aps.daggerarchitecturecomponents.presenter.PresenterScope;

import dagger.Binds;
import dagger.Module;

@Module
public interface MainDomainModule {

    @Binds
    @PresenterScope
    @NonNull
    MainInteractor bindsTo(@NonNull MainModel model);
}
