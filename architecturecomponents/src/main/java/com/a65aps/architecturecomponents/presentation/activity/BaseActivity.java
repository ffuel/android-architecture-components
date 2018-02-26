package com.a65aps.architecturecomponents.presentation.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.a65aps.architecturecomponents.ApplicationDelegate;
import com.a65aps.architecturecomponents.domain.Interactor;
import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.fragment.BaseFragment;
import com.a65aps.architecturecomponents.presentation.mapper.ParcelableToStateMapper;
import com.a65aps.architecturecomponents.presentation.mapper.StateToParcelableMapper;
import com.a65aps.architecturecomponents.presentation.navigation.BaseNavigator;
import com.a65aps.architecturecomponents.presentation.navigation.BaseRouter;
import com.a65aps.architecturecomponents.presentation.presenter.BasePresenter;
import com.a65aps.architecturecomponents.presentation.presenter.PresenterProvider;
import com.a65aps.architecturecomponents.presentation.presenter.ProviderPresenterComponent;
import com.a65aps.architecturecomponents.presentation.view.BaseView;
import com.arellomobile.mvp.MvpAppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import ru.terrakok.cicerone.NavigatorHolder;

public abstract class BaseActivity<S extends State, Parcel extends Parcelable,
        V extends BaseView<S>, I extends Interactor<S>, R extends BaseRouter,
        Presenter extends BasePresenter<S, V, I, R>> extends MvpAppCompatActivity
        implements HasSupportFragmentInjector, BaseView<S> {

    private static final String VIEW_STATE = "view_state";

    @Inject
    @Nullable
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    @Nullable
    NavigatorHolder navigationHolder;
    @Inject
    @Nullable
    BaseNavigator navigator;
    @Inject
    @Nullable
    StateToParcelableMapper<S, Parcel> stateMapper;
    @Inject
    @Nullable
    ParcelableToStateMapper<Parcel, S> parcelMapper;

    @Nullable
    private Parcel state;

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationDelegate.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
    }

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
    protected void onResumeFragments() {
        super.onResumeFragments();

        if (navigator == null) {
            throw new IllegalStateException("BaseNavigator is not provided for: "
                    + this.getClass().getName());
        }
        if (navigationHolder != null) {
            navigationHolder.setNavigator(navigator);
        }
    }

    @Override
    @CallSuper
    protected void onPause() {
        if (navigationHolder != null) {
            navigationHolder.removeNavigator();
        }
        super.onPause();
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
    @Nullable
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
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
    protected abstract Presenter getPresenter();

    @NonNull
    @CallSuper
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
        return (ProviderPresenterComponent<S, V, I, R, Presenter>)
                ApplicationDelegate.presenterSubComponentBuilders()
                .getPresenterSubComponentBuilder(clazz)
                .build();
    }
}
