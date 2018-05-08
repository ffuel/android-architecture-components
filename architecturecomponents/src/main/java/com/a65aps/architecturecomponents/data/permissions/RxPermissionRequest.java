package com.a65aps.architecturecomponents.data.permissions;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.permissions.PermissionState;
import com.a65aps.architecturecomponents.domain.permissions.RequestPermissionsManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposables;

final class RxPermissionRequest implements SingleOnSubscribe<List<PermissionState>> {

    private static final int REQUEST_CODE = 100;

    @NonNull
    private final WeakReference<RequestPermissionsManager> managerPointer;
    @NonNull
    private final String[] permissions;
    private final boolean force;

    @NonNull
    public static Single<List<PermissionState>> create(@NonNull RequestPermissionsManager manager,
                                                       @NonNull String[] permissions, boolean force) {
        return Single.defer(() -> Single.create(new RxPermissionRequest(manager, permissions, force)));
    }

    private RxPermissionRequest(@NonNull RequestPermissionsManager manager,
                                @NonNull String[] permissions, boolean force) {
        this.managerPointer = new WeakReference<>(manager);
        this.permissions = permissions;
        this.force = force;
    }

    @Override
    public void subscribe(SingleEmitter<List<PermissionState>> emitter) {
        emitter.setDisposable(Disposables.fromRunnable(() -> {
            RequestPermissionsManager manager = managerPointer.get();
            if (manager != null) {
                manager.setResultCallback(null);
            }
        }));

        RequestPermissionsManager manager = managerPointer.get();
        if (manager != null) {
            List<PermissionState> result = new ArrayList<>(permissions.length);
            for (String permission: permissions) {
                if (manager.checkPermission(permission)) {
                    result.add(PermissionState.GRANTED);
                }
            }

            if (result.size() == permissions.length) {
                emitter.onSuccess(result);
                return;
            }

            manager.setResultCallback((requestCode, permission, grantResult) -> {
                RequestPermissionsManager ptr = managerPointer.get();
                if (ptr == null) {
                    return;
                }

                if (requestCode == REQUEST_CODE) {
                    List<PermissionState> finalResult = new ArrayList<>(permissions.length);
                    for (int i = 0; i < permissions.length; i++) {
                        if (grantResult[i] != PackageManager.PERMISSION_GRANTED
                                && !manager.showRequestPermissionRationale(permission[i])) {
                            finalResult.add(PermissionState.SHOW_SETTINGS);
                        } else if (grantResult[i] != PackageManager.PERMISSION_GRANTED) {
                            finalResult.add(PermissionState.NOT_GRANTED);
                        } else {
                            finalResult.add(PermissionState.GRANTED);
                        }
                    }
                    emitter.onSuccess(finalResult);
                }
            });

            boolean canRequest = false;
            for (String permission : permissions) {
                canRequest = force || !manager.showRequestPermissionRationale(permission);
                if (!canRequest) {
                    break;
                }
            }
            if (canRequest) {
                manager.requestPermission(permissions, REQUEST_CODE);
            } else {
                result.clear();
                for (int i = 0; i < permissions.length; i++) {
                    result.add(PermissionState.NEED_EXPLANATION);
                }
                emitter.onSuccess(result);
            }
        }
    }
}
