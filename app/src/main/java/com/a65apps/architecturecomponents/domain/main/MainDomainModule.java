package com.a65apps.architecturecomponents.domain.main;

import android.support.annotation.NonNull;

import dagger.Binds;
import dagger.Module;

@Module
public interface MainDomainModule {

    @Binds
    @NonNull
    MainInteractor bindsTo(@NonNull MainModel model);
}
