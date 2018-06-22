package com.a65apps.moxyarchitecturecomponents.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.a65apps.architecturecomponents.domain.Interactor;
import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.fragment.BaseFragment;
import com.a65apps.architecturecomponents.presentation.navigation.Router;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.architecturecomponents.presentation.view.View;
import com.arellomobile.mvp.MvpDelegate;

final class MoxyFragmentDelegate<S extends State, Parcel extends Parcelable,
        V extends View<S>, I extends Interactor<S, R>, R extends Router,
        P extends Presenter<S, V, I, R>> {

    private boolean isStateSaved;
    @NonNull
    private final MvpDelegate<? extends BaseFragment<S, Parcel, V, I, R, P>> mvpDelegate;

    MoxyFragmentDelegate(@NonNull MvpDelegate<? extends BaseFragment<S, Parcel, V, I, R, P>> mvpDelegate) {
        this.mvpDelegate = mvpDelegate;
    }

    void onCreate(@Nullable Bundle savedInstanceState) {
        mvpDelegate.onCreate(savedInstanceState);
    }

    void onAttach() {
        isStateSaved = false;
        mvpDelegate.onAttach();
    }

    void onStop() {
        mvpDelegate.onDetach();
    }

    void onSaveInstanceState(@NonNull Bundle outState) {
        isStateSaved = true;
        mvpDelegate.onSaveInstanceState(outState);
        mvpDelegate.onDetach();
    }

    void onDestroyView() {
        mvpDelegate.onDetach();
        mvpDelegate.onDestroyView();
    }

    void onDestroy(@NonNull Fragment fragment) {
        FragmentActivity activity = fragment.getActivity();
        //We leave the screen and respectively all fragments will be destroyed
        if (activity != null && activity.isFinishing()) {
            mvpDelegate.onDestroy();
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
        Fragment parent = fragment.getParentFragment();
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving();
            parent = parent.getParentFragment();
        }

        if (fragment.isRemoving() || anyParentIsRemoving) {
            mvpDelegate.onDestroy();
        }
    }
}
