package com.a65apps.daggerarchitecturecomponents.presenter;

import com.a65apps.architecturecomponents.domain.Interactor;
import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.navigation.Router;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.architecturecomponents.presentation.view.View;

import dagger.MembersInjector;

public interface PresenterComponent<S extends State, V extends View<S>, I extends Interactor<S, R>,
        R extends Router, P extends Presenter<S, V, I, R>> extends MembersInjector<P> {
}
