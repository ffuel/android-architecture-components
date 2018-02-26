package com.a65apps.architecturecomponents.data.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.sample.SampleSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public interface SampleDataModule {

    @Singleton
    @Binds
    @NonNull
    SampleSource bindsTo(@NonNull SampleDataSource source);
}
