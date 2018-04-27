package com.a65aps.architecturecomponents.domain.model;

import com.a65aps.architecturecomponents.domain.Interactor;
import com.a65aps.architecturecomponents.presentation.navigation.Router;

public interface ReloadingInteractor<S extends ReloadingState, R extends Router>
        extends Interactor<S, R> {

    void reload();
}
