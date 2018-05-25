package com.a65apps.architecturecomponents.sample.data.common;

import android.support.annotation.NonNull;

import dagger.MapKey;

@MapKey
public @interface SourceKey {
    @NonNull
    SourceType value();
}
