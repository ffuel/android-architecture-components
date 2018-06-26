package com.a65apps.architecturecomponents.data.permissions;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.a65apps.architecturecomponents.domain.permissions.PermissionState;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsManager;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsRequest;
import com.a65apps.architecturecomponents.domain.permissions.RequestPermissionsWorker;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

final class RxPermissionRequest implements PermissionsRequest {

    private static final int REQUEST_CODE = 100;

    @NonNull
    private final String[] permissions;
    private final boolean force;
    @NonNull
    private final PermissionsManager permissionsManager;

    @Nullable
    private SingleEmitter<List<PermissionState>> emitter;

    @NonNull
    public static Single<List<PermissionState>> create(@NonNull PermissionsManager permissionsManager,
                                                       @NonNull String[] permissions, boolean force) {
        return Single.defer(() -> Single.create(
                new RxPermissionRequest(permissionsManager, permissions, force)));
    }

    private RxPermissionRequest(@NonNull PermissionsManager permissionsManager,
                                @NonNull String[] permissions, boolean force) {
        this.permissionsManager = permissionsManager;
        this.permissions = permissions;
        this.force = force;
    }

    @Override
    public void subscribe(@NonNull SingleEmitter<List<PermissionState>> emitter) {
        this.emitter = emitter;
        permissionsManager.executeRequest(this);
    }

    @Override
    public void execute(@NonNull RequestPermissionsWorker worker) {
        SingleEmitter<List<PermissionState>> localEmitter = this.emitter;
        if (localEmitter == null || localEmitter.isDisposed()) {
            return;
        }

        final WeakReference<RequestPermissionsWorker> managerPointer = new WeakReference<>(worker);
        localEmitter.setDisposable(createDisposable(managerPointer));

        RequestPermissionsWorker manager = managerPointer.get();
        if (manager != null) {
            List<PermissionState> result = new ArrayList<>(permissions.length);
            for (String permission: permissions) {
                if (manager.checkPermission(permission)) {
                    result.add(PermissionState.GRANTED);
                }
            }

            if (result.size() == permissions.length) {
                localEmitter.onSuccess(result);
                return;
            }

            manager.setResultCallback(createCallback(managerPointer, localEmitter));

            processNegativeCases(manager, result, localEmitter);
        }
    }

    private void processNegativeCases(@NonNull RequestPermissionsWorker manager,
                                      @NonNull List<PermissionState> result,
                                      @NonNull SingleEmitter<List<PermissionState>> emitter) {
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

    @NonNull
    private Disposable createDisposable(@NonNull final WeakReference<RequestPermissionsWorker> managerPointer) {
        return Disposables.fromRunnable(() -> {
            RequestPermissionsWorker manager = managerPointer.get();
            if (manager != null) {
                manager.setResultCallback(null);
            }
        });
    }

    @NonNull
    private RequestPermissionsWorker.OnRequestPermissionCallback createCallback(
            @NonNull final WeakReference<RequestPermissionsWorker> managerPointer,
            @NonNull SingleEmitter<List<PermissionState>> emitter) {
        return (requestCode, permission, grantResult) -> {
            RequestPermissionsWorker ptr = managerPointer.get();
            if (ptr == null) {
                emitter.onError(new IllegalStateException("RequestPermissionsManager is null"));
                return;
            }
            if (requestCode != REQUEST_CODE) {
                return;
            }

            List<PermissionState> finalResult = new ArrayList<>(permissions.length);
            for (int i = 0; i < permissions.length; i++) {
                if (grantResult[i] != PackageManager.PERMISSION_GRANTED
                        && !ptr.showRequestPermissionRationale(permission[i])) {
                    finalResult.add(PermissionState.SHOW_SETTINGS);
                } else if (grantResult[i] != PackageManager.PERMISSION_GRANTED) {
                    finalResult.add(PermissionState.NOT_GRANTED);
                } else {
                    finalResult.add(PermissionState.GRANTED);
                }
            }
            emitter.onSuccess(finalResult);
        };
    }
}
