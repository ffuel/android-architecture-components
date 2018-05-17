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

    public PermissionsManager(@NonNull PermissionsRequestBuffer buffer) {
        requestBuffer = buffer;
    }

    @NonNull
    public PermissionsRequestHolder getPermissionsRequestHolder() {
        return requestBuffer;
    }

    public void executeRequest(@NonNull PermissionsRequest request) {
        requestBuffer.executeRequest(request);
    }
}
