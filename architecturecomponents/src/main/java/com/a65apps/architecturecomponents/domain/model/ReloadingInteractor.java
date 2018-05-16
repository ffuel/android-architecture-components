package com.a65apps.architecturecomponents.domain.model;

import com.a65apps.architecturecomponents.domain.Interactor;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

public interface ReloadingInteractor<S extends ReloadingState, R extends Router>
        extends Interactor<S, R> {

    void reload();
}
