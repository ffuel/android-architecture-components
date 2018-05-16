package com.a65apps.architecturecomponents.domain.permissions;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.a65apps.architecturecomponents.domain.DataSource;

import java.util.List;

import io.reactivex.Single;

@UiThread
public interface PermissionsSource extends DataSource {

    @NonNull
    Single<List<PermissionState>> requestPermissions(boolean force, @NonNull String... permission);

    @NonNull
    Single<PermissionState> requestPermission(boolean force, @NonNull String permission);
}
