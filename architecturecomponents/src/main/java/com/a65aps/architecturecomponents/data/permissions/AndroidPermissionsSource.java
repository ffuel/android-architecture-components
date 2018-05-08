package com.a65aps.architecturecomponents.data.permissions;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.permissions.PermissionState;
import com.a65aps.architecturecomponents.domain.permissions.PermissionsSource;
import com.a65aps.architecturecomponents.domain.permissions.RequestPermissionsManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public final class AndroidPermissionsSource implements PermissionsSource {

    @NonNull
    private final RequestPermissionsManager manager;

    @Inject
    public AndroidPermissionsSource(@NonNull RequestPermissionsManager manager) {
        this.manager = manager;
    }

    @NonNull
    @Override
    public Single<List<PermissionState>> requestPermissions(boolean force, @NonNull String... permission) {
        return RxPermissionRequest.create(manager, permission, force);
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
