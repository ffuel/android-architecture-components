package com.a65aps.daggerarchitecturecomponents;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.data.resources.AndroidStringResources;
import com.a65aps.architecturecomponents.domain.resources.StringResources;

import dagger.Binds;
import dagger.Module;

@Module
public interface ResourcesModule {

    @Binds
    @NonNull
    StringResources bindsStringResources(@NonNull AndroidStringResources resources);
}
