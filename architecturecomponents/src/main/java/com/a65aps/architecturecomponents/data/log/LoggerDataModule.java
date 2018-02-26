package com.a65aps.architecturecomponents.data.log;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.log.ApplicationLogger;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public interface LoggerDataModule {

    @Singleton
    @Binds
    @NonNull
    ApplicationLogger bindsTo(@NonNull AndroidApplicationLogger logger);
}
