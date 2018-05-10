package com.a65aps.architecturecomponents.domain.permissions;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import java.util.List;

import io.reactivex.SingleOnSubscribe;

@UiThread
public interface PermissionsRequest extends SingleOnSubscribe<List<PermissionState>> {

    void execute(@NonNull RequestPermissionsWorker worker);
}
