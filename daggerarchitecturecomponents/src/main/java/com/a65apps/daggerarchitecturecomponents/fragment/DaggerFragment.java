package com.a65apps.daggerarchitecturecomponents.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.a65apps.architecturecomponents.domain.Interactor;
import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.fragment.BaseFragment;
import com.a65apps.architecturecomponents.presentation.navigation.Router;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.architecturecomponents.presentation.view.View;

import dagger.android.support.AndroidSupportInjection;

public abstract class DaggerFragment<S extends State, Parcel extends Parcelable,
        V extends View<S>, I extends Interactor<S, R>, R extends Router,
        P extends Presenter<S, V, I, R>> extends BaseFragment<S, Parcel, V, I, R, P> {

    @CallSuper
    @Override
    protected void inject(@Nullable Bundle arguments) {
        AndroidSupportInjection.inject(this);
    }
}
