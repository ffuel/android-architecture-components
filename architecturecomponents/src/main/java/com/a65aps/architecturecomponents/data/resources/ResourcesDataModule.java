package com.a65aps.architecturecomponents.data.resources;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.resources.StringResources;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public interface ResourcesDataModule {

    @Singleton
    @Binds
    @NonNull
    StringResources bindsTo(@NonNull AndroidStringResources resources);
}
