package com.a65aps.architecturecomponents.domain;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.presentation.navigation.Router;

import io.reactivex.Observable;

public interface CompositeStateInteractor<S extends State, CS extends State, R extends Router>
        extends Interactor<S, R> {

    @NonNull
    Observable<CS> observeDependentState();
}
