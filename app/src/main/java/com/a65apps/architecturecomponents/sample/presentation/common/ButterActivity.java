package com.a65apps.architecturecomponents.sample.presentation.common;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.moxydaggerarchitecturecomponents.activity.MoxyDaggerActivity;

import butterknife.ButterKnife;

public abstract class ButterActivity<S extends State, Parcel extends Parcelable, P extends Presenter>
        extends MoxyDaggerActivity<S, Parcel, P> {

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
