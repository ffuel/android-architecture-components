package com.a65aps.architecturecomponents.domain.schedulers;

import android.support.annotation.NonNull;

import dagger.MapKey;

@MapKey
public @interface SchedulerKey {
    @NonNull
    SchedulerType value();
}
