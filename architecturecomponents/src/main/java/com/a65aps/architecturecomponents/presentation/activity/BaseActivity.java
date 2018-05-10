package com.a65aps.architecturecomponents.presentation.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.a65aps.architecturecomponents.domain.Interactor;
import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.domain.permissions.PermissionsManager;
import com.a65aps.architecturecomponents.domain.permissions.RequestPermissionsWorker;
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
        implements View<S>, RequestPermissionsWorker {

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
    @Inject
    @Nullable
    PermissionsManager permissionsManager;

    @Nullable
    private Parcel state;

    @Nullable
    private OnRequestPermissionCallback permissionsCallback;

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        if (permissionsManager != null) {
            permissionsManager.getPermissionsRequestHolder().setWorker(this);
        }
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

    @Override
    @CallSuper
    protected void onDestroy() {
        if (permissionsManager != null) {
            permissionsManager.getPermissionsRequestHolder().removeWorker();
        }
        super.onDestroy();
    }

    protected abstract void inject();

    protected final void restoreState(@NonNull Bundle savedInstanceState) {
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

    @Override
    @CallSuper
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length != 0 && grantResults.length != 0 && permissionsCallback != null) {
            permissionsCallback.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    protected abstract void updateState(@NonNull Parcel state);

    @LayoutRes
    protected abstract int getLayoutResId();

    @NonNull
    protected abstract P getPresenter();

    @Override
    public final void setResultCallback(@Nullable OnRequestPermissionCallback callback) {
        this.permissionsCallback = callback;
    }

    @Override
    public final void requestPermission(@NonNull String[] permission, int requestCode) {
        ActivityCompat.requestPermissions(this, permission, requestCode);
    }

    @Override
    public final boolean showRequestPermissionRationale(@NonNull String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
    }

    @Override
    public final boolean checkPermission(@NonNull String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
