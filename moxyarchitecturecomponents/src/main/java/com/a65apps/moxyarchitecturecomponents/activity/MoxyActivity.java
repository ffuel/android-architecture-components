package com.a65apps.moxyarchitecturecomponents.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.activity.BaseActivity;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.arellomobile.mvp.MvpDelegate;

@UiThread
public abstract class MoxyActivity<S extends State, Parcel extends Parcelable, P extends Presenter>
        extends BaseActivity<S, Parcel, P> {

    @Nullable
    private MvpDelegate<? extends MoxyActivity<S, Parcel, P>> mvpDelegate;

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getMvpDelegate().onCreate(savedInstanceState);

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();

        getMvpDelegate().onAttach();
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();

        getMvpDelegate().onAttach();
    }

    @Override
    @CallSuper
    protected void onStop() {
        super.onStop();

        getMvpDelegate().onDetach();
    }

    @Override
    @CallSuper
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        getMvpDelegate().onSaveInstanceState(outState);
        getMvpDelegate().onDetach();
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        super.onDestroy();

        getMvpDelegate().onDestroyView();

        if (isFinishing()) {
            getMvpDelegate().onDestroy();
        }
    }

    /**
     * @return The {@link MvpDelegate} being used by this Activity.
     */
    @NonNull
    public MvpDelegate getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new MvpDelegate<>(this);
        }
        return mvpDelegate;
    }
}
