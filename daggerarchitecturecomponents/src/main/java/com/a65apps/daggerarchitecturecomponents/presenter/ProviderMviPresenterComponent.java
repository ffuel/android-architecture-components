package com.a65apps.daggerarchitecturecomponents.presenter;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.domain.model.IntentInteractor;
import com.a65apps.architecturecomponents.presentation.navigation.Router;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.architecturecomponents.presentation.view.View;

public interface ProviderMviPresenterComponent<S extends State, V extends View<S>, R extends Router,
        P extends Presenter<S, V, IntentInteractor<S, R>, R>> extends ProviderPresenterComponent<S, V,
        IntentInteractor<S, R>, R, P> {
}
