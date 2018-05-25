package com.a65apps.architecturecomponents.sample.domain.main;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.a65apps.architecturecomponents.sample.R;
import com.a65apps.architecturecomponents.domain.model.BaseModel;
import com.a65apps.architecturecomponents.domain.permissions.PermissionState;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsSource;
import com.a65apps.architecturecomponents.domain.resources.StringResources;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

import javax.inject.Inject;

final class MainModel extends BaseModel<MainState, Router> implements MainInteractor {

    @NonNull
    private final StringResources stringResources;
    @NonNull
    private final PermissionsSource permissionsSource;

    @Inject
    MainModel(@NonNull Router router, @NonNull StringResources stringResources,
              @NonNull PermissionsSource permissionsSource) {
        super(MainState.create(Screen.SAMPLE), router);
        this.stringResources = stringResources;
        this.permissionsSource = permissionsSource;
    }

    @UiThread
    @Override
    public void firstStart(boolean isRestoring) {
        if (!isRestoring) {
            getRouter().newRootScreen(getState().screen().getName());
        }
    }

    @UiThread
    @Override
    public void navigateContacts() {
        addDisposable(permissionsSource.requestPermission(false, Manifest.permission.READ_CONTACTS)
                .doOnSuccess(this::checkPermissionState)
                .subscribe());
    }

    @UiThread
    @Override
    public void forceContactsPermissions() {
        getRouter().exit();
        addDisposable(permissionsSource.requestPermission(true, Manifest.permission.READ_CONTACTS)
                .doOnSuccess(this::checkPermissionState)
                .subscribe());
    }

    @UiThread
    @Override
    public void onBack() {
        switch (getState().screen()) {
            case SAMPLE:
            case CONTACTS:
            case POSTS:
                setState(MainState.create(Screen.SAMPLE));
                break;
            case PERMISSION_EXPLANATION:
                setState(MainState.create(Screen.CONTACTS));
                break;
            default:
                break;
        }
        getRouter().exit();
    }

    @Override
    public void navigatePosts() {
        setState(MainState.create(Screen.POSTS));
        getRouter().navigateTo(getState().screen().getName());
    }

    private void checkPermissionState(@NonNull PermissionState state) {
        switch (state) {
            case GRANTED:
                setState(MainState.create(Screen.CONTACTS));
                getRouter().navigateTo(getState().screen().getName());
                break;
            case SHOW_SETTINGS:
            case NOT_GRANTED:
                getRouter().showSystemMessage(stringResources.getString(
                        R.string.contacts_permissions_not_granted));
                break;
            case NEED_EXPLANATION:
                setState(MainState.create(Screen.PERMISSION_EXPLANATION));
                getRouter().navigateTo(getState().screen().getName(), new String[] {
                        stringResources.getString(R.string.contacts_permissions_not_granted)
                });
                break;
            default:
                break;
        }
    }
}
