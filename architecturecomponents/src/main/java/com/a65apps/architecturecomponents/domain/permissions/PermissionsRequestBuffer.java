package com.a65apps.architecturecomponents.domain.permissions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import net.jcip.annotations.NotThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@NotThreadSafe
@UiThread
public class PermissionsRequestBuffer implements PermissionsRequestHolder {

    @Nullable
    private RequestPermissionsWorker worker;
    @NonNull
    private final Queue<PermissionsRequest> pendingRequests = new LinkedList<>();

    @Override
    public void setWorker(@Nullable RequestPermissionsWorker worker) {
        this.worker = worker;
        while (!pendingRequests.isEmpty()) {
            if (worker != null) {
                PermissionsRequest request = pendingRequests.poll();
                if (request != null) {
                    executeRequest(request);
                }
            } else {
                break;
            }
        }
    }

    @Override
    public void removeWorker() {
        this.worker = null;
    }

    void executeRequest(@NonNull PermissionsRequest request) {
        if (worker != null) {
            request.execute(worker);
        } else {
            pendingRequests.add(request);
        }
    }
}
