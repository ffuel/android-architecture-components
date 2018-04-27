package com.a65aps.daggerarchitecturecomponents.presenter;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.presentation.presenter.Presenter;

import dagger.MapKey;

@MapKey
public @interface PresenterKey {

    @NonNull
    Class<? extends Presenter> value();
}
