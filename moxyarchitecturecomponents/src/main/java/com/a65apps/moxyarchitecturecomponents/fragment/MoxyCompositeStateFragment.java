package com.a65apps.moxyarchitecturecomponents.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.a65apps.architecturecomponents.domain.CompositeStateInteractor;
import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.fragment.BaseCompositeStateFragment;
import com.a65apps.architecturecomponents.presentation.navigation.Router;
import com.a65apps.architecturecomponents.presentation.presenter.CompositeStatePresenter;
import com.a65apps.architecturecomponents.presentation.view.CompositeStateView;
import com.arellomobile.mvp.MvpDelegate;

public abstract class MoxyCompositeStateFragment<S extends State, Parcel extends Parcelable,
        CS extends State, V extends CompositeStateView<S, CS>,
        I extends CompositeStateInteractor<S, CS, R>, R extends Router,
        P extends CompositeStatePresenter<S, CS, V, I, R>>
        extends BaseCompositeStateFragment<S, Parcel, CS, V, I, R, P> {

    @Nullable
    private MoxyFragmentDelegate<S, Parcel, V, I, R, P> delegate;

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvpDelegate().onCreate(savedInstanceState);
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        getMvpDelegate().onAttach();
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        getMvpDelegate().onAttach();
    }

    @Override
    @CallSuper
    public void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    @Override
    @CallSuper
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getMvpDelegate().onSaveInstanceState(outState);
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        super.onDestroyView();
        getMvpDelegate().onDestroyView();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        getMvpDelegate().onDestroy(this);
    }

    /**
     * @return The {@link MvpDelegate} being used by this Activity.
     */
    @NonNull
    public MoxyFragmentDelegate<S, Parcel, V, I, R, P> getMvpDelegate() {
        if (delegate == null) {
            //noinspection unchecked
            delegate = new MoxyFragmentDelegate(new MvpDelegate<>(this));
        }
        return delegate;
    }
}
