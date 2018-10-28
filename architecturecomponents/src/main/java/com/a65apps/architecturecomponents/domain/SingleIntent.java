package com.a65apps.architecturecomponents.domain;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.a65apps.architecturecomponents.domain.model.IntentInteractor;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

import io.reactivex.Single;

public interface SingleIntent<S extends State, R extends Router> extends Intent {

    @NonNull
    Single<S> execute(@NonNull IntentInteractor<S, R> interactor, @NonNull R router, @Nullable Object data);

    @NonNull
    Single<S> onError(@NonNull Throwable error, @NonNull IntentInteractor<S, R> interactor, @NonNull R router);
}
