package com.a65aps.architecturecomponents.presentation.presenter;

import com.a65aps.architecturecomponents.domain.CompositeStateInteractor;
import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.architecturecomponents.presentation.view.CompositeStateView;

public interface CompositeStatePresenter<S extends State, CS extends State,
        V extends CompositeStateView<S, CS>, I extends CompositeStateInteractor<S, CS, R>,
        R extends Router>
        extends Presenter<S, V, I, R> {
}
