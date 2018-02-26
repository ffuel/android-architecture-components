package com.a65aps.architecturecomponents.presentation.presenter;

import com.a65aps.architecturecomponents.domain.Interactor;
import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.navigation.BaseRouter;
import com.a65aps.architecturecomponents.presentation.view.BaseView;

import dagger.MembersInjector;

public interface PresenterComponent<S extends State, V extends BaseView<S>, I extends Interactor<S>,
        R extends BaseRouter, P extends BasePresenter<S, V, I, R>> extends MembersInjector<P> {
}
