package com.a65aps.architecturecomponents.domain;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.presentation.navigation.Router;

import io.reactivex.Observable;

public interface Interactor<S extends State, R extends Router> {

    @NonNull
    Observable<S> observeState();

    void restoreState(@NonNull S state);

    @NonNull
    R getRouter();

    void dispose();
}
