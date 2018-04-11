package com.a65aps.daggerarchitecturecomponents.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.a65aps.architecturecomponents.presentation.presenter.Presenter;

import javax.inject.Inject;
import javax.inject.Provider;

public class PresenterProvider<P extends Presenter> {

    @Inject
    @Nullable
    Provider<P> presenter;

    public PresenterProvider() {
//       empty constructor
    }

    @NonNull
    public P getPresenter() {
        if (presenter == null) {
            throw new IllegalStateException("PresenterProvider is not injected");
        }

        return presenter.get();
    }
}
