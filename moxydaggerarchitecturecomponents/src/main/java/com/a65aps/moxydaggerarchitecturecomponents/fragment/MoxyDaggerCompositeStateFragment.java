package com.a65aps.moxydaggerarchitecturecomponents.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.a65aps.architecturecomponents.domain.CompositeStateInteractor;
import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.architecturecomponents.presentation.presenter.CompositeStatePresenter;
import com.a65aps.architecturecomponents.presentation.view.CompositeStateView;
import com.a65aps.moxyarchitecturecomponents.fragment.MoxyCompositeStateFragment;

import dagger.android.support.AndroidSupportInjection;

public abstract class MoxyDaggerCompositeStateFragment<S extends State, Parcel extends Parcelable,
        CS extends State, V extends CompositeStateView<S, CS>,
        I extends CompositeStateInteractor<S, CS, R>, R extends Router,
        P extends CompositeStatePresenter<S, CS, V, I, R>>
        extends MoxyCompositeStateFragment<S, Parcel, CS, V, I, R, P> {

    @CallSuper
    @Override
    protected void inject(@Nullable Bundle arguments) {
        AndroidSupportInjection.inject(this);
    }
}