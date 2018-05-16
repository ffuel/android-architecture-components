package com.a65apps.daggerarchitecturecomponents.presenter;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.presenter.Presenter;

import dagger.MapKey;

@MapKey
public @interface PresenterKey {

    @NonNull
    Class<? extends Presenter> value();
}
