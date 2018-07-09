package com.a65apps.architecturecomponents.presentation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.mapper.ParcelableToStateMapper;
import com.a65apps.architecturecomponents.presentation.mapper.StateToParcelableMapper;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.architecturecomponents.presentation.view.View;

import javax.inject.Inject;

@UiThread
public abstract class BaseFragment<S extends State, Parcel extends Parcelable, P extends Presenter>
        extends Fragment implements View<S> {

    private static final String VIEW_STATE = "view_state";

    @Inject
    @Nullable
    StateToParcelableMapper<S, Parcel> stateMapper;
    @Inject
    @Nullable
    ParcelableToStateMapper<Parcel, S> parcelMapper;

    @Nullable
    private Parcel state;

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
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }
    }

    @CallSuper
    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                          @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    @CallSuper
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (state != null) {
            outState.putParcelable(VIEW_STATE, state);
        }
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

    public boolean onBackPressed() {
        getPresenter().onBackPressed();
        return true;
    }

    protected abstract void updateState(@NonNull Parcel state);

    @LayoutRes
    protected abstract int getLayoutRes();

    @NonNull
    protected abstract P getPresenter();

    @Nullable
    protected Parcel getState() {
        return state;
    }

    private void restoreState(@NonNull Bundle savedInstanceState) {
        Parcel localState = savedInstanceState.getParcelable(VIEW_STATE);
        this.state = localState;
        if (localState != null) {
            if (parcelMapper == null) {
                throw new IllegalStateException("ParcelableMapper is not provided");
            }
            //noinspection unchecked
            getPresenter().restoreState(parcelMapper.map(localState));
        }
    }
}
