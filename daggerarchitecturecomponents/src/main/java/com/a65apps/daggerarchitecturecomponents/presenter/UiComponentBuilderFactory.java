package com.a65apps.daggerarchitecturecomponents.presenter;

import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class UiComponentBuilderFactory<Key, ComponentBuilder>
        implements ComponentBuilderFactory<Key, ComponentBuilder> {
    @NonNull
    private final Map<Key, Provider<ComponentBuilder>> subComponentBuilders;

    @Inject
    public UiComponentBuilderFactory(@NonNull Map<Key, Provider<ComponentBuilder>> subComponentBuilders) {
        this.subComponentBuilders = subComponentBuilders;
    }

    @NonNull
    @Override
    public ComponentBuilder get(@NonNull Key key) {
        Provider<ComponentBuilder> provider = subComponentBuilders.get(key);
        if (provider == null) {
            throw new AssertionError("Can't find provider for component key = " + key.toString());
        }

        return provider.get();
    }
}
