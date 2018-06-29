package com.a65apps.moxydaggerarchitecturecomponents.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.presenter.CompositeStatePresenter;
import com.a65apps.moxyarchitecturecomponents.fragment.MoxyCompositeStateFragment;

import dagger.android.support.AndroidSupportInjection;

public abstract class MoxyDaggerCompositeStateFragment<S extends State, Parcel extends Parcelable,
        CS extends State, P extends CompositeStatePresenter>
        extends MoxyCompositeStateFragment<S, Parcel, CS, P> {

    @CallSuper
    @Override
    protected void inject(@Nullable Bundle arguments) {
        AndroidSupportInjection.inject(this);
    }
}
