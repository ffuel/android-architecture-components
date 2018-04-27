package com.a65apps.architecturecomponents.presentation.common;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.a65aps.architecturecomponents.domain.Interactor;
import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.architecturecomponents.presentation.presenter.Presenter;
import com.a65aps.architecturecomponents.presentation.view.View;
import com.a65aps.moxydaggerarchitecturecomponents.activity.MoxyDaggerActivity;

import butterknife.ButterKnife;

public abstract class ButterActivity<S extends State, Parcel extends Parcelable,
        V extends View<S>, I extends Interactor<S, R>, R extends Router,
        P extends Presenter<S, V, I, R>> extends MoxyDaggerActivity<S, Parcel, V, I, R, P> {

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
