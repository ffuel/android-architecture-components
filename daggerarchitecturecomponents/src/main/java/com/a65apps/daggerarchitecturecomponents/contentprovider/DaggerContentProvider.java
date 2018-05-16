package com.a65apps.daggerarchitecturecomponents.contentprovider;

import com.a65apps.architecturecomponents.data.contentprovider.BaseContentProvider;

import dagger.android.AndroidInjection;

public abstract class DaggerContentProvider extends BaseContentProvider {

    @Override
    protected void inject() {
        AndroidInjection.inject(this);
    }
}
