package com.a65aps.moxyarchitecturecomponents.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import com.a65aps.architecturecomponents.domain.Interactor;
import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.activity.BaseActivity;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.architecturecomponents.presentation.presenter.Presenter;
import com.a65aps.architecturecomponents.presentation.view.View;
import com.arellomobile.mvp.MvpDelegate;

@UiThread
public abstract class MoxyActivity<S extends State, Parcel extends Parcelable,
        V extends View<S>, I extends Interactor<S, R>, R extends Router,
        P extends Presenter<S, V, I, R>> extends BaseActivity<S, Parcel, V, I, R, P> {

    @Nullable
    private MvpDelegate<? extends MoxyActivity<S, Parcel, V, I, R, P>> mvpDelegate;

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMvpDelegate().onCreate(savedInstanceState);
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
