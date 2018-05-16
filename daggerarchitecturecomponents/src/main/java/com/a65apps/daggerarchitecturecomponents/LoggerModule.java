package com.a65apps.daggerarchitecturecomponents;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.data.log.AndroidApplicationLogger;
import com.a65apps.architecturecomponents.domain.log.ApplicationLogger;

import dagger.Binds;
import dagger.Module;

@Module
public interface LoggerModule {

    @Binds
    @NonNull
    ApplicationLogger bindsTo(@NonNull AndroidApplicationLogger logger);
}
