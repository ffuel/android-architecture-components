package com.a65apps.daggerarchitecturecomponents.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.fragment.BaseCompositeStateFragment;
import com.a65apps.architecturecomponents.presentation.presenter.CompositeStatePresenter;

import dagger.android.support.AndroidSupportInjection;

public abstract class DaggerCompositeStateFragment<S extends State, Parcel extends Parcelable,
        CS extends State, P extends CompositeStatePresenter>
        extends BaseCompositeStateFragment<S, Parcel, CS, P> {

    @CallSuper
    @Override
    protected void inject(@Nullable Bundle arguments) {
        AndroidSupportInjection.inject(this);
    }
}
