package com.a65aps.daggerarchitecturecomponents.presenter;

import com.a65aps.architecturecomponents.domain.Interactor;
import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.architecturecomponents.presentation.presenter.Presenter;
import com.a65aps.architecturecomponents.presentation.view.View;

import dagger.MembersInjector;

public interface PresenterComponent<S extends State, V extends View<S>, I extends Interactor<S, R>,
        R extends Router, P extends Presenter<S, V, I, R>> extends MembersInjector<P> {
}
