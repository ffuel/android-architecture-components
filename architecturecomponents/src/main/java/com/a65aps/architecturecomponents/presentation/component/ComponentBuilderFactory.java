package com.a65aps.architecturecomponents.presentation.component;

import android.support.annotation.NonNull;

public interface ComponentBuilderFactory<Key, ComponentBuilder> {
    @NonNull
    ComponentBuilder get(@NonNull Key key);
}
