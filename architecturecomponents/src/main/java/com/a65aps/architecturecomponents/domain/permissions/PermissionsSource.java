package com.a65aps.architecturecomponents.domain.permissions;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.DataSource;

import java.util.List;

import io.reactivex.Single;

public interface PermissionsSource extends DataSource {

    @NonNull
    Single<List<PermissionState>> requestPermissions(boolean force, @NonNull String... permission);

    @NonNull
    Single<PermissionState> requestPermission(boolean force, @NonNull String permission);
}
