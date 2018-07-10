package com.a65apps.architecturecomponents.presentation.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.mapper.ParcelableToStateMapper;
import com.a65apps.architecturecomponents.presentation.mapper.StateToParcelableMapper;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;

import javax.inject.Inject;

public class FragmentDelegate<S extends State, Parcel extends Parcelable, P extends Presenter> {
    private static final String VIEW_STATE = "view_state";

    @NonNull
    private final FragmentInterface<Parcel, P> fragment;
    @NonNull
    private final StateToParcelableMapper<S, Parcel> stateMapper;
    @NonNull
    private final ParcelableToStateMapper<Parcel, S> parcelMapper;

    @Nullable
    private Parcel state;

    @Inject
    public FragmentDelegate(@NonNull FragmentInterface<Parcel, P> fragment,
                            @NonNull StateToParcelableMapper<S, Parcel> stateMapper,
                            @NonNull ParcelableToStateMapper<Parcel, S> parcelMapper) {
        this.fragment = fragment;
        this.stateMapper = stateMapper;
        this.parcelMapper = parcelMapper;
    }

    @CallSuper
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }
    }

    @CallSuper
    @Nullable
    public android.view.View onCreateView(@NonNull LayoutInflater inflater,
                                          @Nullable ViewGroup container,
                                          @SuppressWarnings("unused") @Nullable Bundle savedInstanceState) {
        return inflater.inflate(fragment.getLayoutRes(), container, false);
    }

    @CallSuper
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (state != null) {
            outState.putParcelable(VIEW_STATE, state);
        }
    }

    @CallSuper
    public void updateState(@NonNull S state) {
        this.state = map(state);
        fragment.updateState(this.state);
    }

    public boolean onBackPressed() {
        fragment.getPresenter().onBackPressed();
        return true;
    }

    @Nullable
    protected Parcel getState() {
        return state;
    }

    @NonNull
    public Parcel map(@NonNull S state) {
        return stateMapper.map(state);
    }

    @NonNull
    public S map(@NonNull Parcel state) {
        return parcelMapper.map(state);
    }

    private void restoreState(@NonNull Bundle savedInstanceState) {
        Parcel localState = savedInstanceState.getParcelable(VIEW_STATE);
        this.state = localState;
        if (localState != null) {
            //noinspection unchecked
            fragment.getPresenter().restoreState(map(localState));
        }
    }

    public interface FragmentInterface<Parcel extends Parcelable, P extends Presenter> {

        void updateState(@NonNull Parcel state);

        @LayoutRes
        int getLayoutRes();

        @NonNull
        P getPresenter();
    }
}
