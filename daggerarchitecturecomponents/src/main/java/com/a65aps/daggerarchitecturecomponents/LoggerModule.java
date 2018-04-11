package com.a65aps.daggerarchitecturecomponents;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.data.log.AndroidApplicationLogger;
import com.a65aps.architecturecomponents.domain.log.ApplicationLogger;

import dagger.Binds;
import dagger.Module;

@Module
public interface LoggerModule {

    @Binds
    @NonNull
    ApplicationLogger bindsTo(@NonNull AndroidApplicationLogger logger);
}
