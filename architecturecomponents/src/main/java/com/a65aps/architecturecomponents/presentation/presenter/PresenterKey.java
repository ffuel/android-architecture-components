package com.a65aps.architecturecomponents.presentation.presenter;

import android.support.annotation.NonNull;

import dagger.MapKey;

@MapKey
public @interface PresenterKey {

    @NonNull
    Class<? extends BasePresenter> value();
}
