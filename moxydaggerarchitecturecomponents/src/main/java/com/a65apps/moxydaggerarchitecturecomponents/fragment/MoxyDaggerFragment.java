package com.a65apps.moxydaggerarchitecturecomponents.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.moxyarchitecturecomponents.fragment.MoxyFragment;

import dagger.android.support.AndroidSupportInjection;

public abstract class MoxyDaggerFragment<S extends State, Parcel extends Parcelable, P extends Presenter>
        extends MoxyFragment<S, Parcel, P> {

    @CallSuper
    @Override
    protected void inject(@Nullable Bundle arguments) {
        AndroidSupportInjection.inject(this);
    }
}
