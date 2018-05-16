package com.a65apps.daggerarchitecturecomponents;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.schedulers.SchedulerType;

import dagger.MapKey;

@MapKey
public @interface SchedulerKey {

    @NonNull
    SchedulerType value();
}
