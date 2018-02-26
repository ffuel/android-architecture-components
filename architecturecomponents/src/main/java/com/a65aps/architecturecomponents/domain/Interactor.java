package com.a65aps.architecturecomponents.domain;

import android.support.annotation.NonNull;

import io.reactivex.Observable;

public interface Interactor<T extends State> {

    @NonNull
    Observable<T> observeState();

    void restoreState(@NonNull T state);
}
