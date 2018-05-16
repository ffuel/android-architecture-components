package com.a65apps.architecturecomponents.data.permissions;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.permissions.PermissionState;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsManager;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public final class AndroidPermissionsSource implements PermissionsSource {

    @NonNull
    private final PermissionsManager permissionsManager;

    @Inject
    public AndroidPermissionsSource(@NonNull PermissionsManager permissionsManager) {
        this.permissionsManager = permissionsManager;
    }

    @NonNull
    @Override
    public Single<List<PermissionState>> requestPermissions(boolean force, @NonNull String... permission) {
        return RxPermissionRequest.create(permissionsManager, permission, force);
    }

    @NonNull
    @Override
    public Single<PermissionState> requestPermission(boolean force, @NonNull String permission) {
        return requestPermissions(force, permission)
                .filter(result -> !result.isEmpty())
                .map(result -> result.get(0))
                .toSingle(PermissionState.NOT_GRANTED);
    }
}
