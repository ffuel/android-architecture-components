package com.a65apps.architecturecomponents.domain.permissions;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

@UiThread
public class PermissionsManager {

    @NonNull
    private final PermissionsRequestBuffer requestBuffer;

    public PermissionsManager() {
        requestBuffer = new PermissionsRequestBuffer();
    }

    @NonNull
    public PermissionsRequestHolder getPermissionsRequestHolder() {
        return requestBuffer;
    }

    public void executeRequest(@NonNull PermissionsRequest request) {
        requestBuffer.executeRequest(request);
    }
}
