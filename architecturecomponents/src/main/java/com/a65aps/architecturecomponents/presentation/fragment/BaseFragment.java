package com.a65aps.architecturecomponents.presentation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.a65aps.architecturecomponents.ApplicationDelegate;
import com.a65aps.architecturecomponents.domain.Interactor;
import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.mapper.ParcelableToStateMapper;
import com.a65aps.architecturecomponents.presentation.mapper.StateToParcelableMapper;
import com.a65aps.architecturecomponents.presentation.navigation.BaseNavigator;
import com.a65aps.architecturecomponents.presentation.navigation.BaseRouter;
import com.a65aps.architecturecomponents.presentation.presenter.BasePresenter;
import com.a65aps.architecturecomponents.presentation.presenter.PresenterProvider;
import com.a65aps.architecturecomponents.presentation.presenter.ProviderPresenterComponent;
import com.a65aps.architecturecomponents.presentation.view.BaseView;
import com.arellomobile.mvp.MvpAppCompatFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<S extends State, Parcel extends Parcelable,
        V extends BaseView<S>, I extends Interactor<S>, R extends BaseRouter,
        Presenter extends BasePresenter<S, V, I, R>> extends MvpAppCompatFragment
        implements BaseView<S> {

    private static final String VIEW_STATE = "view_state";

    @Inject
    @Nullable
    StateToParcelableMapper<S, Parcel> stateMapper;
    @Inject
    @Nullable
    ParcelableToStateMapper<Parcel, S> parcelMapper;

    @Nullable
    private Parcel state;
    @Nullable
    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        ApplicationDelegate.inject(this);
        super.onAttach(context);
    }

    @Override
    @CallSuper
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }
    }

    @Override
    @CallSuper
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (BaseNavigator.isBackCommandActivated()) {
            Animation a = new Animation() {
            };
            a.setDuration(0);
            return a;
        }

        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @CallSuper
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    @CallSuper
    public void onResume() {
        View view = getView();
        if (view != null && BaseNavigator.isBackCommandActivated()) {
            view.setVisibility(View.GONE);
            view.requestLayout();
        } else if (view != null) {
            view.setVisibility(View.VISIBLE);
        }

        super.onResume();
    }

    @Override
    @CallSuper
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (state != null) {
            outState.putParcelable(VIEW_STATE, state);
        }
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
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
    protected abstract Presenter getPresenter();

    @CallSuper
    @NonNull
    protected Presenter providePresenter(
            @NonNull Class<? extends BasePresenter<S, V, I, R>> clazz) {
        ProviderPresenterComponent<S, V, I, R, Presenter> presenterComponent =
                buildPresenterComponent(clazz);
        PresenterProvider<Presenter> presenterProvider = new PresenterProvider<>();
        presenterComponent.inject(presenterProvider);
        Presenter presenter = presenterProvider.getPresenter();
        presenter.setComponent(presenterComponent);
        return presenter;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    private ProviderPresenterComponent<S, V, I, R, Presenter> buildPresenterComponent(
            @NonNull Class<? extends BasePresenter<S, V, I, R>> clazz) {
        return (ProviderPresenterComponent<S, V, I, R, Presenter>) ApplicationDelegate
                .presenterSubComponentBuilders()
                .getPresenterSubComponentBuilder(clazz)
                .build();
    }

    private void restoreState(@NonNull Bundle savedInstanceState) {
        state = savedInstanceState.getParcelable(VIEW_STATE);
        if (state != null) {
            if (parcelMapper == null) {
                throw new IllegalStateException("ParcelableMapper is not provided");
            }
            getPresenter().restoreState(parcelMapper.map(state));
        }
    }
}
