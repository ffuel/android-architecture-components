package com.a65aps.architecturecomponents.domain;

import android.support.annotation.NonNull;

import dagger.MapKey;

@MapKey
public @interface SourceKey {
    @NonNull
    Source value();
}
