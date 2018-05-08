package com.a65aps.architecturecomponents.domain.permissions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface RequestPermissionsManager {

    void setResultCallback(@Nullable OnRequestPermissionCallback callback);

    void requestPermission(@NonNull String[] permission, int requestCode);

    boolean showRequestPermissionRationale(@NonNull String permission);

    boolean checkPermission(@NonNull String permission);

    interface OnRequestPermissionCallback {
        void onRequestPermissionsResult(int requestCode, @NonNull String[] permission, int... grantResult);
    }
}
