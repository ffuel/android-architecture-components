package com.a65aps.architecturecomponents.domain.permissions;

import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

@UiThread
public interface PermissionsRequestHolder {

    void setWorker(@Nullable RequestPermissionsWorker worker);

    void removeWorker();
}
