package com.a65apps.architecturecomponents.domain.main;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.Interactor;
import com.a65aps.architecturecomponents.domain.permissions.PermissionsSource;
import com.a65aps.architecturecomponents.presentation.navigation.Router;

public interface MainInteractor extends Interactor<MainState, Router> {

    void navigateContacts(@NonNull PermissionsSource permissionsSource);

    void forceContactsPermissions(@NonNull PermissionsSource permissionsSource);
}
