package com.a65apps.daggerarchitecturecomponents.presenter;

import com.a65apps.architecturecomponents.sample.presentation.main.Presenter;

import dagger.MapKey;

@MapKey
public @interface PresenterKey {

    Class<? extends Presenter> value();
}
