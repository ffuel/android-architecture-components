package com.a65aps.architecturecomponents.presentation.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Provider;

public class PresenterProvider<Presenter extends BasePresenter> {

    @Inject
    @Nullable
    Provider<Presenter> presenter;

    @NonNull
    public Presenter getPresenter() {
        if (presenter == null) {
            throw new IllegalStateException("PresenterProvider is not injected");
        }

        return presenter.get();
    }
}
