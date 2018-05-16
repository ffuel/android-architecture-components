package com.a65apps.moxyarchitecturecomponents.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

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

    private boolean isStateSaved;
    @Nullable
    private MvpDelegate<? extends MoxyCompositeStateFragment<S, Parcel, CS, V, I, R, P>> mvpDelegate;

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

        isStateSaved = false;
        getMvpDelegate().onAttach();
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();

        isStateSaved = false;
        getMvpDelegate().onAttach();
    }

    @Override
    @CallSuper
    public void onStop() {
        super.onStop();

        getMvpDelegate().onDetach();
    }

    @Override
    @CallSuper
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        isStateSaved = true;
        getMvpDelegate().onSaveInstanceState(outState);
        getMvpDelegate().onDetach();
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        super.onDestroyView();

        getMvpDelegate().onDetach();
        getMvpDelegate().onDestroyView();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();

        FragmentActivity activity = getActivity();
        //We leave the screen and respectively all fragments will be destroyed
        if (activity != null && activity.isFinishing()) {
            getMvpDelegate().onDestroy();
            return;
        }

        // When we rotate device isRemoving() return true for fragment placed in backstack
        // http://stackoverflow.com/questions/34649126/fragment-back-stack-and-isremoving
        if (isStateSaved) {
            isStateSaved = false;
            return;
        }

        // See https://github.com/Arello-Mobile/Moxy/issues/24
        boolean anyParentIsRemoving = false;
        Fragment parent = getParentFragment();
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving();
            parent = parent.getParentFragment();
        }

        if (isRemoving() || anyParentIsRemoving) {
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
