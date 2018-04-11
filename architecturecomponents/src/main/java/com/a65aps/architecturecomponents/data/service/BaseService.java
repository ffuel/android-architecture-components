package com.a65aps.architecturecomponents.data.service;

import android.app.Service;
import android.support.annotation.CallSuper;

public abstract class BaseService extends Service {

    @Override
    @CallSuper
    public void onCreate() {
        inject();
        super.onCreate();
    }

    protected abstract void inject();
}
