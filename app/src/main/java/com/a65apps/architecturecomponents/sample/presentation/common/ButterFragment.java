package com.a65apps.architecturecomponents.sample.presentation.common;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.moxydaggerarchitecturecomponents.fragment.MoxyDaggerFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class ButterFragment<S extends State, Parcel extends Parcelable, P extends Presenter>
        extends MoxyDaggerFragment<S, Parcel, P> {

    @Nullable
    private Unbinder unbinder;

    @CallSuper
    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater,
                                          @Nullable ViewGroup container,
                                          @Nullable Bundle savedInstanceState) {
        android.view.View view = super.onCreateView(inflater, container, savedInstanceState);
        if (view != null) {
            unbinder = ButterKnife.bind(this, view);
        }
        return view;
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }

        super.onDestroyView();
    }
}
