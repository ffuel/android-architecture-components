package com.a65apps.architecturecomponents.domain;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.a65apps.architecturecomponents.domain.model.IntentInteractor;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

import io.reactivex.Maybe;

public interface MaybeIntent<S extends State, R extends Router> extends Intent {

    @NonNull
    Maybe<S> execute(@NonNull IntentInteractor<S, R> interactor, @NonNull R router, @Nullable Object data);

    @NonNull
    Maybe<S> onError(@NonNull Throwable error, @NonNull IntentInteractor<S, R> interactor,  @NonNull R router);
}
