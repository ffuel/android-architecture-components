package com.a65aps.daggerarchitecturecomponents.contentprovider;

import com.a65aps.architecturecomponents.data.contentprovider.BaseContentProvider;

import dagger.android.AndroidInjection;

public abstract class DaggerContentProvider extends BaseContentProvider {

    @Override
    protected void inject() {
        AndroidInjection.inject(this);
    }
}
