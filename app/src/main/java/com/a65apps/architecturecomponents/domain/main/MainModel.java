package com.a65apps.architecturecomponents.domain.main;

import android.Manifest;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.R;
import com.a65aps.architecturecomponents.domain.model.BaseModel;
import com.a65aps.architecturecomponents.domain.permissions.PermissionState;
import com.a65aps.architecturecomponents.domain.permissions.PermissionsSource;
import com.a65aps.architecturecomponents.domain.resources.StringResources;
import com.a65aps.architecturecomponents.presentation.navigation.Router;

import javax.inject.Inject;

final class MainModel extends BaseModel<MainState, Router> implements MainInteractor {

    @NonNull
    private final StringResources stringResources;

    @Inject
    MainModel(@NonNull Router router, @NonNull StringResources stringResources) {
        super(MainState.create(Screen.SAMPLE), router);
        this.stringResources = stringResources;
    }

    @Override
    public void firstStart(boolean isRestoring) {
        if (!isRestoring) {
            getRouter().newRootScreen(getState().screen().getName());
        }
    }

    @Override
    public void navigateContacts(@NonNull PermissionsSource permissionsSource) {
        addDisposable(permissionsSource.requestPermission(false, Manifest.permission.READ_CONTACTS)
                .doOnSuccess(this::checkPermissionState)
                .subscribe());
    }

    @Override
    public void forceContactsPermissions(@NonNull PermissionsSource permissionsSource) {
        getRouter().exit();
        addDisposable(permissionsSource.requestPermission(true, Manifest.permission.READ_CONTACTS)
                .doOnSuccess(this::checkPermissionState)
                .subscribe());
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
