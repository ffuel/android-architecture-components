package com.a65apps.architecturecomponents.domain;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.a65apps.architecturecomponents.presentation.navigation.Router;

import io.reactivex.Observable;

public interface Interactor<S extends State, R extends Router> {

    @UiThread
    void firstStart(boolean isRestoring);

    @NonNull
    Observable<S> observeState();

    @UiThread
    void restoreState(@NonNull S state);

    @NonNull
    R getRouter();

    void dispose();
}
