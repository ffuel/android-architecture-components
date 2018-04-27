package com.a65aps.architecturecomponents.data.contentprovider;

import android.content.ContentProvider;
import android.support.annotation.CallSuper;

public abstract class BaseContentProvider extends ContentProvider {

    @Override
    @CallSuper
    public boolean onCreate() {
        inject();
        return true;
    }

    protected abstract void inject();
}
