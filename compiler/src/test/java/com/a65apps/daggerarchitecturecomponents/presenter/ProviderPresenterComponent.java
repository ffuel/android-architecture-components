package com.a65apps.daggerarchitecturecomponents.presenter;

import com.a65apps.architecturecomponents.sample.presentation.main.Presenter;

public interface ProviderPresenterComponent<S, V, I, R, P extends Presenter<S, V, I, R>> extends PresenterComponent<S, V, I, R, P> {
    void inject(PresenterProvider<P> presenterProvider);
}
