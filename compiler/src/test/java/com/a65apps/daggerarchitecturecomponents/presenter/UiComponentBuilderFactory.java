package com.a65apps.daggerarchitecturecomponents.presenter;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class UiComponentBuilderFactory<Key, ComponentBuilder>
        implements ComponentBuilderFactory<Key, ComponentBuilder> {
    private final Map<Key, Provider<ComponentBuilder>> subComponentBuilders;

    @Inject
    public UiComponentBuilderFactory(Map<Key, Provider<ComponentBuilder>> subComponentBuilders) {
        this.subComponentBuilders = subComponentBuilders;
    }

    @Override
    public ComponentBuilder get(Key key) {
        Provider<ComponentBuilder> provider = subComponentBuilders.get(key);
        if (provider == null) {
            throw new AssertionError("Can't find provider for component key = " + key.toString());
        }

        return provider.get();
    }
}
