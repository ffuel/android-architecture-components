package com.a65aps.architecturecomponents.presentation.presenter;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.Interactor;
import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.navigation.BaseRouter;
import com.a65aps.architecturecomponents.presentation.view.BaseView;

public interface ProviderPresenterComponent<S extends State, V extends BaseView<S>, I extends Interactor<S>,
        R extends BaseRouter, P extends BasePresenter<S, V, I, R>> extends PresenterComponent<S, V, I, R, P> {
    void inject(@NonNull PresenterProvider<P> presenterProvider);
}
