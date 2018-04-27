package com.a65aps.daggerarchitecturecomponents;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.schedulers.SchedulerType;

import dagger.MapKey;

@MapKey
public @interface SchedulerKey {

    @NonNull
    SchedulerType value();
}
