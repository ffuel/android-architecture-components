package com.a65apps.architecturecomponents.presentation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.architecturecomponents.presentation.view.View;

import javax.inject.Inject;

@UiThread
public abstract class BaseBottomSheetDialogFragment<S extends State, Parcel extends Parcelable, P extends Presenter>
        extends BottomSheetDialogFragment implements View<S>, FragmentDelegate.FragmentInterface<Parcel, P> {

    @Inject
    FragmentDelegate<S, Parcel, P> fragmentDelegate;

    @Override
    public void onAttach(Context context) {
        inject(getArguments());
        super.onAttach(context);
    }

    protected abstract void inject(@Nullable Bundle arguments);

    @Override
    @CallSuper
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentDelegate.onActivityCreated(savedInstanceState);
    }

    @CallSuper
    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                          @Nullable Bundle savedInstanceState) {
        return fragmentDelegate.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    @CallSuper
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        fragmentDelegate.onSaveInstanceState(outState);
    }

    @Override
    @CallSuper
    public void updateState(@NonNull S state) {
        fragmentDelegate.updateState(state);
    }

    public boolean onBackPressed() {
        return fragmentDelegate.onBackPressed();
    }

    @Nullable
    protected Parcel getState() {
        return fragmentDelegate.getState();
    }
}
