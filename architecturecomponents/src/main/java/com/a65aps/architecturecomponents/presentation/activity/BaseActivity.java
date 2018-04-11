package com.a65aps.architecturecomponents.presentation.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.a65aps.architecturecomponents.domain.Interactor;
import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.fragment.BaseFragment;
import com.a65aps.architecturecomponents.presentation.mapper.ParcelableToStateMapper;
import com.a65aps.architecturecomponents.presentation.mapper.StateToParcelableMapper;
import com.a65aps.architecturecomponents.presentation.navigation.NavigatorDelegate;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.architecturecomponents.presentation.presenter.Presenter;
import com.a65aps.architecturecomponents.presentation.view.View;

import javax.inject.Inject;

@UiThread
public abstract class BaseActivity<S extends State, Parcel extends Parcelable,
        V extends View<S>, I extends Interactor<S, R>, R extends Router,
        P extends Presenter<S, V, I, R>> extends AppCompatActivity
        implements View<S> {

    private static final String VIEW_STATE = "view_state";

    @Inject
    @Nullable
    StateToParcelableMapper<S, Parcel> stateMapper;
    @Inject
    @Nullable
    ParcelableToStateMapper<Parcel, S> parcelMapper;
    @Inject
    @Nullable
    NavigatorDelegate navigatorDelegate;

    @Nullable
    private Parcel state;

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
    }

    @Override
    @CallSuper
    protected void onResumeFragments() {
        super.onResumeFragments();

        if (navigatorDelegate != null) {
            navigatorDelegate.onAttach();
        }
    }

    @Override
    @CallSuper
    protected void onPause() {
        super.onPause();

        if (navigatorDelegate != null) {
            navigatorDelegate.onDetach();
        }
    }

    protected abstract void inject();

    @Override
    @CallSuper
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        state = savedInstanceState.getParcelable(VIEW_STATE);
        if (state != null) {
            if (parcelMapper == null) {
                throw new IllegalStateException("ParcelableMapper is not provided");
            }
            getPresenter().restoreState(parcelMapper.map(state));
        }
    }

    @Override
    @CallSuper
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (state != null) {
            outState.putParcelable(VIEW_STATE, state);
        }
    }

    @Override
    @CallSuper
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getFragments() == null || fragmentManager.getFragments().isEmpty()) {
            getPresenter().onBackPressed();
            return;
        }

        for (Fragment fragment : fragmentManager.getFragments()) {
            if (!(fragment instanceof BaseFragment) || fragment.isDetached()
                    || fragment.isRemoving()) {
                continue;
            }

            BaseFragment baseFragment = (BaseFragment) fragment;
            if (baseFragment.onBackPressed()) {
                return;
            }
        }

        getPresenter().onBackPressed();
    }

    @Override
    @CallSuper
    public void updateState(@NonNull S state) {
        if (stateMapper == null) {
            throw new IllegalStateException("ParcelableMapper is not provided");
        }

        this.state = stateMapper.map(state);
        updateState(this.state);
    }

    protected abstract void updateState(@NonNull Parcel state);

    @LayoutRes
    protected abstract int getLayoutResId();

    @NonNull
    protected abstract P getPresenter();
}
