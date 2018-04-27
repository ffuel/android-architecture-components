package com.a65aps.daggerarchitecturecomponents.presenter;

import android.support.annotation.NonNull;

public interface ComponentBuilderFactory<Key, ComponentBuilder> {
    @NonNull
    ComponentBuilder get(@NonNull Key key);
}
