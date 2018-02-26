package com.a65apps.architecturecomponents.domain.main;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.presentation.presenter.PresenterScope;

import dagger.Binds;
import dagger.Module;

@Module
public interface MainDomainModule {

    @PresenterScope
    @Binds
    @NonNull
    MainInteractor bindsTo(@NonNull MainModel model);
}
