package com.a65aps.daggerarchitecturecomponents.service;

import android.support.annotation.CallSuper;

import com.a65aps.architecturecomponents.data.service.BaseService;

import dagger.android.AndroidInjection;

public abstract class DaggerService extends BaseService {

    @Override
    @CallSuper
    protected void inject() {
        AndroidInjection.inject(this);
    }
}
