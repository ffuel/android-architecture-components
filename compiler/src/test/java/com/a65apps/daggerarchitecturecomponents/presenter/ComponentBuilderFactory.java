package com.a65apps.daggerarchitecturecomponents.presenter;


public interface ComponentBuilderFactory<Key, ComponentBuilder> {
    ComponentBuilder get(Key key);
}
