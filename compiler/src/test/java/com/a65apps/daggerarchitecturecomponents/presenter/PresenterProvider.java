package com.a65apps.daggerarchitecturecomponents.presenter;

import com.a65apps.architecturecomponents.sample.presentation.main.Presenter;

import javax.inject.Inject;
import javax.inject.Provider;

public class PresenterProvider<P extends Presenter> {

    @Inject
    Provider<P> presenter;

    public PresenterProvider() {
//       empty constructor
    }

    public P getPresenter() {
        if (presenter == null) {
            throw new IllegalStateException("PresenterProvider is not injected");
        }

        return presenter.get();
    }
}
