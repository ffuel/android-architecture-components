package com.a65apps.daggerarchitecturecomponents.presenter;

import android.support.annotation.NonNull;

public interface ComponentBuilderFactory<Key, ComponentBuilder> {
    @NonNull
    ComponentBuilder get(@NonNull Key key);
}
