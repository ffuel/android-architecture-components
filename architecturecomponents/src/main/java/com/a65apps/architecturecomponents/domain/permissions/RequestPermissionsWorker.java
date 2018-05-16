package com.a65apps.architecturecomponents.domain.permissions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

@UiThread
public interface RequestPermissionsWorker {

    void setResultCallback(@Nullable OnRequestPermissionCallback callback);

    void requestPermission(@NonNull String[] permission, int requestCode);

    boolean showRequestPermissionRationale(@NonNull String permission);

    boolean checkPermission(@NonNull String permission);

    interface OnRequestPermissionCallback {
        void onRequestPermissionsResult(int requestCode, @NonNull String[] permission, int... grantResult);
    }
}
