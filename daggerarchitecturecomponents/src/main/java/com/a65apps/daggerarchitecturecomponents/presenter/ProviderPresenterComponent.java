package com.a65apps.daggerarchitecturecomponents.presenter;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.Interactor;
import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.navigation.Router;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.architecturecomponents.presentation.view.View;

public interface ProviderPresenterComponent<S extends State, V extends View<S>, I extends Interactor<S, R>,
        R extends Router, P extends Presenter<S, V, I, R>> extends PresenterComponent<S, V, I, R, P> {
    void inject(@NonNull PresenterProvider<P> presenterProvider);
}
